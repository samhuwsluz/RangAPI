package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class RangAPI extends JavaPlugin {
    Database dbConnector = new Database();
    Connection connection;
    PointsAPI pointsAPI;
   public DBAPI dbAPI;
    @Override
    public void onEnable() {

    FileConfiguration config = getConfiguration();
    createDefaultConfig(this, config);

        dbConnector.connect("jdbc:mysql://ksrminecraft.ch/KSRPointsDBTest", "points_test_user", "snC7oFdB1w");
        connection = dbConnector.getConnection();
        pointsAPI = new PointsAPI(connection);
        dbAPI = new DBAPI(pointsAPI);
    }

    @Override
    public void onDisable() {
        //TODO DBSession Teardown
    }

    private FileConfiguration getConfiguration() {
        FileConfiguration config = this.getConfig();
        return config;
    }
    private void createDefaultConfig(JavaPlugin plugin, FileConfiguration config) {
        config.addDefault("database-url", "PathPlaceholder");
        config.addDefault("database-user", "UserPlaceholder");
        config.addDefault("database-password", "PasswordPlaceholder");

        config.options().copyDefaults(true);

        plugin.saveConfig();
    }

    private String getDatabaseURL() {return this.getConfig().getString("database-url");}
    private String getDatabaseUser() {return this.getConfig().getString("database-user");}
    private String getDatabasePassword() {return this.getConfig().getString("database-password");}
}