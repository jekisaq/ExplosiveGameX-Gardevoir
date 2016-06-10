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

import java.util.Set;
import java.util.logging.Logger;

public class PvPCommand implements CommandExecutor {

    private Logger logger;
    private String pvpArenaName;
    private Location lobby;

    public PvPCommand(Logger logger, String regionName, Location lobby) {
        this.logger = logger;
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
            if (player.teleport(lobby)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&l[&6&lInfo&9&l] &2&lВы покинули &c&lpvp&r-&6&lарену"));
            }
        } else {
            logger.info("Player " + player.getDisplayName() + " attempted to leave the pvp-arena from its outside!");
        }
    }
}
