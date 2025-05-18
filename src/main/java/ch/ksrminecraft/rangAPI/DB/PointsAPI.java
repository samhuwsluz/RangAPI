package ch.ksrminecraft.rangAPI.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Provides SQL operations for reading and writing player points.
 */
public class PointsAPI {

    // JDBC connection to the database
    private final Connection connection;

    /**
     * Initializes the PointsAPI with a valid database connection.
     *
     * @param conn Active SQL connection
     */
    public PointsAPI(Connection conn) {
        this.connection = conn;
    }

    /**
     * Ensures the 'points' table exists in the database.
     * Creates it if it doesn't exist.
     */
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS points (" +
                "UUID VARCHAR(36) PRIMARY KEY," +
                "points INT(11) NOT NULL DEFAULT 0," +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to create 'points' table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the current number of points for a player.
     *
     * @param uuid UUID of the player
     * @return Number of points or 0 if not found or on error
     */
    public int getPoints(UUID uuid) {
        String query = "SELECT points FROM points WHERE UUID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("points");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get points for UUID " + uuid + ": " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Sets the points for a player. If the player does not exist, an insert will be attempted.
     *
     * @param uuid   UUID of the player
     * @param points Number of points to assign
     */
    public void setPoints(UUID uuid, int points) {
        // Try to update first
        String update = "UPDATE points SET points = ? WHERE UUID = ?";
        try (PreparedStatement ps = connection.prepareStatement(update)) {
            ps.setInt(1, points);
            ps.setString(2, uuid.toString());
            int affectedRows = ps.executeUpdate();

            // If no rows were updated, insert new row
            if (affectedRows == 0) {
                String insert = "INSERT INTO points (UUID, points) VALUES (?, ?)";
                try (PreparedStatement insertPs = connection.prepareStatement(insert)) {
                    insertPs.setString(1, uuid.toString());
                    insertPs.setInt(2, points);
                    insertPs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to set points for UUID " + uuid + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a number of points to a player's existing points (can be negative).
     *
     * @param uuid        UUID of the player
     * @param pointsDelta Number of points to add
     */
    public void addPoints(UUID uuid, int pointsDelta) {
        int current = getPoints(uuid);
        setPoints(uuid, current + pointsDelta);
    }
}
