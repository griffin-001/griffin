package com.griffin.insightsdb.service;

import com.griffin.insightsdb.model.*;
import com.griffin.insightsdb.repository.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InsightDBService {

    ServerRepository serverRepository;
    DependencyRepository dependencyRepository;
    RepositorySnapShotRepository repositorySnapShotRepository;

    TimeStampRepository timeStampRepository;

    SnapshotDependencyRepository snapshotDependencyRepository;

    public InsightDBService(ServerRepository serverRepository,
                            DependencyRepository dependencyRepository,
                            RepositorySnapShotRepository repositoryRepository,
                            TimeStampRepository timeStampRepository,
                            SnapshotDependencyRepository snapshotDependencyRepository) {
        this.serverRepository = serverRepository;
        this.dependencyRepository = dependencyRepository;
        this.repositorySnapShotRepository = repositoryRepository;
        this.timeStampRepository = timeStampRepository;
        this.snapshotDependencyRepository = snapshotDependencyRepository;
    }


    public void UpdateProject(String ip, String type, String name, List<String> dependencies, String project){

        //find the latest timestamps and second-latest timestamp
        List<TimeStamp> timestamps = timeStampRepository.findAllByOrderByTimestampDsc();
        TimeStamp latest = timestamps.get(0);
        TimeStamp second_latest = null;
        Long current_id;

        if (timestamps.size() > 1){
            second_latest = timestamps.get(1);
        }

        //get the corresponding server
        Server server = null;

        for (Server s : latest.getServer()){
            if(s.getIp().equals(ip)){
                server = s;
                break;
            }
        }

        //create server if server not found, then create new snapshot on it
        if (server == null){
            Server new_server = new Server(ip, type, latest);
            serverRepository.save(new_server);
            RepositorySnapShot repository = new RepositorySnapShot(name, new_server, project);
            new_server.getRepository().add(repository);
            repositorySnapShotRepository.save(repository);
            current_id = repository.getId();
            latest.getServer().add(new_server);
            serverRepository.save(new_server);
            timeStampRepository.save(latest);
        }else{
            RepositorySnapShot repository = new RepositorySnapShot(name, server, project);
            server.getRepository().add(repository);
            serverRepository.save(server);
            repositorySnapShotRepository.save(repository);
            current_id = repository.getId();
        }

        Dependency existing_dependency = dependencyRepository.findByName(name);


        //check the project is referenced by other project or not and update the category accordingly
        if (existing_dependency == null){
            Dependency new_dependency = new Dependency(name, "normal", "external");
            dependencyRepository.save(new_dependency);
        }else {
            existing_dependency.setCategory("internal");
            dependencyRepository.save(existing_dependency);
        }

        RepositorySnapShot repo = repositorySnapShotRepository.findById(current_id).orElse(null);

        //add dependency
        for(String dependency: dependencies){
            Dependency new_dependency;
            if(!dependencyRepository.existsByName(dependency)){
                new_dependency = new Dependency(dependency, "normal", "external");
                dependencyRepository.save(new_dependency);
            }else {
                new_dependency = dependencyRepository.findByName(dependency);
            }

            //find the relationship inbetween snapshot and dependency in last timestamp
            if(second_latest == null){
                SnapshotDependency snapshotDependency = new SnapshotDependency(new_dependency, repo, "new_dependency");
                snapshotDependencyRepository.save(snapshotDependency);
            }else {
                Long oldDependencyId = (long) -1;
                List<RepositorySnapShot> repos = repositorySnapShotRepository.findByName(name);

                for(RepositorySnapShot repositorySnapShot: repos){
                    if (repositorySnapShot.getServer().getTimeStamp().getTimestamp()
                            .compareTo(second_latest.getTimestamp()) == 0){
                        oldDependencyId = repositorySnapShot.getId();
                        break;
                    }
                }

                Long dependency_id = dependencyRepository.findByName(dependency).getId();
                String status = snapshotDependencyRepository.
                        findByDependencyIdAndRepositorySnapShotId(dependency_id, oldDependencyId).getStatus();

                if (status == null && repo.getVulnerabilities().size() > 0
                        && repo.getVulnerabilities().contains(dependency)){
                    status = "unresolved";
                }
                SnapshotDependency snapshotDependency = new SnapshotDependency(new_dependency,
                        repo, Objects.requireNonNullElse(status, "new_dependency"));
                snapshotDependencyRepository.save(snapshotDependency);
            }
        }
    }



    //return a hashmap with key is a timestamp and value is a repository snapshot for the latest 2 snapshot that have
    // the given repository name
    public Map<TimeStamp, RepositorySnapShot> getDependenciesChanges(String name){
        List<TimeStamp> timestamps = timeStampRepository.findAllByOrderByTimestampDsc();
        if(timestamps.size() == 0){
            return null;
        }
        List<RepositorySnapShot> repositorySnapShots = repositorySnapShotRepository.findByName(name);

        if (timestamps.size() == 1){
            if(repositorySnapShots.isEmpty()){
                return null;
            }else {
                Map<TimeStamp, RepositorySnapShot> res =  new HashMap<>();
                res.put(timestamps.get(0), repositorySnapShots.get(0));
                return res;
            }

        } else {
            Map<TimeStamp, RepositorySnapShot> res =  new HashMap<>();
            for(RepositorySnapShot repositorySnapShot: repositorySnapShots){
                if (repositorySnapShot.getServer().getTimeStamp().
                        getTimestamp().compareTo((timestamps.get(0).getTimestamp()))==0){
                    res.put(timestamps.get(0), repositorySnapShot);
                } else if (repositorySnapShot.getServer().getTimeStamp().
                        getTimestamp().compareTo((timestamps.get(1).getTimestamp()))==0) {
                    res.put(timestamps.get(1), repositorySnapShot);
                }
            }
            return res;
        }
    }

}
