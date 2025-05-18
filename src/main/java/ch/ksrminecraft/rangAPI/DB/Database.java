package ch.ksrminecraft.rangAPI.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles connection management to a MySQL database.
 */
public class Database {

    // Holds the active database connection
    private Connection connection;

    /**
     * Establishes a connection to the database if not already connected.
     *
     * @param url  JDBC connection string (e.g. jdbc:mysql://localhost:3306/mydb)
     * @param user MySQL username
     * @param pass MySQL password
     */
    public void connect(String url, String user, String pass) {
        try {
            // Skip if the connection is already open
            if (connection != null && !connection.isClosed()) {
                return;
            }

            // Thread-safe connection initialization
            synchronized (this) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(url, user, pass);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the current database connection.
     *
     * @return The active Connection object, or null if not connected
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
