package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.RepositorySnapShot;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface RepositorySnapShotRepository extends JpaRepository<RepositorySnapShot, Long> {
    List<RepositorySnapShot> findByName(String name);
}
