package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.sql.Connection;

public final class RangAPI extends JavaPlugin {
    Database dbConnector = new Database();
    Connection connection;
    @Override
    public void onEnable() {
        dbConnector.connect("jdbc:mysql://HOST/DATABASE_NAME", "USERNAME", "PASSWORD");
        connection = dbConnector.getConnection();

    }

    @Override
    public void onDisable() {
        //TODO DBSession Teardown
    }

    //public void GetPoints();
    //public void SetPoints();
    //public void AddPoints();
}
