package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.SnapshotDependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnapshotDependencyRepository extends JpaRepository<SnapshotDependency, Long> {

    SnapshotDependency findByDependencyIdAndRepositorySnapShotId(Long dependencyId, Long SnapShotId);
}
