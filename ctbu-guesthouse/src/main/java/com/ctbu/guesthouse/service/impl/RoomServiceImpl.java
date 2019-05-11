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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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

    /**
     * 获取所有收银记录
     * @param pageNo
     * @return
     */
    @Override
    public Object pageLogDetail(Integer pageNo) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageNo, 5);
        // 获取房间收银分页记录
        Page<SysLog> all = sysLogDao.findAll((Root<SysLog> root,CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isNull(root.get("isDeleted"))); // 未被删除的
            return cb.and(predicates.toArray(new Predicate[]{}));
        }, pageable);

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

        // 获取销售物品分页记录
        Page<ConsumLog> all1 = consumLogDao.findAll((Root<ConsumLog> root,CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isNull(root.get("isDeleted"))); // 未被删除的
            return cb.and(predicates.toArray(new Predicate[]{}));
        }, pageable);

        List<ConsumLog> consumLogs = all1.getContent();
        consumLogs.forEach(po -> {
            SysLogDto sysLogDto = new SysLogDto();
            String mdcValue = po.getMdcValue();
            Room room = roomDao.findByMdcValue(mdcValue);

            sysLogDto.setGuestPrice(Float.valueOf(po.getConsumePrice()));
            sysLogDto.setCtTime(po.getCtTime());
            sysLogDto.setId(po.getId());
            if(!Objects.isNull(room)) {
                sysLogDto.setRoomCode(room.getRoomCode());
            } else {
                SysLog sysLog = sysLogDao.findByMdcValue(mdcValue);
                if(!Objects.isNull(sysLog)) {
                    Room logRoom = roomDao.findById(sysLog.getRoomId()).get();
                    sysLogDto.setRoomCode(logRoom.getRoomCode());
                }
            }
            sysLogDtos.add(sysLogDto);
        });
        return new PageImpl<>(sysLogDtos, pageable, all.getTotalElements()+all1.getTotalElements());
//        return all;
    }

    @Override
    public Object findTotalIncom() {
        Float total = 0f;
        List<SysLog> all = sysLogDao.findAll();
        for (SysLog sysLog : all) {
            total += sysLog.getGuestPrice();
        }
        List<ConsumLog> all1 = consumLogDao.findAll();
        for (ConsumLog consumLog : all1) {
            total += Float.valueOf(consumLog.getConsumePrice());
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
            consumLogDto.setConsumePrice(po.getConsumePrice());
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
        Integer goodsNum = Integer.valueOf((String) map.get("goods_num"));

        Goods goods = goodsDao.findById(Long.valueOf(goodsId)).get();
        Integer goodsNumber = Integer.valueOf(goods.getGoodsNumber());
        Integer goodsPrice = Integer.valueOf(goods.getGoodsPrice());
        Integer i = Integer.valueOf(goodsNumber) - goodsNum;
        if (i < 0)
            return "库存不足";
        // 更新库存
        goods.setGoodsNumber(i.toString());
        goodsDao.saveAndFlush(goods);
        // 存入价格
        Integer comsumePrice = goodsPrice * goodsNum;
        Room room = roomDao.findByRoomCode(roomCode);
        if (room.getStatus() != 1)
            return "该房间无人入住";
        ConsumLog consumLog = new ConsumLog(goodsId, goodsNum.toString(), comsumePrice.toString(), room.getMdcValue());
        consumLogDao.save(consumLog);
        return "成功";
    }

    @Override
    public Object pageConsumeDetails(Integer pageNo) {
//        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
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

            String mdcValue = po.getMdcValue();
            List<ConsumLog>  consumLogs = consumLogDao.findByMdcValue(mdcValue);
            Float guestPrice = sysLogDto.getGuestPrice();
            for (ConsumLog consumLog : consumLogs) {
                guestPrice += Float.valueOf(consumLog.getConsumePrice());
            }
            sysLogDto.setGuestPrice(guestPrice);

            sysLogDtos.add(sysLogDto);
        });
        return new PageImpl<>(sysLogDtos, pageable, all.getTotalElements());
//        return all;
    }

    @Override
    public String cancelBook(Long rooId) {
        Room room = roomDao.findById(rooId).get();
        room.initRoom();
        room.setStatus(0);
        roomDao.saveAndFlush(room);
        return "成功";
    }

    @Override
    public Object deleteLog(Long id, Date time) {
        SysLog sysLog = sysLogDao.findByIdAndCtTime(id, time);
        if(Objects.isNull(sysLog)) {
            ConsumLog consumeLog = consumLogDao.findByIdAndCtTime(id, time);
            if(!Objects.isNull(consumeLog)) {
                consumeLog.setIsDeleted(1);
                consumLogDao.save(consumeLog);
            }
        } else {
            sysLog.setIsDeleted(1);
            sysLogDao.save(sysLog);
        }
        return "成功";
    }
}
