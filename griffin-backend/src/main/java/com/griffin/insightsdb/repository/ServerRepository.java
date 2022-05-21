package com.griffin.insightsdb.repository;

import com.griffin.insightsdb.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


public interface ServerRepository extends JpaRepository<Server, Long> {

    List<Server> findByIp(String ip);

}
