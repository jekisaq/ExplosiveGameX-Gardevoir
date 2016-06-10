package ru.explosivegamex.gardevoir;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.commands.PvPCommand;

public class GardevoirMain extends JavaPlugin
{
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        getLogger().info("Gardevoir has been enabled.");

        setUpConfig();
        registerEventListeners();
        registerCommandExecutors();
    }

    private void registerCommandExecutors() {
        getCommand("pvp").setExecutor(new PvPCommand(getLogger(), config.getString("PvPLobbyRegion.name"), getLobbyLocation()));
    }

    private void setUpConfig() {
        config.options().header("The ExplosiveGameX main plugin named Gardevoir. All rights reserved.");

        Location spawnLocation = getServer().getWorld("world").getSpawnLocation();
        config.addDefault("PvPLobbyRegion.x", spawnLocation.getBlockX());
        config.addDefault("PvPLobbyRegion.y", spawnLocation.getBlockY());
        config.addDefault("PvPLobbyRegion.z", spawnLocation.getBlockZ());
        config.addDefault("PvPLobbyRegion.name", "pvp-lobby");

        config.options().copyDefaults(true);

        saveConfig();
    }

    private Location getLobbyLocation() {
        return new Location(
                getServer().getWorld("world"),
                config.getDouble("PvPLobbyRegion.x"),
                config.getDouble("PvPLobbyRegion.y"),
                config.getDouble("PvPLobbyRegion.z")
        );
    }

    private void registerEventListeners() {
        getLogger().info("All event listeners have been loaded.");
    }

}
