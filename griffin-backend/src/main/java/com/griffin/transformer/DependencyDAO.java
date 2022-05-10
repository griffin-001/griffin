package com.griffin.transformer;

import org.yaml.snakeyaml.events.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DependencyDAO {

    //initialize connection to database
    public static Connection initializeDatabase() throws SQLException {

        Connection conn;
        String dbURL = "jdbc:mysql://localhost:3306/griffin_main";

        //please ensure the username and password are correct
        String userName = "root";
        String password = "2102201060";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        conn = DriverManager.getConnection(dbURL, userName, password);

        return conn;
    }


    //add a project into database along with dependencies and update map
    public static void insertProject(String projectName, List<String> dependencies) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = initializeDatabase();
            int ID = checkRepository(projectName, conn);
            //get connection;
            if(ID == -1){
                System.out.println("adding non-existing project");

                String query = "insert into repository values(?, ?, ?, ?)";

                // create statement;
                stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                // write query sql;
                stmt.setNull(1, Types.NULL);
                stmt.setString(2, projectName);
                stmt.setString(3, "a description");
                stmt.setInt(4, 1);
                stmt.executeUpdate();

                //get the ID for newly added project
                rs = stmt.getGeneratedKeys();
                rs.next();

            }
            System.out.println("adding existing project");

            //insert all dependencies
            for (String dependency: dependencies) insertDependency(dependency, conn, ID);

            //update the category of the project in dependency table
            updateCategory(projectName, conn);
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            // close all resources;
            if (conn != null){
                try{
                    conn.close();
                } catch (SQLException e){
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
        }
    }

    //check if the repo already exist
    public static int checkRepository(String repoName, Connection conn) throws SQLException {

        int repoID = -1;
        try {

            PreparedStatement stmt = conn.prepareStatement("select ID from repository where Name= ?");
            stmt.setString(1, repoName);
            ResultSet result;
            result = stmt.executeQuery();
            if (result.next()){
                repoID = result.getInt(1);
            }


            stmt.close();
            result.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return repoID;
    }

    //check if the dependency already exist
    public static Boolean checkDependency(String dependencyName, Connection conn) throws SQLException {

        boolean isDependency = false;
        String[] IDs = dependencyName.split(":");
        try {

            PreparedStatement stmt = conn.prepareStatement(
                    "select * from dependency where GroupID = ? and ArtifactID = ? and VersionID = ?");

            stmt.setString(1, IDs[0]);
            stmt.setString(2, IDs[1]);
            stmt.setString(3, IDs[2]);
            ResultSet result = stmt.executeQuery();
            isDependency = result.next();

            stmt.close();
            result.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return isDependency;
    }


    //insert the relationship between project and dependency to database
    public static void insertMap(int repoID, int dependencyID, Connection conn){
        try {

            PreparedStatement stmt = conn.prepareStatement(
                    "insert into map values(?, ?, ?)");
            stmt.setNull(1, Types.NULL);
            stmt.setInt(2, repoID);
            stmt.setInt(3, dependencyID);
            stmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //insert a new dependency to database
    public static void insertDependency(String dependency, Connection conn, int repoID){
        try {
            if (!checkDependency(dependency, conn)){
                String[] IDs = dependency.split(":");
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into dependency values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setNull(1, Types.NULL);
                stmt.setString(2, IDs[0]);
                stmt.setString(3, IDs[1]);
                stmt.setString(4, IDs[2]);
                stmt.setInt(5, 2);
                stmt.setInt(6, 1);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                int dependencyID = rs.getInt(1);
                insertMap(repoID, dependencyID, conn);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //update the category of the project
    public static void updateCategory(String projectName, Connection conn){
        try {
            String[] IDs = projectName.split(":");
            String[] info = new String[3];
            for (int i = 0; i < info.length; i++){

            }
            PreparedStatement stmt = conn.prepareStatement(
                    "update dependency set Category = ? where GroupID = ? and ArtifactID = ? and VersionID = ?");
            stmt.setInt(1, 1);
            stmt.setString(2, projectName);
            stmt.setString(3, projectName);
            stmt.setString(4, projectName);
            stmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<String> dependencies = new ArrayList<>(
                List.of("junit:junit:3.8.1",
                        "org.hibernate:hibernate-core:3.6.3.Final"));

        insertProject("org.example:jpademo:1.0", dependencies);
    }


}
