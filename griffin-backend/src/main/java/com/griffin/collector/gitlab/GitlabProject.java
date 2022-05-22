package com.griffin.collector.gitlab;

import com.griffin.collector.Project;
import com.griffin.collector.bitbucket.BitbucketRepo;

import java.util.HashMap;

public class GitlabProject implements Project {
    @Override
    public void setRepoHashMap(HashMap<String, BitbucketRepo> repoHashMap) {
    }

    @Override
    public HashMap<String, BitbucketRepo> getRepoHashMap() {
        return new HashMap<>();
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
