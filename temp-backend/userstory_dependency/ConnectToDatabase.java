package userstory_dependency;

import java.sql.*;

public class ConnectToDatabase {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            // register driver;
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            //get connection;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/griffin_main", "root", "123456");

            // create statement;
            stmt = conn.createStatement();

            // write query sql;
            String Querysql = "select * from repository";
            rs = stmt.executeQuery(Querysql);

            while(rs.next()){
                String ID = rs.getString("ID");
                String Name = rs.getString("Name");
                String Description = rs.getString("Description");
                String Status = rs.getString("Status");
                System.out.println(ID + "," + Name + "," + Description + "," + Status);
            }
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
            if (rs != null){
                try{
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}