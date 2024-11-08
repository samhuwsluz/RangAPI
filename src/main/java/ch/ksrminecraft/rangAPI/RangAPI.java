package ch.ksrminecraft.rangAPI;

import ch.ksrminecraft.rangAPI.DB.Database;
import org.bukkit.plugin.java.JavaPlugin;

public final class RangAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        Database dbConnector = new Database();
        dbConnector.connect("jdbc:mysql://HOST/DATABASE_NAME", "USERNAME", "PASSWORD");
    }

    @Override
    public void onDisable() {
        //TODO DBSession Teardown
    }
}
