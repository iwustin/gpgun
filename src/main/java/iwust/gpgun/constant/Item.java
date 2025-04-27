package iwust.gpgun.constant;

import iwust.gpgun.item.type.gui.Gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

public final class Item {
    @NonNull
    public static final ItemStack ITEMS_GUI;

    static {
        Gui itemsGui = Gui.builder()
                .GuiId(GuiId.ITEMS)
                .id("") /* must be set and empty ALWAYS */
                .material(Material.GLASS_PANE)
                .customName("<#FDEF7D>Предметы")
                .maxStackSize(1)
                .unbreakable(true)
                .build();
        ITEMS_GUI = itemsGui.getFactory().createItemStack(itemsGui);
    }
}
