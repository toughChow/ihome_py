package com.ctbu.guesthouse.service.impl;

import com.ctbu.guesthouse.dao.PositionDao;
import com.ctbu.guesthouse.dao.RoomDao;
import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.User;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    PositionDao positionDao;

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
}
