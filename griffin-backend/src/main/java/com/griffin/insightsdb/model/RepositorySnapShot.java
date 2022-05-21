package com.griffin.insightsdb.model;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.*;

@Entity(name = "repository_snapshot")
@Table(name = "repository_snapshot")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class RepositorySnapShot {

    @Id
    @SequenceGenerator(name = "repository_sequence", sequenceName = "repository_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repository_sequence")
    private Long id;

    @Column( name = "name", nullable = false)
    private String name;

    @Column(name = "project", nullable = false)
    private String project;



    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "repositorySnapShot")
    Set<SnapshotDependency> snapshotDependency = new HashSet<>();


    @Type(type = "list-array")
    @Column(name = "vulnerablity_history", columnDefinition = "text[]")
    private List<String> vulnerabilities = new ArrayList<>();


    public RepositorySnapShot(String name, Server server, String project) {
        this.name = name;
        this.server = server;
        this.project = project;
    }

    public RepositorySnapShot() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<SnapshotDependency> getSnapshotDependency() {
        return snapshotDependency;
    }

    public void setSnapshotDependency(Set<SnapshotDependency> snapshotDependency) {
        this.snapshotDependency = snapshotDependency;
    }

    public List<String> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<String> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
}
