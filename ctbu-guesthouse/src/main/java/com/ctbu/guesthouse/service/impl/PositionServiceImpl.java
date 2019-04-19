package com.ctbu.guesthouse.service.impl;

import com.ctbu.guesthouse.dao.PositionDao;
import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.User;
import com.ctbu.guesthouse.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    PositionDao positionDao;

    @Override
    public List<Position> findAll() {
        User userDetails = (User) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        List<Position> positions = positionDao.findAllByUser(userDetails);
        return positions;
    }
}
