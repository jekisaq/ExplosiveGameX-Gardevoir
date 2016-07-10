package ru.explosivegamex.gardevoir.modules.caseInformer;

import net.lightshard.itemcases.event.RewardReceivedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.explosivegamex.gardevoir.modules.Module;
import ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts.CubeScript;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;

public class CaseInformer extends Module implements Listener {

    public CaseInformer(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("Module 'CaseInformer' has been enabled.");
    }

    @EventHandler
    public void on(RewardReceivedEvent e) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("player", e.getPlayer().getName());
        parameters.put("privilege", getPrivilegeName(e));

        Inform.toAll("Игрок %player% открыл кейс с привилегией - %privilege%. Поздравим его!", MessageType.INFO, parameters);
        Inform.to(e.getPlayer(), "Поздравляем Вас с открытием кейса. Вы можете написать команду /menu и перейти в пункт \"Донат\", после чего найти и прочитать все возможности Вашей новой привилегии. Так же не забывайте ознакомиться с правилами для донатеров, дабы избежать неприятной ситуации."
                , MessageType.SUCCESS);

        FireworksProcessor fireworks = new FireworksProcessor(new CubeScript(e.getPlayer().getLocation()));
        fireworks.run();
    }

    private String getPrivilegeName(RewardReceivedEvent e) {
        PermissionUser pexUser = PermissionsEx.getUser(e.getPlayer());
        return pexUser.getPrefix();
    }

    @Override
    public void disable() {
        plugin.getLogger().info("Module 'CaseInformer' has been disabled.");
    }
}
