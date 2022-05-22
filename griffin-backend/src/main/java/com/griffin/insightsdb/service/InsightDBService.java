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


    /**
     *
     * @param ip ip of the server
     * @param type type of the server
     * @param name name of the repository
     * @param dependencies dependencies
     * @param project project that the repository belongs to
     */
    public void UpdateRepository(String ip, String type, String name, List<String> dependencies, String project){

        System.out.println("----------------------------------------------------");
        //find the latest timestamps and second-latest timestamp
        List<TimeStamp> timestamps = timeStampRepository.findAllByOrderByTimestampDesc();
        TimeStamp latestTimeStamp = timestamps.get(0);

        
        TimeStamp secondLatestTimeStamp = null;
        Long currentRepoId;

        //find the time stamp prior to the latest one
        if (timestamps.size() > 1){
            secondLatestTimeStamp = timestamps.get(1);
        }

        //get the corresponding server
        Server server = null;

        for (Server s : latestTimeStamp.getServer()){
            if(s.getIp().equals(ip)){
                server = s;
                break;
            }
        }


        //create server if server not found, then create new snapshot on it
        if (server == null){

            Server new_server = new Server(ip, type, latestTimeStamp);
            serverRepository.save(new_server);

            RepositorySnapShot repository = new RepositorySnapShot(name, new_server, project);
            new_server.getRepository().add(repository);
            repositorySnapShotRepository.save(repository);

            currentRepoId = repository.getId();
            latestTimeStamp.getServer().add(new_server);

            serverRepository.save(new_server);
            timeStampRepository.save(latestTimeStamp);
        }else{

            //if server exist, create or get the repository that need update
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
            currentRepoId = repository.getId();
        }



        Dependency existingDependency = dependencyRepository.findByName(name);

        //check the repository is referenced by other repository or not and update the category accordingly
        if (existingDependency == null){

            Dependency new_dependency = new Dependency(name, "normal", "external");
            dependencyRepository.save(new_dependency);
        }else {
            existingDependency.setCategory("internal");
            dependencyRepository.save(existingDependency);
        }

        RepositorySnapShot currentRepo = repositorySnapShotRepository.findById(currentRepoId).orElse(null);

        List<RepositorySnapShot> repos = repositorySnapShotRepository.findByName(name);

        Long oldRepoId = (long) -1;

        //find the current repository in last time stamp
        if(secondLatestTimeStamp != null){
            for(RepositorySnapShot repositorySnapShot: repos){
                if (repositorySnapShot.getServer().getTimeStamp().getTimestamp()
                        .compareTo(secondLatestTimeStamp.getTimestamp()) == 0
                &&repositorySnapShot.getProject().equals(currentRepo.getProject())){
                    oldRepoId = repositorySnapShot.getId();
                    break;
                }
            }
        }

        //inheriting vulnerability history
        RepositorySnapShot prevRepo = repositorySnapShotRepository.findById(oldRepoId).orElse(null);
        if (prevRepo != null){
            for(String vulnerabilities: prevRepo.getVulnerabilities()){
                if (!currentRepo.getVulnerabilities().contains(vulnerabilities)){
                    currentRepo.getVulnerabilities().add(vulnerabilities);
                }
            }
        }

        repositorySnapShotRepository.save(currentRepo);

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
                if (secondLatestTimeStamp == null) {
                    if (snapshotDependencyRepository.
                            findByDependencyIdAndRepositorySnapShotId(new_dependency.getId(), currentRepo.getId()) == null){

                        SnapshotDependency snapshotDependency;
                        if(isVulnerable){
                            snapshotDependency = new SnapshotDependency(new_dependency, currentRepo, "new_vulnerability");
                        }else {
                            snapshotDependency = new SnapshotDependency(new_dependency, currentRepo, "new_dependency");
                        }
                        snapshotDependencyRepository.save(snapshotDependency);


                    }
                } else {
                    if (snapshotDependencyRepository.
                            findByDependencyIdAndRepositorySnapShotId(new_dependency.getId(), currentRepo.getId()) == null){
                        Long dependency_id = dependencyRepository.findByName(dependency).getId();

                        String status = null;

                        SnapshotDependency prevSnapshotDependency = snapshotDependencyRepository.
                                findByDependencyIdAndRepositorySnapShotId(dependency_id, oldRepoId);


                        if (prevSnapshotDependency == null) {
                            if(currentRepo.getVulnerabilities().size() > 0
                                    && currentRepo.getVulnerabilities().contains(dependency)){
                                status = "unresolved";
                            }else if(isVulnerable){
                                status = "new_vulnerability";
                            }else {
                                status = "new_dependency";
                            }

                        } else {
                            String preStatus = prevSnapshotDependency.getStatus();

                            if(isVulnerable){
                                status = "new_vulnerability";
                            }else if (preStatus.equals("new_dependency")){
                                status = "existing_dependency";
                            }else if(preStatus.equals("existing_dependency")){
                                status = "existing_dependency";
                            }

                            if(preStatus.equals("new_vulnerability") && isVulnerable){
                                status = "unresolved";
                            }if(preStatus.equals("unresolved") && isVulnerable){
                                status = "unresolved";
                            }if(preStatus.equals("resolved")){
                                status = "exclude";
                            }
                        }


                        if(status != null && !status.equals("exclude")){
                            SnapshotDependency snapshotDependency1 = new SnapshotDependency(new_dependency,
                                    currentRepo, status);
                            snapshotDependencyRepository.save(snapshotDependency1);
                        }
                    }
                }

                if(isVulnerable) {
                    if (!currentRepo.getVulnerabilities().contains(dependency)){
                        currentRepo.getVulnerabilities().add(dependency);
                    }
                }
            }
            repositorySnapShotRepository.save(currentRepo);

            if (oldRepoId != -1){
                List<SnapshotDependency> previous = snapshotDependencyRepository.findByRepositorySnapShotId(oldRepoId);
                for(SnapshotDependency prevRecord: previous){
                    if (prevRecord.getStatus().equals("unresolved")){
                        if (!dependencies.contains(prevRecord.getDependency().getName())){
                            SnapshotDependency resolved =
                                    new SnapshotDependency(prevRecord.getDependency(), currentRepo, "resolved");
                            snapshotDependencyRepository.save(resolved);
                        }
                    }
                }
            }
        }
    }


    //return all dependencies
    public List<Dependency> findAllDependencies() {
        List<Dependency> dependencies = dependencyRepository.findAll();
        return dependencies;
    }
  
}
