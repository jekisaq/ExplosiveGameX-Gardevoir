package ru.explosivegamex.gardevoir.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Informer {

    public static void to(Player player, String text) {
        to(player, text, MessageType.INFO);
    }

    public static void to(Player player, String text, MessageType type) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getChatMessage(type, text)));
    }

    private static String getChatMessage(MessageType type, String text) {
        return "&9&l[&6&lInfo&9&l] " + type.getCode() + text;
    }
}
