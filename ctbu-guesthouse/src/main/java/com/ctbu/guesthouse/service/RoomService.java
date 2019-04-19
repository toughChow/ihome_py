package com.ctbu.guesthouse.service;

import com.ctbu.guesthouse.domain.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll(Integer posId);

}
