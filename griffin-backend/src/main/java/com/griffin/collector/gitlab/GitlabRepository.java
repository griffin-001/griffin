package com.griffin.collector.gitlab;

import com.griffin.collector.Repository;

import java.io.File;
import java.util.List;

public class GitlabRepository implements Repository {
    @Override
    public List<File> getBuildFiles() {
        return null;
    }
}
