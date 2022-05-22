package com.griffin.collector;

import com.griffin.collector.bitbucket.BitbucketRepo;

import java.util.HashMap;

public interface Project {

    public void setRepoHashMap(HashMap<String, BitbucketRepo> repoHashMap);

    public HashMap<String, BitbucketRepo> getRepoHashMap();

    public String getKey();

    public String getName();
}
