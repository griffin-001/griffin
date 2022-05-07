package com.griffin.collector.gitlab;

import com.griffin.collector.Project;
import com.griffin.collector.bitbucket.BitbucketRepository;

import java.util.HashMap;

public class GitlabProject implements Project {
    @Override
    public void setRepositoryHashMap(HashMap<String, BitbucketRepository> repositoryHashMap) {
    }

    @Override
    public String getKey() {
        return null;
    }
}
