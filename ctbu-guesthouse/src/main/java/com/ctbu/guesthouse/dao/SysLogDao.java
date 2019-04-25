package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogDao extends JpaRepository<SysLog, Long> {
}
