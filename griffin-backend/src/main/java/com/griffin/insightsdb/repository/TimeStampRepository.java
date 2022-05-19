package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Long> {
}
