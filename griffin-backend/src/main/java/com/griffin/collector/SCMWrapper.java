package com.griffin.collector;

import com.fasterxml.jackson.databind.JsonNode;
import com.griffin.collector.bitbucket.BitbucketProject;
import com.griffin.collector.bitbucket.BitbucketRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is meant to provide an interface for accessing data from a remote SCM instance.
 *
 * The idea is to keep HTTP get requests somewhat restricted to classes which implement
 *  this interface, to reduce coupling with a given SCM throughout other classes.
 */
public interface SCMWrapper {

    /**
     * Ask the specified ip address what sort of SCM instance it is.
     * @param ip address of the server to connect to.
     * @return
     */
    public String getServerType(String ip);

    /**
     * Connect to remote SCM instance and create basic project objects with no repository info.
     * @param ip address of the server to connect to.
     * @return list of projects.
     */
    public List<BitbucketProject> getProjects(String ip);

    /**
     * For the specified project, connect to remote SCM instance and retrieve information for each repository
     *  in that project, then create a Repository object for each repo found and add to a list.
     * @param ip address of the server to connect to.
     * @param project specified project to get repos from.
     * @return list of repositories for specified project on remote server.
     */
    public HashMap<String, BitbucketRepo> getProjectRepos(String ip, Project project);

    /**
     * If a remote server supports this, it's easier to just grab all the repositories in one go.
     * Bitbucket doesn't support this without authentication via API.
     */
    public ArrayList<String> getRepos(String ip, String projectName);

    public JsonNode getRepoInfo(String ip, String repoURL);
}
