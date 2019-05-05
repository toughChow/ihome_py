package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.ConsumLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumLogDao extends JpaRepository<ConsumLog, Long> {
}
