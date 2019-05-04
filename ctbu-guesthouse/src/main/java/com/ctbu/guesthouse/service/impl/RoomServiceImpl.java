package com.ctbu.guesthouse.service.impl;

import com.ctbu.guesthouse.dao.PositionDao;
import com.ctbu.guesthouse.dao.RoomDao;
import com.ctbu.guesthouse.dao.SysLogDao;
import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.domain.User;
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

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    SysLogDao sysLogDao;

    @Override
    public List<Room> findAll(Integer posId) {
        User userDetails = (User) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        List<Room> rooms = null;
        if(posId!=null) {
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
        Room room = roomDao.findById(roomId).get();
        room.setRoomCode(num);
        room.setRoomName(name);
        room.setPrice(price);
        roomDao.saveAndFlush(room);
        return "修改成功";
    }

    @Override
    public Object pageLogDetail(Integer pageNo) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
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
        return new PageImpl<>(sysLogDtos);
//        return all;
    }
}
