package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.SnapshotDependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotDependencyRepository extends JpaRepository<SnapshotDependency, Long> {

    SnapshotDependency findByDependencyIdAndRepositorySnapShotId(Long dependencyId, Long SnapShotId);

    List<SnapshotDependency> findByRepositorySnapShotId(Long snapShotId);
}
