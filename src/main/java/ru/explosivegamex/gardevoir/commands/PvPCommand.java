package ru.explosivegamex.gardevoir.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.commands.pvp.LeaveSubCommand;
import ru.explosivegamex.gardevoir.listeners.PlayerUnmovedListener;

import java.util.logging.Logger;

public class PvPCommand implements CommandExecutor {

    private Logger logger;
    private JavaPlugin plugin;
    private String pvpArenaName;
    private PlayerUnmovedListener playerUnmovedListener;

    public PvPCommand(JavaPlugin plugin, PlayerUnmovedListener playerUnmovedListener, String regionName) {
        this.plugin = plugin;
        this.playerUnmovedListener = playerUnmovedListener;
        this.pvpArenaName = regionName;
        this.logger = plugin.getLogger();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("leave")) {
                if (sender instanceof Player) {
                    PlayerSubCommand leaveSubCommand = new LeaveSubCommand(plugin, playerUnmovedListener, pvpArenaName, (Player) sender, command);
                    leaveSubCommand.run();
                } else {
                    logger.info("This command might be call by player only!");
                }
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
}
