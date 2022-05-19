package com.griffin.insightsdb.model;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "server")
@Table(name = "server")
public class Server {

    @Id
    @SequenceGenerator(name = "server_sequence", sequenceName = "server_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_sequence")
    private Long id;

    @Column( name = "ip", nullable = false, unique = true)
    private String ip;

    @Column( name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "server", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RepositorySnapShot> repository = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "timestamp_id", nullable = false)
    private TimeStamp timestamp;

    public Server(String ip, String type, TimeStamp timeStamp) {
        this.ip = ip;
        this.type = type;
        this.timestamp = timeStamp;
    }


    public Server() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<RepositorySnapShot> getRepository() {
        return repository;
    }

    public void setRepository(Set<RepositorySnapShot> repository) {
        this.repository = repository;
    }

    public TimeStamp getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        this.timestamp = timeStamp;
    }
}
