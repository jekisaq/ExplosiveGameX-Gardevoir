package ru.explosivegamex.gardevoir.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class PvPCommand implements CommandExecutor, Listener {

    private Logger logger;
    private JavaPlugin plugin;
    private String pvpArenaName;
    private Location lobby;

    private Set<Player> unmovedPlayers = new HashSet<>();

    public PvPCommand(Logger logger, JavaPlugin plugin, String regionName, Location lobby) {
        this.logger = logger;
        this.plugin = plugin;
        this.pvpArenaName = regionName;
        this.lobby = lobby;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("leave")) {
                onLeaveSubCommand(sender);
            } else {
                showUsage(sender, command);
            }
        } else {
            showUsage(sender, command);
        }

        return true;
    }

    private void showUsage(CommandSender sender, Command command) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&lИспользование: "));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', command.getUsage()));
    }

    private void onLeaveSubCommand(CommandSender sender) {
        if (sender instanceof Player) {
            performLeaveFor((Player) sender);
        } else {
            logger.info("This command might be call by player only!");
        }
    }

    private void performLeaveFor(Player player) {
        RegionManager manager = WGBukkit.getRegionManager(player.getWorld());
        Set<ProtectedRegion> regionSet = manager.getApplicableRegions(player.getLocation()).getRegions();
        if (regionSet.stream().anyMatch(protectedRegion -> protectedRegion.getId().equals(pvpArenaName))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[&6&lInfo&9&l] &7Вы покинете &c&lpvp&r-&6&lарену &7через &6&l5 &7секунд. Не двигайтесь!"));
            unmovedPlayers.add(player);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (unmovedPlayers.contains(player)) {
                    if (player.teleport(lobby)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[&6&lInfo&9&l] &2&lВы покинули &c&lpvp&r-&6&lарену"));
                    }

                    unmovedPlayers.remove(player);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[&6&lInfo&9&l] &c&lЗапрос на телепортацию был отменен!"));
                }
            }, 5 * 20);
        } else {
            logger.info("Player " + player.getDisplayName() + " attempted to leave the pvp-arena from its outside!");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        unmovedPlayers.removeIf(p -> p.equals(event.getPlayer()));
    }
}
