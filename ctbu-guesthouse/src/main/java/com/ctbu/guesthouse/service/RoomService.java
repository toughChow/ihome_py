package com.ctbu.guesthouse.service;

import com.ctbu.guesthouse.domain.Room;

import java.util.List;
import java.util.Map;

public interface RoomService {
    List<Room> findAll(Integer posId);

    String updateRoom(Map map);

    Object pageLogDetail(Integer pageNo);

    Object findTotalIncom();

    Object pageConsumeDetail(Integer pageNo);

    Object getAllGoods();

    String doConsume(Map map);
}
