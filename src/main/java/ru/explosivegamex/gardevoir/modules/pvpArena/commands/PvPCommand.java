package ru.explosivegamex.gardevoir.modules.pvpArena.commands;

import com.google.common.collect.Maps;
import ru.explosivegamex.gardevoir.GardevoirMain;
import ru.explosivegamex.gardevoir.commands.Command;
import ru.explosivegamex.gardevoir.commands.SubCommand;
import ru.explosivegamex.gardevoir.modules.pvpArena.commands.pvp.LeaveSubCommand;

import java.lang.reflect.Method;
import java.util.Map;

public class PvPCommand extends Command {

    private final Map<String, Method> methods = Maps.newLinkedHashMap();

    private GardevoirMain plugin;

    public PvPCommand(GardevoirMain plugin) {
        this.plugin = plugin;
        setAllowConsole(false);

        registerSubCommands();
    }

    private void registerSubCommands() {
        for(Method method : getClass().getDeclaredMethods()) {
            if(!method.isAnnotationPresent(SubCommand.class)) {
                continue;
            }

            methods.put(method.getName(), method);
        }
    }

    @Override
    public void execute() throws ReflectiveOperationException {
        String subCommand = getArgLength() > 0 ? getArg(0) : "help";
        Method method = methods.get(subCommand.toLowerCase());
        if(method == null) {
            reply(false, "Неизвестная команда");
            return;
        }

        SubCommand info = method.getAnnotation(SubCommand.class);
        if(getArgLength() < info.minArgs() + 1) {
            reply(false, "Эта команда требует другое кол-во аргументов");
            return;
        }

        method.invoke(this);
    }

    @SubCommand(description = "Вывести справку команды", minArgs = -1)
    private void help() {
        reply("Использование: ");

        for(Map.Entry<String, Method> entry : methods.entrySet()) {
            String subCommandName = entry.getKey();
            SubCommand info = entry.getValue().getAnnotation(SubCommand.class);

            reply("&6&l/pvp " + subCommandName + " &7- " + info.description());
        }
    }

    @SubCommand(description = "Покинуть pvp-арену")
    private void leave() {
        LeaveSubCommand leaveSubCommand = new LeaveSubCommand(plugin, player);
        leaveSubCommand.run();
    }
}
