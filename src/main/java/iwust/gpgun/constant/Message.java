package iwust.gpgun.constant;

import net.kyori.adventure.text.Component;

import iwust.gpgun.util.StringUtil;

import lombok.NonNull;

public final class Message {
    @NonNull
    public static final Component NO_OP = StringUtil.deserialize(Color.DEFAULT + "Вы не оператор.");
    @NonNull
    public static final Component OPEN_MENU = StringUtil.deserialize(Color.DEFAULT + "Открытие меню.");
    @NonNull
    public static final Component OPEN_ITEMS = StringUtil.deserialize(Color.DEFAULT + "Открытие предметов.");
}