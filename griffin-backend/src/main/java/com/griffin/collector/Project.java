package com.griffin.collector;

import com.griffin.collector.bitbucket.BitbucketRepository;

import java.util.HashMap;

public interface Project {

    public void setRepositoryHashMap(HashMap<String, BitbucketRepository> repositoryHashMap);

    public String getKey();
}
