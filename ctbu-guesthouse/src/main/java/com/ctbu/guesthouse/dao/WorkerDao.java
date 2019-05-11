package com.ctbu.guesthouse.dao;

import com.ctbu.guesthouse.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerDao extends JpaRepository<Worker, Long> {
}
