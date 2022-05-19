package com.griffin.insightsdb.model;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "repository_snapshot")
@Table(name = "repository_snapshot")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Proxy(lazy = false)

public class RepositorySnapShot {

    @Id
    @SequenceGenerator(name = "repository_sequence", sequenceName = "repository_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repository_sequence")
    private Long id;

    @Column( name = "name", nullable = false)
    private String name;

    @Column(name = "project", nullable = false)
    private String project;

    @Lob
    @Column( name = "build")
    private byte[] build;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "repository_dependency", joinColumns = { @JoinColumn(name = "repository_id") },
            inverseJoinColumns = { @JoinColumn(name = "dependency_id") })
    Set<Dependency> dependencies = new HashSet<>();


    public RepositorySnapShot(String name, byte[] build, Server server, String project) {
        this.name = name;
        this.build = build;
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

    public byte[] getBuild() {
        return build;
    }

    public void setBuild(byte[] build) {
        this.build = build;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Set<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<Dependency> dependencies) {
        this.dependencies = dependencies;
    }


}
