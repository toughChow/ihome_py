package com.ctbu.guesthouse.service.impl;

import com.ctbu.guesthouse.dao.*;
import com.ctbu.guesthouse.domain.*;
import com.ctbu.guesthouse.dto.ConsumLogDto;
import com.ctbu.guesthouse.dto.SysLogDto;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    SysLogDao sysLogDao;
    @Autowired
    ConsumLogDao consumLogDao;
    @Autowired
    GoodsDao goodsDao;

    @Override
    public List<Room> findAll(Integer posId) {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Room> rooms = null;
        if (posId != null) {
            Position postion = positionDao.findById(Long.valueOf(posId)).get();
            rooms = roomDao.findAllByPosition(postion);
        } else {
            rooms = roomDao.findAll();
        }
        return rooms;
    }

    @Override
    public String updateRoom(Map map) {
        String name = (String) map.get("name");
        String num = (String) map.get("num");
        Long roomId = Long.valueOf((String) map.get("room_id"));
        Float price = Float.valueOf((String) map.get("price"));

        Room roomTmp = roomDao.findByRoomCode(num);
        if (roomTmp.getRoomCode().equals(num))
            return "房间号已存在";
        Room room = roomDao.findById(roomId).get();
        room.setRoomCode(num);
        room.setRoomName(name);
        room.setPrice(price);
        roomDao.saveAndFlush(room);
        return "修改成功";
    }

    @Override
    public Object pageLogDetail(Integer pageNo) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<SysLog> all = sysLogDao.findAll(pageable);
        List<SysLog> content = all.getContent();
        List<SysLogDto> sysLogDtos = new ArrayList<>();
        content.forEach(po -> {
            SysLogDto sysLogDto = new SysLogDto();
            Long roomId = po.getRoomId();
            Room room = roomDao.findById(roomId).get();
            BeanUtils.copyProperties(po, sysLogDto);
            sysLogDto.setRoomCode(room.getRoomCode());
            sysLogDtos.add(sysLogDto);
        });
        return new PageImpl<>(sysLogDtos, pageable, all.getTotalElements());
//        return all;
    }

    @Override
    public Object findTotalIncom() {
        Float total = 0f;
        List<SysLog> all = sysLogDao.findAll();
        for (SysLog sysLog : all) {
            total += sysLog.getGuestPrice();
        }
        return total;
    }

    @Override
    public Object pageConsumeDetail(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);

        Page<ConsumLog> consumLogsPage = consumLogDao.findAll(pageable);
        List<ConsumLog> content = consumLogsPage.getContent();

        List<ConsumLogDto> sysLogDtos = new ArrayList<>();
        content.forEach(po -> {
            ConsumLogDto consumLogDto = new ConsumLogDto();
            String mdcValue = po.getMdcValue();

            Goods goods = goodsDao.findById(Long.valueOf(po.getGoodsId())).get();

            // 获取房间号
            SysLog sysLog = sysLogDao.findByMdcValue(mdcValue);
            Room room = null;
            if (!Objects.isNull(sysLog)) {
                Long roomId = sysLog.getRoomId();
                room = roomDao.findById(roomId).get();
            } else {
                room = roomDao.findByMdcValue(mdcValue);
            }
            String roomCode = room.getRoomCode();

            BeanUtils.copyProperties(po, consumLogDto);

            // 设置房间代码 商品名字
            consumLogDto.setRoomCode(roomCode);
            consumLogDto.setGoodsName(goods.getGoodsName());

            sysLogDtos.add(consumLogDto);
        });
        return new PageImpl<>(sysLogDtos, pageable, consumLogsPage.getTotalElements());
    }

    @Override
    public Object getAllGoods() {
        List<Goods> all = goodsDao.findAll();
        return all;
    }

    @Override
    public String doConsume(Map map) {
        String roomCode = (String) map.get("room_code");
        String goodsId = (String) map.get("goods_id");
        String goodsNum = (String) map.get("goods_num");
        Room room = roomDao.findByRoomCode(roomCode);
        if (room.getStatus() != 1)
            return "该房间无人入住";
        ConsumLog consumLog = new ConsumLog(goodsId, goodsNum, room.getMdcValue());
        consumLogDao.save(consumLog);
        return "成功";
    }
}
