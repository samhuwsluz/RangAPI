package ch.ksrminecraft.rangAPI;

import org.bukkit.entity.Player;

public interface DBAPI {
    public int getPoints(Player player);
    public void setPoints(Player player, int points);
    public void addPoints(Player player, int points);
}
