package edu.icet.librarymanagmentsystem.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private final String URL = "jdbc:mysql://localhost/libraryms";
    private final String userName = "root";
    private final String password = "1234";

    private DBConnection() {
        // Private constructor to enforce singleton pattern
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        // Create a new connection for each call
        return DriverManager.getConnection(URL, userName, password);
    }
}