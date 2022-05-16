package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    List<Repository> findAllByNameOrderByTimestampDesc(String name);
}
