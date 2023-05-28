package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {
    private static Connection connection;

    // Private constructor to prevent instantiation
    private ConnectionUtil() {
    }

    // Method to get a connection to the database
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/dormitorysystem";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }

        return connection;
    }
}
