package com.griffin.insightsdb.service;

import com.griffin.insightsdb.repository.RepoRepository;

public class RepoService {

    private final RepoRepository repoRepository;


    public RepoService(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }
}
