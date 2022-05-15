package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
}
