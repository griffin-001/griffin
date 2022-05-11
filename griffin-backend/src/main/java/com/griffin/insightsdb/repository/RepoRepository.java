package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.DummyRepo;
import org.springframework.data.repository.CrudRepository;

public interface RepoRepository extends CrudRepository<DummyRepo, Integer> {
}
