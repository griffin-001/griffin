package com.griffin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "bitbucket")
public class BitbucketProperties {

    private String protocol;
    private String apiBase;
    private List<String> servers;
    private Boolean minimalClones;
    private Boolean noClones;

    public BitbucketProperties() {
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public Boolean getMinimalClones() {
        return minimalClones;
    }

    public void setMinimalClones(Boolean minimalClones) {
        this.minimalClones = minimalClones;
    }

    public Boolean getNoClones() {
        return noClones;
    }

    public void setNoClones(Boolean noClone) {
        this.noClones = noClone;
    }
}
