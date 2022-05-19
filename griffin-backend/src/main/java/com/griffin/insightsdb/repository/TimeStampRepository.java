package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TimeStampRepository extends JpaRepository<TimeStamp, Long> {
    List<TimeStamp> findAllByOrderByTimestampAsc();
    TimeStamp findByTimestamp(Date timeStamp);
}
