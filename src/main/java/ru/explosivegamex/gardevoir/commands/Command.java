package ru.explosivegamex.gardevoir.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;

import java.util.logging.Level;

public abstract class Command implements CommandExecutor {
    private String usage;
    private String permission;
    private boolean allowConsole = true;
    private int minArgs = 0;

    protected CommandSender sender;
    protected Player player;
    protected boolean isPlayer;
    private String[] args;

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.isPlayer = sender instanceof Player;
        this.sender = sender;
        this.args = args;
        if(!allowConsole && !isPlayer) {
            reply(false, "Вы должны быть игроком, чтобы выполнить эту команду");
            return true;
        }

        if(isPlayer) this.player = (Player) sender;
        if(permission != null && !sender.hasPermission(permission)) {
            reply(false, "У вас нет прав на совершение данного действия.");
            return true;
        }

        if(args.length < minArgs) {
            reply(false, "Эта команда требует как минимум %s аргументов!", minArgs);
            return true;
        }

        try {
            execute();
        } catch(Exception e) {
            reply(false, "Возникла ошибка при выполнении данной команды! Свяжитесь с администрацией проекта.");
            Bukkit.getLogger().log(Level.SEVERE, "Error while running command", e);
        }
        return true;
    }

    public abstract void execute() throws ReflectiveOperationException;

    protected void reply(String message, Object... args) {
        reply(true, message, args);
    }

    protected void reply(boolean success, Object message, Object... args) {
        reply(sender, success, message, args);
    }

    protected void reply(CommandSender sender, boolean success, Object message, Object... args) {
        Inform.to(sender, String.format(message.toString(), args), (success ? MessageType.SUCCESS : MessageType.ERROR));
    }

    protected String getArg(int index) {
        return args[index];
    }

    protected int getArgAsInt(int index) {
        return Integer.parseInt(getArg(index));
    }

    protected Player getArgAsPlayer(int index) {
        return Bukkit.getPlayer(getArg(index));
    }

    protected int getArgLength() {
        return args.length;
    }

    protected String getUsage() {
        return usage;
    }

    protected void setUsage(String usage) {
        this.usage = usage;
    }

    protected String getPermission() {
        return permission;
    }

    protected void setPermission(String permission) {
        this.permission = permission;
    }

    protected int getMinArgs() {
        return minArgs;
    }

    protected void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    protected boolean isAllowConsole() {
        return allowConsole;
    }

    protected void setAllowConsole(boolean allowConsole) {
        this.allowConsole = allowConsole;
    }

}
