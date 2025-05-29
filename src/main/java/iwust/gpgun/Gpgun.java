package iwust.gpgun;

import iwust.gpgun.command.CommandManager;
import iwust.gpgun.config.Config;
import iwust.gpgun.constant.FilePath;
import iwust.gpgun.listener.ListenerManager;
import iwust.gpgun.logger.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public final class Gpgun extends JavaPlugin {
    @Getter
    private static JavaPlugin instance;
    @Getter
    private static Config configuration;

    @Override
    public void onEnable() {
        instance = this;

        try {
            configuration = new Config(FilePath.CONFIG);
            configuration.validate();
            configuration.check();
            Logger.info("Configuration file loaded.");
        } catch (Exception exception) {
            Logger.severe("Cannot load configuration file '" + FilePath.CONFIG + "'.");
            if(exception.getMessage() != null) {
                Logger.severe(exception.getMessage());
            }
            Bukkit.getServer().shutdown();
            return;
        }

        ListenerManager.registerListeners();
        CommandManager.registerCommands();
    }
}
