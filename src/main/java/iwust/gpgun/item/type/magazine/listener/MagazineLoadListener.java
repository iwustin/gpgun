package iwust.gpgun.item.type.magazine.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.sound.SoundManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class MagazineLoadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!event.getClick().isMouseClick()) {
            return;
        }

        ItemStack magazineItemStack = event.getCurrentItem();
        if(magazineItemStack == null) {
            return;
        }

        ItemStack ammoItemStack = event.getCursor();
        if(ammoItemStack.isEmpty()) {
            return;
        }

        Magazine magazine = Factory.MAGAZINE.createCommon(magazineItemStack);
        if(magazine == null) {
            return;
        }

        Common common = Factory.COMMON.createCommon(ammoItemStack);
        if(common == null) {
            return;
        }

        if(!common.getId().equals(magazine.getAmmoId())) {
            return;
        }

        magazine.setAmmo(magazine.getAmmo() + 1);
        if(Factory.MAGAZINE.editItemStack(magazineItemStack, magazine)) {
            event.setCancelled(true);
            if(TypeUtil.isTick(magazineItemStack, (int) ((magazine.getUseDelay() * 20)))) {
                TypeUtil.updateTick(magazineItemStack);
                ammoItemStack.setAmount(ammoItemStack.getAmount() - 1);
                SoundManager.playSoundToPlayer(player, magazine.getLoadSound());
            } else {
                magazine.setAmmo(magazine.getAmmo() - 1);
                Factory.MAGAZINE.editItemStack(magazineItemStack, magazine);
            }
        }
    }
}
