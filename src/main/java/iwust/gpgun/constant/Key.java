package iwust.gpgun.constant;

import iwust.gpgun.Gpgun;

import org.bukkit.NamespacedKey;

import lombok.NonNull;

public final class Key {
    @NonNull
    public static final NamespacedKey ID = new NamespacedKey(Gpgun.getInstance(), "id");
    @NonNull
    public static final NamespacedKey GUI_ID = new NamespacedKey(Gpgun.getInstance(), "gui_id");
    @NonNull
    public static final NamespacedKey AMMO = new NamespacedKey(Gpgun.getInstance(), "ammo");
    @NonNull
    public static final NamespacedKey TICK = new NamespacedKey(Gpgun.getInstance(), "tick");
    @NonNull
    public static final NamespacedKey EQUIP = new NamespacedKey(Gpgun.getInstance(), "equip");
    @NonNull
    public static final NamespacedKey SCOPE = new NamespacedKey(Gpgun.getInstance(), "scope");
}
