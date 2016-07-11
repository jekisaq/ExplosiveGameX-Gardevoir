package ru.explosivegamex.gardevoir;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.modules.Module;
import ru.explosivegamex.gardevoir.modules.caseInformer.CaseInformer;
import ru.explosivegamex.gardevoir.modules.pvpArena.PvPArena;

public final class GardevoirMain extends JavaPlugin
{
    private FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        setUpConfig();
        register(CaseInformer.class, PvPArena.class);
        getLogger().info("GardevoirMain has been enabled.");
    }

    @SafeVarargs
    private final void register(Class<? extends Module>... modules) {
        for (Class<? extends Module> moduleClass : modules) {
            try {
                Module module = moduleClass.getConstructor(GardevoirMain.class).newInstance(this);
                module.enable();
            } catch (ReflectiveOperationException ex) {
                getLogger().warning("Error occurred on registering the " + moduleClass.getName() + " module");
                ex.printStackTrace();
            }
        }
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

}
