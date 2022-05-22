package com.griffin.collector;

import java.io.File;
import java.util.List;

public interface Repo {

    public List<File> getBuildFiles();

    public String getName();

    public String getIp();
}
