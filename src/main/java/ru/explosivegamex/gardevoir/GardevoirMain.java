package ru.explosivegamex.gardevoir;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.commands.PvPCommand;
import ru.explosivegamex.gardevoir.listeners.PlayerQuitListener;
import ru.explosivegamex.gardevoir.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.listeners.PvPArenaEnterListener;
import ru.explosivegamex.gardevoir.modules.Module;
import ru.explosivegamex.gardevoir.modules.caseInformer.CaseInformer;

import java.util.Map;

public final class GardevoirMain extends JavaPlugin
{
    private FileConfiguration config = getConfig();
    private Map<String, Listener> listenerMap = Maps.newHashMap();

    @Override
    public void onEnable() {
        setUpConfig();
        registerEventListeners();
        registerCommandExecutors();

        register(CaseInformer.class);

        getLogger().info("GardevoirMain has been enabled.");
    }

    @SafeVarargs
    private final void register(Class<? extends Module>... modules) {
        for (Class<? extends Module> moduleClass : modules) {
            try {
                Module module = moduleClass.getConstructor(JavaPlugin.class).newInstance(this);
                module.enable();
            } catch (ReflectiveOperationException ex) {
                getLogger().warning("Error occurred on registering " + moduleClass.getName() + " module");
                ex.printStackTrace();
            }
        }
    }

    private void registerCommandExecutors() {
        Listener playerUnmovedListener = listenerMap.get("playerUnmoved");
        if (playerUnmovedListener instanceof PlayerUnmovedListener) {
            getCommand("pvp").setExecutor(new PvPCommand(this, config.getString("PvPLobbyRegion.name")));
        } else {
            getLogger().severe("Command pvp aren't loaded, because PlayerUnmovedListener not detected.");
        }

        getLogger().info("All commands have been loaded.");
    }

    private void setUpConfig() {
        config.options().header("The ExplosiveGameX main plugin named GardevoirMain. All rights reserved.");

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
        listenerMap.put("playerUnmoved", PlayerUnmovedListener.getInstance());
        listenerMap.put("playerQuit", new PlayerQuitListener(config.getString("PvPLobbyRegion.name")));
        listenerMap.put("pvpArenaEnter", new PvPArenaEnterListener(config.getString("PvPLobbyRegion.name")));

        listenerMap.forEach((key, listener) -> getServer().getPluginManager().registerEvents(listener, this));

        getLogger().info("All event listeners have been loaded.");
    }

}
