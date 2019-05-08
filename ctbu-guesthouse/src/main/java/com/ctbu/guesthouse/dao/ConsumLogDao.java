package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.ConsumLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumLogDao extends JpaRepository<ConsumLog, Long> {
    List<ConsumLog> findByMdcValue(String mdcValue);
}
