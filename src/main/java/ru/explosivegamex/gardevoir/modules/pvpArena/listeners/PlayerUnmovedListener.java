package ru.explosivegamex.gardevoir.modules.pvpArena.listeners;

import com.google.common.collect.Sets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Set;

public final class PlayerUnmovedListener implements Listener {

    private static Set<Player> unmovedPlayers = Sets.newHashSet();
    private static final PlayerUnmovedListener INSTANCE = new PlayerUnmovedListener();

    private PlayerUnmovedListener() {
        if (INSTANCE != null) {
            throw new IllegalStateException("PlayerUnmovedListener already instantiated");
        }
    }

    public static PlayerUnmovedListener getInstance() {
        return INSTANCE;
    }

    public static void addListening(Player player) {
        unmovedPlayers.add(player);
    }

    public static boolean hasPlayerMoved(Player player) {
        return unmovedPlayers.contains(player);
    }

    public static void cancelListening(Player player) {
        unmovedPlayers.removeIf(p -> p.equals(player));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        cancelListening(event.getPlayer());
    }

}
