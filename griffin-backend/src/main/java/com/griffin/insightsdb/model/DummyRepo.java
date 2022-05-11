package com.griffin.insightsdb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("REPOSITORY")
public class DummyRepo {

    public DummyRepo(){

    }

    @Id
    private Integer ID;
    private String name;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
