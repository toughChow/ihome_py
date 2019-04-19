package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String s);
}
