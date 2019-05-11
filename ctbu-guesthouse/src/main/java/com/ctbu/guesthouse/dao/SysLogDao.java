package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface SysLogDao extends JpaRepository<SysLog, Long>, PagingAndSortingRepository<SysLog, Long>, JpaSpecificationExecutor<SysLog> {
    SysLog findByMdcValue(String mdcValue);

    SysLog findByIdAndCtTime(Long id, Date time);
}
