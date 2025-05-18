package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DBAPI {

    private final PointsAPI pointsAPI;

    /**
     * Constructor that links this API to a PointsAPI instance (handles DB logic).
     *
     * @param pointsAPI An instance of PointsAPI with an active DB connection
     */
    public DBAPI(PointsAPI pointsAPI) {
        this.pointsAPI = pointsAPI;
    }

    /**
     * Gets the current number of points for a player.
     *
     * @param player The Bukkit player
     * @return The number of points assigned to the player
     */
    public int getPoints(Player player) {
        UUID uuid = player.getUniqueId();
        return pointsAPI.getPoints(uuid);
    }

    /**
     * Sets the points for a player to a specific value.
     *
     * @param player The Bukkit player
     * @param points The number of points to set
     */
    public void setPoints(Player player, int points) {
        UUID uuid = player.getUniqueId();
        pointsAPI.setPoints(uuid, points);
    }

    /**
     * Adds or subtracts points for a player.
     *
     * @param player      The Bukkit player
     * @param pointsDelta The amount of points to add (can be negative)
     */
    public void addPoints(Player player, int pointsDelta) {
        UUID uuid = player.getUniqueId();
        pointsAPI.addPoints(uuid, pointsDelta);
    }
}
