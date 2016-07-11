package ru.explosivegamex.gardevoir.modules.pvpArena.commands.pvp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.commands.PlayerSubCommand;
import ru.explosivegamex.gardevoir.modules.pvpArena.listeners.PlayerUnmovedListener;
import ru.explosivegamex.gardevoir.util.GardevoirPlayer;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;

public class LeaveSubCommand extends PlayerSubCommand {

    private String pvpRegionName;
    private GardevoirMain plugin;
    private Location lobby;

    public LeaveSubCommand(GardevoirMain plugin, String pvpRegionName, Player player, Command command) {
        super(player, command, new String[0]);
        this.pvpRegionName = pvpRegionName;
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
        PlayerUnmovedListener.addListening(player);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (PlayerUnmovedListener.hasPlayerMoved(player)) {
                if (player.teleport(getLobbyLocation())) {
                    Inform.to(player, "Вы покинули &c&lpvp&r-&6&lарену", MessageType.SUCCESS);
                }

                PlayerUnmovedListener.cancelListening(player);
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
