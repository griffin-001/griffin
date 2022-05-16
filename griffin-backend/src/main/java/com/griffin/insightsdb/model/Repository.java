package com.griffin.insightsdb.model;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "repository")
@Table(name = "repository")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Proxy(lazy = false)

public class Repository {

    @Id
    @SequenceGenerator(name = "repository_sequence", sequenceName = "repository_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repository_sequence")
    private Long id;

    @Column( name = "name", nullable = false)
    private String name;

    @Column(name = "timestamp", columnDefinition = "timestamp not null default current_timestamp")
    @CreationTimestamp
    private Date timestamp;


    @Lob
    @Column( name = "build")
    private byte[] build;

    @Type(type = "list-array")
    @Column(
            name = "dependency",
            columnDefinition = "text[]"
    )
    private List<String> dependency;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    public Repository(String name, byte[] build, List<String> dependency, Server server) {
        this.name = name;
        this.build = build;
        this.dependency = dependency;
        this.server = server;
    }

    public Repository() {

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getBuild() {
        return build;
    }

    public void setBuild(byte[] build) {
        this.build = build;
    }

    public List<String> getDependency() {
        return dependency;
    }

    public void setDependency(List<String> dependency) {
        this.dependency = dependency;
    }
}
