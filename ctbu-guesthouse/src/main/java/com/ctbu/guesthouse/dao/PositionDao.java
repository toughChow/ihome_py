package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionDao extends JpaRepository<Position, Long> {
    List<Position> findAllByUser(User userDetails);
}
