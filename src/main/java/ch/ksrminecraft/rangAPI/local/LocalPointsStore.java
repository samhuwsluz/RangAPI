package ch.ksrminecraft.rangAPI.local;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Handles storing and loading player points from a local JSON file.
 */
public class LocalPointsStore {

    private final File dataFile;
    private final JavaPlugin plugin;
    private final Gson gson;

    private final Map<UUID, Integer> pointsMap = new ConcurrentHashMap<>();

    public LocalPointsStore(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        this.dataFile = new File(dataFolder, "points.json");

        loadFromFile();
    }

    /**
     * Loads player points from the JSON file into memory.
     */
    private void loadFromFile() {
        if (!dataFile.exists()) {
            plugin.getLogger().info("No points.json found. Creating new file.");
            saveToFile(); // write empty map
            return;
        }

        try (FileReader reader = new FileReader(dataFile)) {
            Map<String, Integer> rawMap = gson.fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
            if (rawMap != null) {
                rawMap.forEach((uuidStr, points) -> {
                    try {
                        pointsMap.put(UUID.fromString(uuidStr), points);
                    } catch (IllegalArgumentException ignored) {
                        plugin.getLogger().warning("Invalid UUID in points.json: " + uuidStr);
                    }
                });
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load points from local file: " + e.getMessage());
        }
    }

    /**
     * Saves the current state of the player points map to disk.
     */
    private void saveToFile() {
        Map<String, Integer> rawMap = new ConcurrentHashMap<>();
        pointsMap.forEach((uuid, points) -> rawMap.put(uuid.toString(), points));

        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(rawMap, writer);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save points to local file: " + e.getMessage());
        }
    }

    public int getPoints(UUID uuid) {
        return pointsMap.getOrDefault(uuid, 0);
    }

    public void setPoints(UUID uuid, int points) {
        pointsMap.put(uuid, points);
        saveToFile();
    }

    public void addPoints(UUID uuid, int delta) {
        pointsMap.put(uuid, getPoints(uuid) + delta);
        saveToFile();
    }
}
