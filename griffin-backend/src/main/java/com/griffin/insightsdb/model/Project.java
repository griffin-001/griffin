package com.griffin.insightsdb.model;

import javax.persistence.*;

@Entity(name = "project")
@Table(name = "project")
public class Project {

    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    private Long id;
    @Column(
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    public Project(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public Project() {

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
}

