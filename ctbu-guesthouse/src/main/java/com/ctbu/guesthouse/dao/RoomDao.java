package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDao extends JpaRepository<Room, Long> {
    List<Room> findAllByPosition(Position postion);
}
