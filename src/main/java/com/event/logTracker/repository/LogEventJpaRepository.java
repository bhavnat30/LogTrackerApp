package com.event.logTracker.repository;

import com.event.logTracker.model.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventJpaRepository extends JpaRepository<LogEvent,String> {
}
