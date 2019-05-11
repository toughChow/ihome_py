package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.ConsumLog;
import com.ctbu.guesthouse.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ConsumLogDao extends JpaRepository<ConsumLog, Long>, JpaSpecificationExecutor<ConsumLog> {
    List<ConsumLog> findByMdcValue(String mdcValue);

    ConsumLog findByIdAndCtTime(Long id, Date time);
}
