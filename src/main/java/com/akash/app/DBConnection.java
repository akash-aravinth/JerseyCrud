package com.akash.app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection connection;
    private static String url = "jdbc:postgresql://localhost:5432/crud";
    private static String userName = "postgres";
    private static String password = "root";
    public static Connection getConnection(){
        if (connection == null){
            System.out.println("Connection Called");
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url,userName,password);
                System.out.println("Created Connection");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return connection;
    }
}
