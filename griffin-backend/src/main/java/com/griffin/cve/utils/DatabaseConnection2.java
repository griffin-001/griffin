package com.griffin.cve.utils;

import com.griffin.cve.Vulnerability;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;

@Component
public class DatabaseConnection2 {
    //connect the data base and run for a specific list of dep
    private final Environment environment;
    public DatabaseConnection2(Environment environment){
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

    public Vulnerability searchVersion(String dependency) {
        Vulnerability vulnerability = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HashMap<Integer,Vulnerability> versionMap = new HashMap<>();
        String groupId = dependency.trim().split(":")[0];
        String artifactId = dependency.trim().split(":")[1];
        String version = dependency.trim().split(":")[2];

        try {
            conn = initializeDV();
            String query = "select cve_dep_vulnerability.id as id,version_start,version_end,cve_id,description from cve_dependency inner join cve_dep_vulnerability on cve_dependency.id = cve_dep_vulnerability.dep_id where cve_dependency.group_id = ? and cve_dependency.artifact_id = ?";
            // create statement;
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            // write query sql;
            stmt.setString(1,groupId);
            stmt.setString(2,artifactId);

            rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String version_start = rs.getString("version_start");
                String version_end = rs.getString("version_end");
                String cve_id = rs.getString("cve_id");
                String description = rs.getString("description");

                Vulnerability vulnObject = new Vulnerability(cve_id, description,
                        version_start, version_end);
                versionMap.put(id, vulnObject);
            }

            vulnerability= searchVul(versionMap,version);
            System.out.println(artifactId+" "+version+" "+vulnerability);

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
        return vulnerability;
    }

    /**
     * TODO handle one version having more than one CVE
     * TODO check if handles x.x.xx and x.x.x correctly
     * TODO check if x.x.x-jre is parseable
     * @param versionMap
     * @param version
     * @return vulnerability description if exists; else null
     */
    private Vulnerability searchVul(HashMap<Integer, Vulnerability> versionMap, String version) {
        for(Integer key: versionMap.keySet()){
            String begin = versionMap.get(key).getVersionStart();
            String end = versionMap.get(key).getVersionEnd();

            if(null!=end){
                if(version.compareTo(begin)>=0 && version.compareTo(end)<=0){
                    return versionMap.get(key);
                }
            }else{
                //end == null
                if(version.compareTo(begin)==0){
                    return versionMap.get(key);
                }
            }
        }
        return null;
    }
}
