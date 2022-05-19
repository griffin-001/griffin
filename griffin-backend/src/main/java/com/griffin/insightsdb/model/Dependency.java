package com.griffin.insightsdb.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "dependency")
@Table(name = "dependency")
public class Dependency {

    @Id
    @SequenceGenerator(name = "dependency_sequence", sequenceName = "dependency_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dependency_sequence")
    private Long id;

    @Column( name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "dependencies")
    Set<RepositorySnapShot> repositorySnapShotSet = new HashSet<>();

    public Dependency(String name, String status, String category) {
        this.name = name;
        this.status = status;
        this.category = category;
    }

    public Dependency() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<RepositorySnapShot> getRepositorySnapShotSet() {
        return repositorySnapShotSet;
    }

    public void setRepositorySnapShotSet(Set<RepositorySnapShot> repositorySnapShotSet) {
        this.repositorySnapShotSet = repositorySnapShotSet;
    }
}
