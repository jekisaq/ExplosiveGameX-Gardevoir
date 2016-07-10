package ru.explosivegamex.gardevoir.util;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GardevoirPlayer {

    private Player player;

    private static Map<Player, GardevoirPlayer> players = new HashMap<>();

    private GardevoirPlayer(Player player) {
        this.player = player;
    }

    public boolean isInsideRegion(String regionName) {
        RegionManager manager = WGBukkit.getRegionManager(player.getWorld());
        Set<ProtectedRegion> regionSet = manager.getApplicableRegions(player.getLocation()).getRegions();

        return regionSet.stream().anyMatch(protectedRegion -> protectedRegion.getId().equals(regionName));
    }

    public String getPrivilegeName() {
        PermissionUser pexUser = PermissionsEx.getUser(player);
        return pexUser.getPrefix();
    }

    public static GardevoirPlayer of(Player player) {
        if (!players.containsKey(player)) {
            players.put(player, new GardevoirPlayer(player));
        }

        return players.get(player);
    }
}
