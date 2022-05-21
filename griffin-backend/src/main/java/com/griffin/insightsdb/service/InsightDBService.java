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
        List<TimeStamp> timestamps = timeStampRepository.findAllByOrderByTimestampDesc();
        TimeStamp latest = timestamps.get(0);



        if(latest == null){
            TimeStamp timeStamp = new TimeStamp();
            timeStampRepository.save(timeStamp);
            latest = timeStamp;
        }
        
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


            RepositorySnapShot repository = null;
            for(RepositorySnapShot repositorySnapShot: server.getRepository()){
                if (repositorySnapShot.getProject().equals(project) && repositorySnapShot.getName().equals(name)){
                    repository = repositorySnapShot;
                    break;
                }
            }
            if (repository == null){
                repository = new RepositorySnapShot(name, server, project);
                server.getRepository().add(repository);
                repositorySnapShotRepository.save(repository);
                serverRepository.save(server);
            }
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

        List<RepositorySnapShot> repos = repositorySnapShotRepository.findByName(name);

        Long oldRepoId = (long) -1;



        if(second_latest != null){
            for(RepositorySnapShot repositorySnapShot: repos){
                if (repositorySnapShot.getServer().getTimeStamp().getTimestamp()
                        .compareTo(second_latest.getTimestamp()) == 0
                &&repositorySnapShot.getProject().equals(repo.getProject())){
                    oldRepoId = repositorySnapShot.getId();
                    break;
                }
            }
        }

        repositorySnapShotRepository.findById(oldRepoId).
                ifPresent(oldRepo -> repo.getVulnerabilities().addAll(oldRepo.getVulnerabilities()));

        repositorySnapShotRepository.save(repo);



        //add dependency
        if (dependencies != null) {

            for (String dependency : dependencies) {
                Dependency new_dependency;

                if (!dependencyRepository.existsByName(dependency)) {
                    new_dependency = new Dependency(dependency, "normal", "external");
                    dependencyRepository.save(new_dependency);
                } else {
                    new_dependency = dependencyRepository.findByName(dependency);
                }

                //find the relationship in between snapshot and dependency in last timestamp
                if (second_latest == null) {
                    if (snapshotDependencyRepository.
                            findByDependencyIdAndRepositorySnapShotId(new_dependency.getId(), repo.getId()) == null){

                        SnapshotDependency snapshotDependency =
                                new SnapshotDependency(new_dependency, repo, "new_dependency");

                        snapshotDependencyRepository.save(snapshotDependency);
                    }
                } else {
                    if (snapshotDependencyRepository.
                            findByDependencyIdAndRepositorySnapShotId(new_dependency.getId(), repo.getId()) == null){
                        Long dependency_id = dependencyRepository.findByName(dependency).getId();

                        String status = null;

                        SnapshotDependency snapshotDependency = snapshotDependencyRepository.
                                findByDependencyIdAndRepositorySnapShotId(dependency_id, oldRepoId);

                        if (snapshotDependency == null && repo.getVulnerabilities().size() > 0
                                && repo.getVulnerabilities().contains(dependency)) {
                            status = "unresolved";
                        } else if(snapshotDependency != null){
                            status = snapshotDependency.getStatus();
                            if (status.equals("new_dependency")){
                                status = "existing_dependency";
                            }
                        }

                        SnapshotDependency snapshotDependency1 = new SnapshotDependency(new_dependency,
                                repo, Objects.requireNonNullElse(status, "new_dependency"));
                        snapshotDependencyRepository.save(snapshotDependency1);
                    }
                }
            }
        }
    }



    //return a hashmap with key is a timestamp and value is a repository snapshot for the latest 2 snapshot that have
    // the given repository name
    public Map<TimeStamp, RepositorySnapShot> getDependenciesChanges(String name){
        List<TimeStamp> timestamps = timeStampRepository.findAllByOrderByTimestampDesc();
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
            RepositorySnapShot newSnapshot = null, oldSnapshot = null;
            Map<TimeStamp, RepositorySnapShot> res =  new HashMap<>();
            for(RepositorySnapShot repositorySnapShot: repositorySnapShots){
                if (repositorySnapShot.getServer().getTimeStamp().
                        getTimestamp().compareTo((timestamps.get(0).getTimestamp()))==0){
                    newSnapshot = repositorySnapShot;
                    res.put(timestamps.get(0), repositorySnapShot);
                } else if (repositorySnapShot.getServer().getTimeStamp().
                        getTimestamp().compareTo((timestamps.get(1).getTimestamp()))==0) {
                    oldSnapshot = repositorySnapShot;
                    res.put(timestamps.get(1), repositorySnapShot);
                }
            }
            return res;
        }
    }

}
