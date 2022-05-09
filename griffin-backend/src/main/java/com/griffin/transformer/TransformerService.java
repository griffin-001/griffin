package com.griffin.transformer;

import com.griffin.collector.Project;

import java.util.List;

public class TransformerService {
    private List<Project> projects;

    public TransformerService(List<Project> projects) {
        this.projects = projects;
    }

    public void transform() {
        /* TODO:
        for each project in the list:
            for each repository in the project:
                DO stuff
        */
    }
}
