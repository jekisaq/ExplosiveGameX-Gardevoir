package ru.explosivegamex.gardevoir;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.listeners.AuthorizedTitleListener;

public class GardevoirMain extends JavaPlugin
{
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[Gardevoir] Gardevoir has been enabled.");

        setUpConfig();
        registerEventListeners();
    }

    private void setUpConfig() {
        config.options().header("The ExplosiveGameX main plugin named Gardevoir. All rights reserved.");
        config.addDefault("messages.title", "&7&lДобро пожаловать");
        config.addDefault("messages.subtitle", "&7&lна сервер ExplosiveGame&8&lX");

        config.options().copyDefaults(true);

        saveConfig();
    }

    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new AuthorizedTitleListener(
                config.getString("messages.title"),
                config.getString("messages.subtitle")), this);

        Bukkit.getLogger().info("[Gardevoir] All event listeners have been loaded.");
    }

    public static String convertToChat(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

}
