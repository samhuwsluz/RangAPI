package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.sql.Connection;

public final class RangAPI extends JavaPlugin {
    Database dbConnector = new Database();
    Connection connection;
    PointsAPI pointsAPI;
    @Override
    public void onEnable() {
        dbConnector.connect("jdbc:mysql://ksrminecraft.ch/KSRPointsDBTest", "points_test_user", "snC7oFdB1w");
        connection = dbConnector.getConnection();
        pointsAPI = new PointsAPI(connection);
    }

    @Override
    public void onDisable() {
        //TODO DBSession Teardown
    }

    public int GetPoints(Player p){
        int points = pointsAPI.SQLgetInt("Select points from points where UUID = ' " + p.getUniqueId() + "'");
        return points;
        }
    public void SetPoints(Player p, int points){
        pointsAPI.SQLUpdate("Update points set points = " + points + " where UUID = ' " + p.getUniqueId() + "'");
    }
    //public void AddPoints();
}
