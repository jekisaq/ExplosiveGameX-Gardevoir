package ru.explosivegamex.gardevoir.listeners;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.inventivetalent.title.TitleAPI;

import static ru.explosivegamex.gardevoir.GardevoirMain.convertToChat;


public class AuthorizedTitleListener extends GardevoirListener {

    private String title, subtitle;

    public AuthorizedTitleListener(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @EventHandler
    public void onPlayerAuthorized(PlayerLoginEvent e) {
        TitleAPI.sendTimings(e.getPlayer(), 10, 30, 10);

        TextComponent welcome = new TextComponent(convertToChat(this.title));
        TitleAPI.sendTitle(e.getPlayer(), welcome);

        TextComponent toServer = new TextComponent(convertToChat(this.subtitle));
        TitleAPI.sendSubTitle(e.getPlayer(), toServer);
    }
}
