package iwust.gpgun.item.type.magazinegun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
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
public final class MagazineGunUnloadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!(event.getClick() == ClickType.DROP)) {
            return;
        }

        ItemStack magazineGunItemStack = event.getCurrentItem();
        if(magazineGunItemStack == null) {
            return;
        }

        MagazineGun magazineGun = Factory.MAGAZINE_GUN.createCommon(magazineGunItemStack);
        if(magazineGun == null) {
            return;
        }

        if(magazineGun.getAmmo() < 0) {
            return;
        }

        if(!(ItemManager.getCommonById(magazineGun.getAmmoId()) instanceof Magazine magazine)) {
            return;
        }

        magazine.setAmmo(magazineGun.getAmmo());
        magazineGun.setAmmo(-1);
        ItemStack magazineItemStack = Factory.MAGAZINE.createItemStack(magazine);
        if(magazineItemStack != null) {
            event.setCancelled(true);
            Factory.MAGAZINE_GUN.editItemStack(magazineGunItemStack, magazineGun);
            PlayerUtil.addItemStack(event.getWhoClicked(), magazineItemStack);
            SoundManager.playSoundToPlayer(player, magazineGun.getUnloadSound());
        }
    }
}
