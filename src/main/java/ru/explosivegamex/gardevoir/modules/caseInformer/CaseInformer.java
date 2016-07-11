package ru.explosivegamex.gardevoir.modules.caseInformer;

import net.lightshard.itemcases.event.RewardReceivedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.modules.Module;
import ru.explosivegamex.gardevoir.modules.caseInformer.fireworkScripts.SquareScript;
import ru.explosivegamex.gardevoir.util.GardevoirPlayer;
import ru.explosivegamex.gardevoir.util.Inform;
import ru.explosivegamex.gardevoir.util.MessageType;

import java.util.HashMap;
import java.util.Map;

public class CaseInformer extends Module<GardevoirMain> implements Listener {

    public CaseInformer(GardevoirMain plugin) {
        super(plugin);
    }

    @Override
    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("Module 'CaseInformer' has been enabled.");
    }

    @EventHandler
    public void on(RewardReceivedEvent e) {
        if (e.getItemCase().getName().equals("privilege")) {
            runOpenCaseEffect(e.getPlayer());
        }
    }

    private void runOpenCaseEffect(Player player) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("player", player.getName());
        parameters.put("privilege", GardevoirPlayer.of(player).getPrivilegeName());

        Inform.toAll("Игрок %player% открыл кейс с привилегией - %privilege%. Поздравим его!", MessageType.INFO, parameters);
        Inform.to(player, "Поздравляем Вас с открытием кейса. Вы можете написать команду /menu и перейти в пункт \"Донат\", после чего найти и прочитать все возможности Вашей новой привилегии. Так же не забывайте ознакомиться с правилами для донатеров, дабы избежать неприятной ситуации."
                , MessageType.SUCCESS);

        FireworksProcessor fireworks = new FireworksProcessor(new SquareScript(player.getLocation()));
        fireworks.run();
    }

    @Override
    public void disable() {
        plugin.getLogger().info("Module 'CaseInformer' has been disabled.");
    }
}
