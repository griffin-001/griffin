package com.griffin.insightsdb.model;

import javax.persistence.*;

@Entity(name = "relation_map")
@Table(name = "relation_map")
public class RelationMap {

    @Id
    @SequenceGenerator(name = "map_sequence", sequenceName = "map_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "map_sequence")
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "dependency_id", nullable = false)
    private Long dependencyId;

    @Column(name = "status", nullable = false)
    private String status;

    public RelationMap(Long projectId, Long dependencyId, String status) {
        this.projectId = projectId;
        this.dependencyId = dependencyId;
        this.status = status;
    }

    public RelationMap() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long prjectId) {
        this.projectId = prjectId;
    }

    public Long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(Long dependencyId) {
        this.dependencyId = dependencyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
