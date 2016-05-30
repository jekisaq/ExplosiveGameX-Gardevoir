package ru.explosivegamex.gardevoir;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.listeners.PlayerEventsListener;

public class GardevoirMain extends JavaPlugin
{
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[Gardevoir] Gardevoir has been enabled.");
        registerEventListeners();
    }

    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);

        Bukkit.getLogger().info("[Gardevoir] All event listeners has been loaded.");
    }

    public static String convertToChat(String source) {
        return source.replaceAll("&", String.valueOf(ChatColor.COLOR_CHAR));
    }
}
