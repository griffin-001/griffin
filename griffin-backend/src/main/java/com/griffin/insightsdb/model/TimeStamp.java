package com.griffin.insightsdb.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "timestamp")
@Table(name = "timestamp")
public class TimeStamp {

    @Id
    @SequenceGenerator(name = "timestamp_sequence", sequenceName = "timestamp_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timestamp_sequence")
    private Long id;

    @Column(name = "timestamp", columnDefinition = "timestamp not null default current_timestamp")
    @CreationTimestamp
    private Date timestamp;

    @OneToMany(mappedBy = "timestamp", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Server> server = new HashSet<>();

    public TimeStamp(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Set<Server> getServer() {
        return server;
    }

    public void setServer(Set<Server> server) {
        this.server = server;
    }
}
