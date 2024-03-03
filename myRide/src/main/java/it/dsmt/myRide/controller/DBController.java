package it.dsmt.myRide.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

    public static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + System.getenv("DB_PATH");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
