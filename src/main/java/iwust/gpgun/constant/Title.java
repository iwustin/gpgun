package iwust.gpgun.constant;

import net.kyori.adventure.text.Component;

import iwust.gpgun.util.StringUtil;

import lombok.NonNull;

public final class Title {
    @NonNull
    public static final Component MENU = StringUtil.deserialize(Color.DEFAULT + "Меню");
    @NonNull
    public static final Component ITEMS = StringUtil.deserialize(Color.DEFAULT + "Предметы");
}
