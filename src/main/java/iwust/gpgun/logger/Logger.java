package iwust.gpgun.logger;

import iwust.gpgun.Gpgun;

import lombok.NonNull;

public final class Logger {
    public static void info(@NonNull final String message) {
        Gpgun.getInstance().getLogger().info(message);
    }
    public static void warning(@NonNull final String message) {
        Gpgun.getInstance().getLogger().warning(message);
    }
    public static void severe(@NonNull final String message) {
        Gpgun.getInstance().getLogger().severe(message);
    }
}
