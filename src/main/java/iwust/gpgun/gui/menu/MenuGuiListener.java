package iwust.gpgun.gui.menu;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.GuiId;
import iwust.gpgun.constant.Key;
import iwust.gpgun.gui.items.ItemsGui;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import lombok.NonNull;

@GpgunListener
public final class MenuGuiListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        ItemStack currentItemStack = event.getCurrentItem();
        if (currentItemStack == null) {
            return;
        }

        String guiId = ItemStackUtil.getPDC(currentItemStack, Key.GUI_ID, PersistentDataType.STRING);
        if(guiId == null) {
            return;
        }

        switch (guiId) {
            case GuiId.ITEMS -> {
                event.setCancelled(true);
                ItemsGui.open((Player) event.getWhoClicked());
            }
        }
    }
}
