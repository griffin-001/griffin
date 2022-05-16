package com.griffin.insightsdb.service;

import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositoryRepository;
import com.griffin.insightsdb.repository.ServerRepository;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class InsightDBService {

    ServerRepository serverRepository;
    DependencyRepository dependencyRepository;
    RepositoryRepository repositoryRepository;

    public InsightDBService(ServerRepository serverRepository,
                            DependencyRepository dependencyRepository, RepositoryRepository repositoryRepository) {
        this.serverRepository = serverRepository;
        this.dependencyRepository = dependencyRepository;
        this.repositoryRepository = repositoryRepository;
    }
}
