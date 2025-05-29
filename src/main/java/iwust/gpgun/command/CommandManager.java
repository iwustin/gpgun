package iwust.gpgun.command;

import iwust.gpgun.Gpgun;
import iwust.gpgun.annotation.GpgunCommand;
import iwust.gpgun.constant.Package;
import iwust.gpgun.logger.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.reflections.Reflections;

import java.util.Set;

public final class CommandManager {
    public static void registerCommands() {
        Reflections reflections = new Reflections(Package.IWUST_GPGUN);
        Set<Class<?>> commandClasses = reflections.getTypesAnnotatedWith(GpgunCommand.class);

        for (Class<?> commandClass : commandClasses) {
            if (CommandExecutor.class.isAssignableFrom(commandClass)) {
                try {
                    GpgunCommand annotation = commandClass.getAnnotation(GpgunCommand.class);
                    CommandExecutor executor = (CommandExecutor) commandClass.getDeclaredConstructor().newInstance();
                    PluginCommand command = Gpgun.getInstance().getCommand(annotation.name());
                    if(command != null) {
                        command.setExecutor(executor);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception exception) {
                    Logger.severe("Cannot load commands.");
                    Bukkit.getServer().shutdown();
                    return;
                }
            }
        }
    }
}