package com.griffin.insightsdb.service;

import com.griffin.insightsdb.model.Project;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.MapRepository;
import com.griffin.insightsdb.repository.ProjectRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public class InsightDBService {

    DependencyRepository dependencyRepository;
    MapRepository mapRepository;
    ProjectRepository projectRepository;

    public InsightDBService(DependencyRepository dependencyRepository, MapRepository mapRepository, ProjectRepository projectRepository) {
        this.dependencyRepository = dependencyRepository;
        this.mapRepository = mapRepository;
        this.projectRepository = projectRepository;
    }

    public void UpdateProject(String name, List<String> dependencies){
        List<Project> projects = projectRepository.findByName(name);
    }

    @Query(value = "select Id from article", nativeQuery = true)
    List<String> findAllCategories() {
        return null;
    }

}
