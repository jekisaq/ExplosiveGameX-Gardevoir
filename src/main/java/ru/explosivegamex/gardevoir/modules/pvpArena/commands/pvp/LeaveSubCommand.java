package ru.explosivegamex.gardevoir.modules.pvpArena.commands.pvp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.modules.pvpArena.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.util.GardevoirPlayer;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;

public class LeaveSubCommand {

    private String pvpRegionName;
    private GardevoirMain plugin;
    private Player player;
    private Location lobby;

    public LeaveSubCommand(GardevoirMain plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.pvpRegionName = plugin.getConfig().getString("PvPLobbyRegion.name");

        lobby = new Location(
                plugin.getServer().getWorld("world"),
                plugin.getConfig().getDouble("PvPLobbyRegion.x"),
                plugin.getConfig().getDouble("PvPLobbyRegion.y"),
                plugin.getConfig().getDouble("PvPLobbyRegion.z")
        );
    }

    public void run() {
        if (GardevoirPlayer.of(player).isInsideRegion(pvpRegionName)) {
            Inform.to(player, "Вы покинете &c&lpvp&r-&6&lарену &7через &6&l5 &7секунд. Не двигайтесь!");
            performDelayedTeleport();
        } else {
            plugin.getLogger().info("Player " + player.getDisplayName() + " attempted to leave the pvp-arena from its outside!");
        }
    }

    private void performDelayedTeleport() {
        PlayerUnmovedListener.addListening(player);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (PlayerUnmovedListener.hasPlayerMoved(player)) {
                if (player.teleport(lobby)) {
                    Inform.to(player, "Вы покинули &c&lpvp&r-&6&lарену", MessageType.SUCCESS);
                }

                PlayerUnmovedListener.cancelListening(player);
            } else {
                Inform.to(player, "Запрос на телепортацию был отменен!", MessageType.ERROR);
            }
        }, plugin.getConfig().getLong("leave.delay") * 20);
    }
}
