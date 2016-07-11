package ru.explosivegamex.gardevoir.modules.pvpArena;

import com.google.common.collect.Maps;
import org.bukkit.event.Listener;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.modules.pvpArena.commands.PvPCommand;
import ru.explosivegamex.gardevoir.modules.pvpArena.listeners.PlayerQuitListener;
import ru.explosivegamex.gardevoir.modules.pvpArena.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.modules.pvpArena.listeners.PvPArenaEnterListener;
import ru.explosivegamex.gardevoir.modules.Module;

import java.util.Map;

public class PvPArena extends Module<GardevoirMain> {

    private Map<String, Listener> listenerMap = Maps.newHashMap();

    public PvPArena(GardevoirMain plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        registerEventListeners();
        registerCommandExecutors();

        plugin.getLogger().info("Module 'PvPArena' has been enabled.");
    }

    private void registerEventListeners() {
        listenerMap.put("playerUnmoved", PlayerUnmovedListener.getInstance());
        listenerMap.put("playerQuit", new PlayerQuitListener(plugin.getConfig().getString("PvPLobbyRegion.name")));
        listenerMap.put("pvpArenaEnter", new PvPArenaEnterListener(plugin.getConfig().getString("PvPLobbyRegion.name")));

        listenerMap.forEach((key, listener) -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));
    }

    private void registerCommandExecutors() {
        Listener playerUnmovedListener = listenerMap.get("playerUnmoved");
        if (playerUnmovedListener instanceof PlayerUnmovedListener) {
            plugin.getCommand("pvp").setExecutor(new PvPCommand(plugin, plugin.getConfig().getString("PvPLobbyRegion.name")));
        } else {
            plugin.getLogger().severe("Command pvp aren't loaded, because PlayerUnmovedListener not detected.");
        }
    }

    @Override
    public void disable() {
        plugin.getLogger().info("Module 'PvPArena' has been disabled.");
    }
}
