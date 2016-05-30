package ru.explosivegamex.gardevoir.listeners;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.title.TitleAPI;

import static ru.explosivegamex.gardevoir.GardevoirMain.convertToChat;


public class PlayerEventsListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TitleAPI.sendTimings(e.getPlayer(), 10, 30, 10);

        TextComponent welcome = new TextComponent(convertToChat("&7&lДобро пожаловать"));
        TitleAPI.sendTitle(e.getPlayer(), welcome);

        TextComponent toServer = new TextComponent(convertToChat("&7&lна сервер ExplosiveGame&8&lX"));
        TitleAPI.sendSubTitle(e.getPlayer(), toServer);
    }
}
