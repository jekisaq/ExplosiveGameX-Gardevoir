package ru.explosivegamex.gardevoir.commands;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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

    protected boolean isPlayerInsideRegion(String regionName) {
        RegionManager manager = WGBukkit.getRegionManager(player.getWorld());
        Set<ProtectedRegion> regionSet = manager.getApplicableRegions(player.getLocation()).getRegions();

        return regionSet.stream().anyMatch(protectedRegion -> protectedRegion.getId().equals(regionName));
    }
}
