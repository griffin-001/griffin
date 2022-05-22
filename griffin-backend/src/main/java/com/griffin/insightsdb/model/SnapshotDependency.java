package com.griffin.insightsdb.model;

import javax.persistence.*;

@Entity(name = "snapshot_dependency")
@Table(name = "snapshot_dependency")
public class SnapshotDependency {

    @Id
    @SequenceGenerator(name = "snapshot_dependency_sequence",
            sequenceName = "snapshot_dependency_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "snapshot_dependency_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "snapshot_id")
    private RepositorySnapShot repositorySnapShot;

    @ManyToOne
    @JoinColumn(name = "dependency_id")
    private Dependency dependency;

    @Column( name = "status", nullable = false)
    private String status;

    public SnapshotDependency(Dependency dependency, RepositorySnapShot repositorySnapShot, String status){
        this.dependency = dependency;
        this.repositorySnapShot = repositorySnapShot;
        this.status = status;
    }

    public RepositorySnapShot getRepositorySnapShot() {
        return repositorySnapShot;
    }

    public void setRepositorySnapShot(RepositorySnapShot repositorySnapShot) {
        this.repositorySnapShot = repositorySnapShot;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SnapshotDependency() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
