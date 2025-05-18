package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.PointsAPI;
import ch.ksrminecraft.rangAPI.local.LocalPointsStore;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Public API for accessing and modifying player points.
 * Supports both SQL (PointsAPI) and local file-based storage (LocalPointsStore).
 */
public class DBAPI {

    private final PointsAPI pointsAPI;
    private final LocalPointsStore localStore;

    /**
     * Use this constructor for MySQL (online) mode.
     */
    public DBAPI(PointsAPI pointsAPI) {
        this.pointsAPI = pointsAPI;
        this.localStore = null;
    }

    /**
     * Use this constructor for local file-based mode.
     */
    public DBAPI(LocalPointsStore localStore) {
        this.localStore = localStore;
        this.pointsAPI = null;
    }

    /**
     * Gets the number of points for a given player.
     */
    public int getPoints(Player player) {
        UUID uuid = player.getUniqueId();
        if (pointsAPI != null) {
            return pointsAPI.getPoints(uuid);
        } else if (localStore != null) {
            return localStore.getPoints(uuid);
        }
        return 0;
    }

    /**
     * Sets the number of points for a given player.
     */
    public void setPoints(Player player, int points) {
        UUID uuid = player.getUniqueId();
        if (pointsAPI != null) {
            pointsAPI.setPoints(uuid, points);
        } else if (localStore != null) {
            localStore.setPoints(uuid, points);
        }
    }

    /**
     * Adds (or subtracts) points to a player's current point value.
     */
    public void addPoints(Player player, int delta) {
        UUID uuid = player.getUniqueId();
        if (pointsAPI != null) {
            pointsAPI.addPoints(uuid, delta);
        } else if (localStore != null) {
            localStore.addPoints(uuid, delta);
        }
    }
}
