package ru.explosivegamex.gardevoir.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public abstract class PlayerSubCommand {

    protected Player player;
    private Command command;
    private String[] args;

    public PlayerSubCommand(Player player, Command command, String[] args) {
        this.command = command;
        this.player = player;
        this.args = args;
    }

    public abstract void run();
}
