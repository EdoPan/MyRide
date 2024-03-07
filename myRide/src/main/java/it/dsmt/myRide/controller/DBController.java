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
        /* 
        // Create users table.
        System.out.println("[DB] CREATE USERS TABLE");
        Statement stmt = this.connection.createStatement();
        ResultSet rs1 = stmt.executeQuery("CREATE TABLE IF NOT EXISTS  users (username varchar(255) not null primary key, \n" +
            "password varchar(255), isMaintainer boolean)");
        System.out.println(rs1.toString());
        rs1.close();

        System.out.println("[DB] CREATE STATIONS TABLE");
        ResultSet rs2 = stmt.executeQuery("CREATE TABLE IF NOT EXISTS stations (\n" +
                "id INTEGER PRIMARY KEY autoincrement,\n" +
                "address varchar(255),\n" +
                "numberOfBikes integer,\n" +
                ");");
        System.out.println(rs2.toString());
        rs2.close();

        System.out.println("[DB] CREATE BIKES TABLE");
        ResultSet rs3 = stmt.executeQuery("CREATE TABLE IF NOT EXISTS bikes (\n" +
                "id integer not null primary key autoincrement,\n" +
                "type varchar(255),\n" +
                "price double,\n" +
                "condition varchar(255),\n" +
                "stationID integer,\n" +
                "foreign key (stationID) references stations(id)\n" +
                ");");
        System.out.println(rs3.toString());
        rs3.close();    

        System.out.println("[DB] CREATE RIDES TABLE");
        ResultSet rs4 = stmt.executeQuery("CREATE TABLE IF NOT EXISTS rides (\n" +
                "id INTEGER PRIMARY KEY autoincrement,\n" +
                "bikeID INTEGER,\n" +
                "userID INTEGER,\n" +
                "start DATE,\n" +
                "end DATE\n"+
                "FOREIGN KEY (bikeID) REFERENCES bikes(id)\n" +
                "FOREIGN KEY (userID) REFERENCES users(id)\n" +
                ");");
        System.out.println(rs4.toString());
        rs4.close();
        */
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