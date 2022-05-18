package com.griffin.cve.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DependencyBefore {
    private final Environment environment;
    public DependencyBefore(Environment environment){
        this.environment = environment;
    }

    public Connection initializeDV() throws SQLException {
        Connection conn;
        String dbURL = environment.getProperty("spring.datasource.url");
        //please ensure the username and password are correct
        String userName = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        conn = DriverManager.getConnection(dbURL, userName, password);
        conn.setAutoCommit(false);
        return conn;
    }

    public List<String> queryBefore(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String dependencyString = "";
        ArrayList<String> list = new ArrayList<>();

        try {
            conn = initializeDV();

            String queryBefore = "select dependency from repository where name = ? order by timestamp DESC limit 1 offset 1";
            stmt = conn.prepareStatement(queryBefore, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            while(rs.next()){
                //some problems here，  db store test[]， maybe true
                dependencyString = rs.getString("dependency");
            }
            String[] dependencies = dependencyString.trim().replaceAll("\\{","").replaceAll("\\}","").split(",");
            for (String dependency : dependencies) {
                list.add(dependency);
            }


        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            // close all resources;
            if (rs != null){
                try{
                    rs.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try{
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
