package iwust.gpgun.item.type.gun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.gun.Gun;
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
public final class GunUnloadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!(event.getClick() == ClickType.DROP)) {
            return;
        }

        ItemStack gunItemStack = event.getCurrentItem();
        if(gunItemStack == null) {
            return;
        }

        Gun gun = Factory.GUN.createCommon(gunItemStack);
        if(gun == null) {
            return;
        }

        if(!(ItemManager.getCommonById(gun.getAmmoId()) instanceof Common common)) {
            return;
        }

        gun.setAmmo(gun.getAmmo() - 1);
        if(Factory.GUN.editItemStack(gunItemStack, gun)) {
            event.setCancelled(true);
            if(TypeUtil.isTick(gunItemStack, (int) ((gun.getUseDelay() * 20)))) {
                TypeUtil.updateTick(gunItemStack);
                PlayerUtil.addItemStack(event.getWhoClicked(),  Factory.COMMON.createItemStack(common));
                SoundManager.playSoundToPlayer(player, gun.getUnloadSound());
            } else {
                gun.setAmmo(gun.getAmmo() + 1);
                Factory.GUN.editItemStack(gunItemStack, gun);
            }
        }
    }
}
