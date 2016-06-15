package ru.explosivegamex.gardevoir.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerUnmovedListener implements Listener {

    private Set<Player> unmovedPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        cancelListening(event.getPlayer());
    }

    public void addListening(Player player) {
        unmovedPlayers.add(player);
    }

    public boolean hasPlayerMoved(Player player) {
        return unmovedPlayers.contains(player);
    }

    public void cancelListening(Player player) {
        unmovedPlayers.removeIf(p -> p.equals(player));
    }

}
