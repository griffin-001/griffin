package com.griffin.transformer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepoCheckDAO {

    //connect to database
    public  static Connection connectDatabase() throws SQLException {
        Connection conn;
        String dbURL = "jdbc:mysql://localhost:3306/griffin_main";

        String userName = "root";
        String password = "2102201060";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        conn = DriverManager.getConnection(dbURL, userName, password);

        return conn;
    }

    public static void getDepID(String groupID, String artifactID, String versionID, int status,  Connection conn){
        try{
            PreparedStatement stmt;
            ResultSet rs;

            String query = "select * from dependency where GroupID = ? and ArtifactID = ? and VersionID = ?";

            stmt = conn.prepareStatement(query);

            stmt.setString(1, groupID);
            stmt.setString(2, artifactID);
            stmt.setString(3, versionID);

            rs = stmt.executeQuery();

            if(rs.next()){
                int depID = rs.getInt(1);

                updateDepStatus(depID, status, conn);

                findAllRepo(depID, status, conn);
            }

            stmt.close();
            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateDepStatus(int depID, int status, Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "update dependency set Status = ? where ID = ?");
            stmt.setInt(1, status);
            stmt.setInt(2, depID);
            stmt.executeUpdate();

            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateRepoStatus(int repoID, int status, Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "update repository set Status = ? where ID = ?");
            stmt.setInt(1, status);
            stmt.setInt(2, repoID);
            stmt.executeUpdate();

            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void findAllRepo(int depID, int status, Connection conn) {
        try {

            PreparedStatement stmt = conn.prepareStatement(
                    "select * from map where dependency_ID = ?");

            stmt.setInt(1, depID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int repoID = rs.getInt(2);
                System.out.println("repository "+repoID+" is affected.");
                updateRepoStatus(repoID, status, conn);

                String name = getRepo(repoID, conn);
                String[] IDs = name.split(":");

                getDepID(IDs[0], IDs[1], IDs[2], status, conn);
            }

            stmt.close();
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getRepo(int repoID, Connection conn) {
        String name = null;
        try {

            PreparedStatement stmt = conn.prepareStatement(
                    "select * from repository where ID = ?");

            stmt.setInt(1, repoID);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                name = rs.getString(2);
            }

            stmt.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }


    public static void main(String[] args) {
        Connection conn = null;

        try{
            conn = connectDatabase();

            getDepID("dep1", "arti1", "1", 2, conn);
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            // close all resources;
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
