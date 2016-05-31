package ru.explosivegamex.gardevoir.listeners;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.inventivetalent.title.TitleAPI;

import static ru.explosivegamex.gardevoir.GardevoirMain.convertToChat;


public class PlayerEventsListener extends GardevoirListener {

    private String title, subtitle;

    public PlayerEventsListener(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TitleAPI.sendTimings(e.getPlayer(), 10, 30, 10);

        TextComponent welcome = new TextComponent(convertToChat(this.title));
        TitleAPI.sendTitle(e.getPlayer(), welcome);

        TextComponent toServer = new TextComponent(convertToChat(this.subtitle));
        TitleAPI.sendSubTitle(e.getPlayer(), toServer);
    }
}
