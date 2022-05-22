package com.griffin.insightsdb.service;

import com.griffin.cve.CVEScanService;
import com.griffin.insightsdb.model.*;
import com.griffin.insightsdb.repository.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InsightDBService {

    ServerRepository serverRepository;
    DependencyRepository dependencyRepository;
    RepositorySnapShotRepository repositorySnapShotRepository;

    TimeStampRepository timeStampRepository;

    SnapshotDependencyRepository snapshotDependencyRepository;

    CVEScanService cveScanService;

    public InsightDBService(ServerRepository serverRepository,
                            DependencyRepository dependencyRepository,
                            RepositorySnapShotRepository repositoryRepository,
                            TimeStampRepository timeStampRepository,
                            SnapshotDependencyRepository snapshotDependencyRepository,
                            CVEScanService cveScanService) {
        this.serverRepository = serverRepository;
        this.dependencyRepository = dependencyRepository;
        this.repositorySnapShotRepository = repositoryRepository;
        this.timeStampRepository = timeStampRepository;
        this.snapshotDependencyRepository = snapshotDependencyRepository;
        this.cveScanService = cveScanService;
    }


    public void UpdateRepository(String ip, String type, String name, List<String> dependencies, String project){

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

        //check the repository is referenced by other repository or not and update the category accordingly
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


                boolean isVulnerable = false;
                try {
                     isVulnerable = cveScanService.checkVulnerability(dependency);
                }catch (Exception e){
                    e.printStackTrace();
                }


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

                        if(isVulnerable){
                            SnapshotDependency snapshotDependency =
                                    new SnapshotDependency(new_dependency, repo, "new_vulnerability");
                            snapshotDependencyRepository.save(snapshotDependency);
                        }else {
                            SnapshotDependency snapshotDependency =
                                    new SnapshotDependency(new_dependency, repo, "new_dependency");
                            snapshotDependencyRepository.save(snapshotDependency);
                        }


                    }
                } else {
                    if (snapshotDependencyRepository.
                            findByDependencyIdAndRepositorySnapShotId(new_dependency.getId(), repo.getId()) == null){
                        Long dependency_id = dependencyRepository.findByName(dependency).getId();

                        String status = null;

                        SnapshotDependency snapshotDependency = snapshotDependencyRepository.
                                findByDependencyIdAndRepositorySnapShotId(dependency_id, oldRepoId);
                        if(isVulnerable) {
                            status =  "new_vulnerability";
                            if (!repo.getVulnerabilities().contains(dependency)){
                                repo.getVulnerabilities().add(dependency);
                            }
                        }

                        if (snapshotDependency == null && repo.getVulnerabilities().size() > 0
                                && repo.getVulnerabilities().contains(dependency)) {
                            status = "unresolved";
                        } else if(snapshotDependency != null){
                            status = snapshotDependency.getStatus();
                            if (status.equals("new_dependency")){
                                status = "existing_dependency";
                            }if(status.equals("new_vulnerability") && isVulnerable){
                                status = "unresolved";
                            }
                        }

                        if (status == null) status = "new_dependency";

                        if(!status.equals("resolved")){
                            SnapshotDependency snapshotDependency1 = new SnapshotDependency(new_dependency,
                                    repo, status);
                            snapshotDependencyRepository.save(snapshotDependency1);
                        }
                    }
                }
            }

            if (oldRepoId != -1){
                List<SnapshotDependency> previous = snapshotDependencyRepository.findByRepositorySnapShotId(oldRepoId);
                for(SnapshotDependency snapshotDependency: previous){
                    if (snapshotDependency.getStatus().equals("unresolved")){
                        Dependency dependency = dependencyRepository.findById(snapshotDependency
                                .getDependency().getId()).orElse(null);
                        if (dependency != null && !dependencies.contains(dependency.getName())){
                            SnapshotDependency snapshotDependency1 =
                                    new SnapshotDependency(snapshotDependency.getDependency(), repo, "resolved");
                            snapshotDependencyRepository.save(snapshotDependency1);
                        }
                    }
                }
            }
        }
        repositorySnapShotRepository.save(repo);
    }


    //return all dependencies
    public List<Dependency> findAllDependencies() {
        List<Dependency> dependencies = dependencyRepository.findAll();
        return dependencies;
    }
  
}
