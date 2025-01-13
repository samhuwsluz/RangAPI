package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.entity.Player;

public class DBAPI {
    PointsAPI pointsAPI;
    public DBAPI(PointsAPI pointsAPI) {
        this.pointsAPI = pointsAPI;
    }

    public int getPoints(Player p){
        int points = pointsAPI.SQLgetInt("Select points from points where UUID = ' " + p.getUniqueId() + "'");
        return points;
    }
    public void setPoints(Player p, int points){
        pointsAPI.SQLUpdate("Update points set points = " + points + " where UUID = ' " + p.getUniqueId() + "'");
    }
    public void addPoints(Player p, int pointsDelta){
        int points = pointsAPI.SQLgetInt("Select points from points where UUID = ' " + p.getUniqueId() + "'");
        int newPoints = points + pointsDelta;
        pointsAPI.SQLUpdate("Update points set points = " + newPoints + " where UUID = ' " + p.getUniqueId() + "'");
    }

}
