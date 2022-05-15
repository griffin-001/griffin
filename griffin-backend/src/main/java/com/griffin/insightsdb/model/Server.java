package com.griffin.insightsdb.model;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "server")
@Table(name = "server")
@Proxy(lazy = false)
public class Server {

    @Id
    @SequenceGenerator(name = "server_sequence", sequenceName = "server_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_sequence")
    private Long id;

    @Column( name = "ip", nullable = false, unique = true)
    private String ip;

    @Column( name = "type", nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "server", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Repository> repository;

    public Server(String ip, String type) {
        this.ip = ip;
        this.type = type;
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
}
