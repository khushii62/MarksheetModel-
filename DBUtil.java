package com.thrivesup.marksheet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DBUtil {
    public static Connection getConnection() {
        Connection con = null;
        try {
            ResourceBundle rb = ResourceBundle.getBundle("db"); 
            String url = rb.getString("db.url");
            String user = rb.getString("db.user");
            String pass = rb.getString("db.password");

            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.err.println("\u001B[31mDatabase Connection Error: " + e.getMessage() + "\u001B[0m");
        }
        return con;
    }
}
