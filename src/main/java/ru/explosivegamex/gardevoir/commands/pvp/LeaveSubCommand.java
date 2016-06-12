package ru.explosivegamex.gardevoir.commands.pvp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.commands.PlayerSubCommand;
import ru.explosivegamex.gardevoir.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.util.GardevoirPlayer;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;

public class LeaveSubCommand extends PlayerSubCommand {

    private String pvpRegionName;
    private JavaPlugin plugin;
    private PlayerUnmovedListener playerUnmovedListener;
    private Location lobby;

    public LeaveSubCommand(JavaPlugin plugin, PlayerUnmovedListener playerUnmovedListener, String pvpRegionName, Player player, Command command) {
        super(player, command, new String[0]);

        this.pvpRegionName = pvpRegionName;
        this.playerUnmovedListener = playerUnmovedListener;
        this.plugin = plugin;
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
        playerUnmovedListener.addListening(player);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (playerUnmovedListener.hasPlayerMoved(player)) {
                if (player.teleport(getLobbyLocation())) {
                    Inform.to(player, "Вы покинули &c&lpvp&r-&6&lарену", MessageType.SUCCESS);
                }

                playerUnmovedListener.removeListeningPlayer(player);
            } else {
                Inform.to(player, "Запрос на телепортацию был отменен!", MessageType.ERROR);
            }
        }, plugin.getConfig().getLong("leave.delay") * 20);
    }

    private Location getLobbyLocation() {
        if (lobby == null) {
            lobby = new Location(
                    plugin.getServer().getWorld("world"),
                    plugin.getConfig().getDouble("PvPLobbyRegion.x"),
                    plugin.getConfig().getDouble("PvPLobbyRegion.y"),
                    plugin.getConfig().getDouble("PvPLobbyRegion.z")
            );
        }

        return lobby;
    }
}
