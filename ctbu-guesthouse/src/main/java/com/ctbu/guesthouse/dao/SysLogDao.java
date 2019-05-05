package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysLogDao extends JpaRepository<SysLog, Long>, PagingAndSortingRepository<SysLog, Long> {
    SysLog findByMdcValue(String mdcValue);

}
