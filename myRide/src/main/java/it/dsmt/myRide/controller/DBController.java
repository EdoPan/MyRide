package it.dsmt.myRide.controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {
    private static DBController instance = null;
    Connection connection = null;

    public static  DBController getInstance(){
        if (instance == null) {
            try{
                instance = new DBController();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }

    public DBController () throws Exception {
        // SQLite connection string
        String url = "jdbc:sqlite:" + System.getenv("DB_PATH");
        try {
            this.connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection(){
        return DBController.getInstance().connection;
    }
    
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