package com.griffin.collector.gitlab;

import com.griffin.collector.Project;
import com.griffin.collector.bitbucket.BitbucketRepository;
import com.griffin.collector.SCMWrapper;
import com.griffin.collector.bitbucket.BitbucketProject;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GitlabWrapper implements SCMWrapper {
    @Override
    public String getServerType(String ip) {
        return null;
    }

    @Override
    public List<BitbucketProject> getProjects(String ip) {
        return null;
    }

    @Override
    public HashMap<String, BitbucketRepository> getProjectRepos(String ip, Project project) {
        return null;
    }

    @Override
    public ArrayList<String> getRepos(String ip, String projectName) {
        return null;
    }

    @Override
    public JsonNode getRepoInfo(String ip, String repoURL) {
        return null;
    }
}
