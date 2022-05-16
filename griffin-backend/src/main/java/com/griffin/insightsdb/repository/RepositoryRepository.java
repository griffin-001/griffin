package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
}
