package iwust.gpgun.listener;

import org.reflections.Reflections;

import iwust.gpgun.Gpgun;
import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Package;
import iwust.gpgun.logger.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Set;

public final class ListenerManager {
    public static void registerListeners() {
        Reflections reflections = new Reflections(Package.IWUST_GPGUN);
        Set<Class<?>> listenerClasses = reflections.getTypesAnnotatedWith(GpgunListener.class);

        for (Class<?> listenerClass : listenerClasses) {
            if (Listener.class.isAssignableFrom(listenerClass)) {
                try {
                    Listener listener = (Listener) listenerClass.getDeclaredConstructor().newInstance();
                    Bukkit.getPluginManager().registerEvents(listener, Gpgun.getInstance());
                } catch (Exception exception) {
                    Logger.severe("Cannot load listeners.");
                    Bukkit.getServer().shutdown();
                    return;
                }
            }
        }
    }
}