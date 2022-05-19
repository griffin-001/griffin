package com.griffin.insightsdb.service;

import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.RepositorySnapShot;
import com.griffin.insightsdb.model.Server;
import com.griffin.insightsdb.model.TimeStamp;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositorySnapShotRepository;
import com.griffin.insightsdb.repository.ServerRepository;
import com.griffin.insightsdb.repository.TimeStampRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InsightDBService {

    ServerRepository serverRepository;
    DependencyRepository dependencyRepository;
    RepositorySnapShotRepository repositoryRepository;

    TimeStampRepository timeStampRepository;

    public InsightDBService(ServerRepository serverRepository,
                            DependencyRepository dependencyRepository,
                            RepositorySnapShotRepository repositoryRepository, TimeStampRepository timeStampRepository) {
        this.serverRepository = serverRepository;
        this.dependencyRepository = dependencyRepository;
        this.repositoryRepository = repositoryRepository;
        this.timeStampRepository = timeStampRepository;
    }

    public void UpdateProject(String ip, String type, String name, List<String> dependencies, byte[] build, String project){

        TimeStamp latest = timeStampRepository.findAllByOrderByTimestampAsc().get(0);
        if(latest == null){
            latest = new TimeStamp();
            timeStampRepository.save(latest);
        }
        Server server = latest.getServer().stream()
                .filter(s->s.getIp().equals(ip)).collect(Collectors.toSet()).iterator().next();

        if (server == null){
            Server new_server = new Server(ip, type, latest);
            serverRepository.save(new_server);
            RepositorySnapShot repository = new RepositorySnapShot(name, build, new_server, project);
            repositoryRepository.save(repository);
        }else{
            RepositorySnapShot repository = new RepositorySnapShot(name, build, server, project);
            repositoryRepository.save(repository);
        }

        Dependency existing_dependency = dependencyRepository.findByName(name);

        if (existing_dependency == null){
            Dependency new_dependency = new Dependency(name, "normal", "external");
            dependencyRepository.save(new_dependency);
        }else {
            existing_dependency.setCategory("internal");
            dependencyRepository.save(existing_dependency);
        }

        RepositorySnapShot repo = repositoryRepository.findByName(name);

        for(String dependency: dependencies){
            if(!dependencyRepository.existsByName(dependency)){
                Dependency new_dependency = new Dependency(dependency, "normal", "external");
                dependencyRepository.save(new_dependency);
                repo.getDependencies().add(new_dependency);
                new_dependency.getRepositorySnapShotSet().add(repo);
            }else {
                Dependency exist_dependency = dependencyRepository.findByName(dependency);
                repo.getDependencies().add(exist_dependency);
                exist_dependency.getRepositorySnapShotSet().add(repo);
            }

        }
    }

    /*
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

     */
}
