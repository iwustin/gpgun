package iwust.gpgun.gui.items;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.GuiId;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.util.ItemStackUtil;
import iwust.gpgun.util.PlayerUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class ItemsGuiListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        ItemStack currentItemStack = event.getCurrentItem();
        if (currentItemStack == null) {
            return;
        }

        String guiId = ItemStackUtil.getPDC(currentItemStack, Key.GUI_ID, PDT.STRING);
        if(guiId == null) {
            return;
        }

        if(guiId.equals(GuiId.ITEM_BUTTON)) {
            event.setCancelled(true);

            ItemStack itemStack = currentItemStack.clone();

            int maxStackSize = ItemStackUtil.getMaxStackSize(itemStack);
            itemStack.setAmount(maxStackSize);

            ItemStackUtil.removePDC(itemStack, Key.GUI_ID, PDT.STRING);
            ItemStackUtil.setPDC(itemStack, Key.EQUIP, PDT.BOOLEAN, true);

            PlayerUtil.addItemStack(event.getWhoClicked(), itemStack);
        }
    }
}
