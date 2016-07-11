package ru.explosivegamex.gardevoir.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Module<T extends JavaPlugin> {
    protected T plugin;

    public Module(T plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();
    public abstract void disable();

    protected void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    protected void register(CommandExecutor command, String... cmds) {
        for(String cmd : cmds) {
            plugin.getCommand(cmd).setExecutor(command);
        }
    }
}
