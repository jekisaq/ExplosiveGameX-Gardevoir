package ru.explosivegamex.gardevoir.modules;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Module<T extends JavaPlugin> {
    protected T plugin;

    public Module(T plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();
    public abstract void disable();
}
