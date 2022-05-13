package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.RelationMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<RelationMap, Long> {
}
