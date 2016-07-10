package ru.explosivegamex.gardevoir.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Inform
{

    public static void to(CommandSender sender, String text) {
        to(sender, text, MessageType.INFO);
    }

    public static void to(CommandSender sender, String text, MessageType type) {
        to(sender, text, type, new HashMap<>());
    }

    public static void to(CommandSender sender, String text, MessageType type, Map<String, String> replacements) {
        addMessageChatModificationInEndOfReplaceableValue(type, replacements);

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getChatMessage(type, text, replacements)));
    }

    public static void toAll(String text) {
        toAll(text, MessageType.INFO);
    }

    public static void toAll(String text, MessageType type) {
        toAll(text, type, new HashMap<>());
    }

    public static void toAll(String text, MessageType type, Map<String, String> replacements) {
        addMessageChatModificationInEndOfReplaceableValue(type, replacements);

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getChatMessage(type, text, replacements)));
    }

    private static String getChatMessage(MessageType type, String text, Map<String, String> replacements) {
        return "&9&l[&6&lInfo&9&l] " + type.getCode() + replaceTokens(text, replacements);
    }

    private static void addMessageChatModificationInEndOfReplaceableValue(MessageType type, Map<String, String> replacements) {
        for (Map.Entry<String, String> pair : replacements.entrySet()) {
            pair.setValue(pair.getValue() + type.getCode());
        }
    }

    public static String replaceTokens(String text, Map<String, String> replacements) {
        Pattern pattern = Pattern.compile("%(.+?)%");
        Matcher matcher = pattern.matcher(text);
        StringBuilder builder = new StringBuilder();

        int i = 0;
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));

            if (replacement == null) {
                builder.append(matcher.group(0));
            } else {
                builder.append(replacement);
            }

            i = matcher.end();
        }

        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }
}
