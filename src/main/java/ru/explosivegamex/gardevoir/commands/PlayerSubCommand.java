package ru.explosivegamex.gardevoir.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class PlayerSubCommand {

    protected Player player;
    protected Command command;
    protected String[] args;

    public PlayerSubCommand(Player player, Command command, String[] args) {
        this.command = command;
        this.player = player;
        this.args = args;
    }

    public abstract void run();
}
