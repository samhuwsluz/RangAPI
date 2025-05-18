package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import ch.ksrminecraft.rangAPI.local.LocalPointsStore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

/**
 * Main class of the RangAPI plugin.
 * Initializes either MySQL-based or local file-based point storage.
 */
public final class RangAPI extends JavaPlugin {

    private final Database dbConnector = new Database();
    private Connection connection;

    // Either pointsAPI (for online mode) or localStore (for local mode) will be used
    private PointsAPI pointsAPI;
    private LocalPointsStore localStore;

    // Public interface for accessing points
    public DBAPI dbAPI;

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        createDefaultConfig(config);

        String mode = config.getString("mode", "local").toLowerCase();

        if (mode.equals("online")) {
            getLogger().info("ONLINE MODE: Connecting to MySQL database...");
            String url = config.getString("database.online.url");
            String user = config.getString("database.online.user");
            String password = config.getString("database.online.password");

            dbConnector.connect(url, user, password);
            connection = dbConnector.getConnection();

            if (connection != null) {
                pointsAPI = new PointsAPI(connection);
                pointsAPI.createTableIfNotExists();
                dbAPI = new DBAPI(pointsAPI); // Use SQL-based implementation
                getLogger().info("Connected to MySQL successfully.");
            } else {
                getLogger().severe("Failed to connect to MySQL database.");
            }

        } else {
            getLogger().warning("LOCAL MODE: Using file-based point storage.");
            localStore = new LocalPointsStore(this);
            dbAPI = new DBAPI(localStore); // Use local file-based implementation
        }
    }

    @Override
    public void onDisable() {
        dbConnector.disconnect();
        getLogger().info("Database connection closed.");
    }

    private void createDefaultConfig(FileConfiguration config) {
        config.addDefault("mode", "local");

        config.addDefault("database.online.url", "jdbc:mysql://your-online-host:3306/proddb");
        config.addDefault("database.online.user", "produser");
        config.addDefault("database.online.password", "prodpassword");

        config.options().copyDefaults(true);
        saveConfig();
    }
}
