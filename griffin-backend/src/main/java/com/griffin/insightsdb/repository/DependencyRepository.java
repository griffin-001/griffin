package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {

    Dependency findByName(String name);

    Boolean existsByName(String name);
}
