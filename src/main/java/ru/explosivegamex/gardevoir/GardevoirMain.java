package ru.explosivegamex.gardevoir;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.commands.PvPCommand;
import ru.explosivegamex.gardevoir.listeners.PlayerQuitListener;
import ru.explosivegamex.gardevoir.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.listeners.PvPArenaEnterListener;

import java.util.HashMap;
import java.util.Map;

public class GardevoirMain extends JavaPlugin
{
    private FileConfiguration config = getConfig();
    private Map<String, Listener> listenerMap;

    @Override
    public void onEnable() {
        listenerMap = new HashMap<>();

        setUpConfig();
        registerEventListeners();
        registerCommandExecutors();

        getLogger().info("Gardevoir has been enabled.");
    }

    private void registerCommandExecutors() {
        Listener playerUnmovedListener = listenerMap.get("playerUnmoved");
        if (playerUnmovedListener instanceof PlayerUnmovedListener) {
            getCommand("pvp").setExecutor(new PvPCommand(this, (PlayerUnmovedListener) listenerMap.get("playerUnmoved"), config.getString("PvPLobbyRegion.name")));
        } else {
            getLogger().severe("Command pvp aren't loaded, because PlayerUnmovedListener not detected.");
        }
    }

    private void setUpConfig() {
        config.options().header("The ExplosiveGameX main plugin named Gardevoir. All rights reserved.");

        Location spawnLocation = getServer().getWorld("world").getSpawnLocation();
        config.addDefault("PvPLobbyRegion.x", spawnLocation.getBlockX());
        config.addDefault("PvPLobbyRegion.y", spawnLocation.getBlockY());
        config.addDefault("PvPLobbyRegion.z", spawnLocation.getBlockZ());
        config.addDefault("PvPLobbyRegion.name", "pvp-lobby");

        config.addDefault("leave.delay", 5L);

        config.options().copyDefaults(true);

        saveConfig();
    }

    private void registerEventListeners() {
        listenerMap.put("playerUnmoved", new PlayerUnmovedListener());
        listenerMap.put("playerQuit", new PlayerQuitListener(config.getString("PvPLobbyRegion.name")));
        listenerMap.put("pvpArenaEnter", new PvPArenaEnterListener(config.getString("PvPLobbyRegion.name")));

        listenerMap.forEach((key, listener) -> getServer().getPluginManager().registerEvents(listener, this));

        getLogger().info("All event listeners have been loaded.");
    }

}
