package ru.explosivegamex.gardevoir.modules.pvpArena.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.explosivegamex.gardevoir.util.GardevoirPlayer;

public class PlayerQuitListener implements Listener {

    private String pvpRegionName;

    public PlayerQuitListener(String pvpRegionName) {
        this.pvpRegionName = pvpRegionName;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (GardevoirPlayer.of(player).isInsideRegion(pvpRegionName)) {
            player.setHealth(0);
        }
    }
}
