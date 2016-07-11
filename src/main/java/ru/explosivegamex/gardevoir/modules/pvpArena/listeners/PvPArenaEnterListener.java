package ru.explosivegamex.gardevoir.modules.pvpArena.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPArenaEnterListener implements Listener
{
    private String pvpRegionName;

    public PvPArenaEnterListener(String pvpRegionName) {
        this.pvpRegionName = pvpRegionName;
    }

    @EventHandler
    public void on(RegionEnterEvent e) {
        ProtectedRegion region = e.getRegion();

        if (isRegionPvPArena(region)) {
            Essentials ess = JavaPlugin.getPlugin(Essentials.class);

            Player player = e.getPlayer();
            User user = ess.getUser(player);

            if (user.isGodModeEnabled()) {
                user.setGodModeEnabled(false);
            }

            player.setFlying(false);
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    private boolean isRegionPvPArena(ProtectedRegion region)
    {
        return region.getId().equals(pvpRegionName);
    }
}
