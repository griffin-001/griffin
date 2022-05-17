package com.griffin.insightsdb.service;

import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.model.Server;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositoryRepository;
import com.griffin.insightsdb.repository.ServerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.util.LinkedList;
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

    /**
     *
     * @param ip ip of the server
     * @param type type of the server
     * @param name name of the repo
     * @param dependencies dependency list
     * @param build build file, should be byte[]
     */
    public void UpdateProject(String ip, String type, String name, List<String> dependencies, byte[] build, String project){

        //find server by ip
        Server server = serverRepository.findByIp(ip);

        //if server not exist, add new server to database, add create a new repo and add to server
        if (server == null){
            Server new_server = new Server(ip, type);
            serverRepository.save(new_server);
            Repository repository = new Repository(name, build, dependencies, new_server, project);
            repositoryRepository.save(repository);
        }else{
            Repository repository = new Repository(name, build, dependencies, server, project);
            repositoryRepository.save(repository);
        }


        //check if new repo is used as dependency by other repository before, u
        // update the category to internal if it iss true
        Dependency existing_dependency = dependencyRepository.findByName(name);
        if (existing_dependency == null){
            Dependency new_dependency = new Dependency(name, "normal", "external");
            dependencyRepository.save(new_dependency);
        }else {

            existing_dependency.setCategory("internal");
            dependencyRepository.save(existing_dependency);
        }

        //check dependency list and add new dependency to the DEPENDENCY table if not present before
        for(String dependency: dependencies){
            if(!dependencyRepository.existsByName(dependency)){
                Dependency new_dependency = new Dependency(dependency, "normal", "external");
                dependencyRepository.save(new_dependency);
            }
        }

    }

    public List<Repository> getDependenciesChanges(String name){
        List<Repository> repositories = repositoryRepository.findAllByNameOrderByTimestampDesc(name);
        if (repositories.size() == 0){
            return null;
        }else if(repositories.size() <= 2){
            return repositories;
        }else {
            List<Repository> res = new LinkedList<>();
            res.add(repositories.get(0));
            res.add(repositories.get(1));
            return res;
        }
    }
}
