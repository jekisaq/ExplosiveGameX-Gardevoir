package ru.explosivegamex.gardevoir.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.commands.pvp.LeaveSubCommand;
import ru.explosivegamex.gardevoir.util.Inform;

import java.util.logging.Logger;

public class PvPCommand implements CommandExecutor {

    private Logger logger;
    private GardevoirMain plugin;
    private String pvpArenaName;

    public PvPCommand(GardevoirMain plugin, String regionName) {
        this.plugin = plugin;
        this.pvpArenaName = regionName;
        this.logger = plugin.getLogger();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("leave")) {
                if (sender instanceof Player) {
                    PlayerSubCommand leaveSubCommand = new LeaveSubCommand(plugin, pvpArenaName, (Player) sender, command);
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
        Inform.to(sender, "Использование: ");
        Inform.to(sender, command.getUsage());
    }
}
