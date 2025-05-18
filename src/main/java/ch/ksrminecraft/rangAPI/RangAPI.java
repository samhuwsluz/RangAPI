package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

/**
 * Main class of the RangAPI plugin.
 * Responsible for initializing database access and loading configuration.
 */
public final class RangAPI extends JavaPlugin {

    // Helper class for managing database connections
    private final Database dbConnector = new Database();

    // Active JDBC connection to the database
    private Connection connection;

    // API for low-level SQL access to the points table
    private PointsAPI pointsAPI;

    // Public API for other plugins to access player point data
    public DBAPI dbAPI;

    /**
     * Called when the plugin is enabled (server startup or /reload).
     * Initializes config and database connection, with test mode support.
     */
    @Override
    public void onEnable() {
        // Load and create config with default values if not present
        FileConfiguration config = this.getConfig();
        createDefaultConfig(config);

        // Check whether to use test or production database
        boolean testMode = config.getBoolean("testmode", false);
        String url, user, password;

        if (testMode) {
            getLogger().warning("TEST MODE ENABLED: Using local test database.");
            url = "jdbc:mysql://localhost:3306/testpoints";
            user = "testuser";
            password = "testpassword";
        } else {
            getLogger().info("Production mode: Connecting to configured database.");
            url = getDatabaseURL();
            user = getDatabaseUser();
            password = getDatabasePassword();
        }

        // Connect to the database
        dbConnector.connect(url, user, password);
        connection = dbConnector.getConnection();

        if (connection != null) {
            // Initialize internal APIs
            pointsAPI = new PointsAPI(connection);

            // Ensure the 'points' table exists
            pointsAPI.createTableIfNotExists();

            // Provide public access API
            dbAPI = new DBAPI(pointsAPI);

            getLogger().info("Database connection established successfully.");
        } else {
            getLogger().severe("Failed to establish database connection. Plugin features will be limited.");
        }
    }

    /**
     * Called when the plugin is disabled (server shutdown or /reload).
     * Closes the database connection cleanly using Database#disconnect().
     */
    @Override
    public void onDisable() {
        dbConnector.disconnect();
        getLogger().info("Database connection closed.");
    }

    /**
     * Creates default config entries if they don't exist.
     *
     * @param config The configuration object to populate
     */
    private void createDefaultConfig(FileConfiguration config) {
        config.addDefault("database-url", "jdbc:mysql://prodhost:3306/proddb");
        config.addDefault("database-user", "produser");
        config.addDefault("database-password", "prodpassword");
        config.addDefault("testmode", true); // default to true for safe dev environment

        config.options().copyDefaults(true);
        saveConfig();
    }

    // Helpers to access config values
    private String getDatabaseURL() {
        return getConfig().getString("database-url");
    }

    private String getDatabaseUser() {
        return getConfig().getString("database-user");
    }

    private String getDatabasePassword() {
        return getConfig().getString("database-password");
    }
}
