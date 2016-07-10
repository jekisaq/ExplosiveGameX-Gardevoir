package ru.explosivegamex.gardevoir.modules;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Module {
    protected JavaPlugin plugin;

    public Module(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();
    public abstract void disable();
}
