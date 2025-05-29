package iwust.gpgun.item.type.magazine.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.sound.SoundManager;
import iwust.gpgun.util.PlayerUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class MagazineUnloadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!(event.getClick() == ClickType.DROP)) {
            return;
        }

        ItemStack magazineItemStack = event.getCurrentItem();
        if(magazineItemStack == null) {
            return;
        }

        Magazine magazine = Factory.MAGAZINE.createCommon(magazineItemStack);
        if(magazine == null) {
            return;
        }

        if(!(ItemManager.getCommonById(magazine.getAmmoId()) instanceof Common common)) {
            return;
        }

        magazine.setAmmo(magazine.getAmmo() - 1);
        if(Factory.MAGAZINE.editItemStack(magazineItemStack, magazine)) {
            event.setCancelled(true);
            if(TypeUtil.isTick(magazineItemStack, (int) ((magazine.getUseDelay() * 20)))) {
                TypeUtil.updateTick(magazineItemStack);
                PlayerUtil.addItemStack(event.getWhoClicked(),  Factory.COMMON.createItemStack(common));
                SoundManager.playSoundToPlayer((Player) event.getWhoClicked(), magazine.getUnloadSound());
            } else {
                magazine.setAmmo(magazine.getAmmo() + 1);
                Factory.MAGAZINE.editItemStack(magazineItemStack, magazine);
            }
        }
    }
}
