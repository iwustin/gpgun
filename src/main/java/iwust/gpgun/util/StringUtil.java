package iwust.gpgun.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import lombok.NonNull;

public final class StringUtil {
    @NonNull
    public static Component deserialize(@NonNull String input, boolean italic) {
        return italic ? MiniMessage.miniMessage().deserialize(input) : deserialize(input);
    }

    @NonNull
    public static Component deserialize(@NonNull String input) {
        return MiniMessage.miniMessage().deserialize(input).decoration(TextDecoration.ITALIC, false);
    }

    @NonNull
    public static String serialize(@NonNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
}
