package iwust.gpgun.item.type.gun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.sound.SoundManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class GunLoadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!event.getClick().isMouseClick()) {
            return;
        }

        ItemStack gunItemStack = event.getCurrentItem();
        if(gunItemStack == null) {
            return;
        }

        ItemStack ammoItemStack = event.getCursor();
        if(ammoItemStack.isEmpty()) {
            return;
        }

        Gun gun = Factory.GUN.createCommon(gunItemStack);
        if(gun == null) {
            return;
        }

        Common common = Factory.COMMON.createCommon(ammoItemStack);
        if(common == null) {
            return;
        }

        if(!common.getId().equals(gun.getAmmoId())) {
            return;
        }

        gun.setAmmo(gun.getAmmo() + 1);
        if(Factory.GUN.editItemStack(gunItemStack, gun)) {
            event.setCancelled(true);
            if(TypeUtil.isTick(gunItemStack, (int) ((gun.getUseDelay() * 20)))) {
                TypeUtil.updateTick(gunItemStack);
                ammoItemStack.setAmount(ammoItemStack.getAmount() - 1);
                SoundManager.playSoundToPlayer(player, gun.getLoadSound());
            } else {
                gun.setAmmo(gun.getAmmo() - 1);
                Factory.GUN.editItemStack(gunItemStack, gun);
            }
        }
    }
}
