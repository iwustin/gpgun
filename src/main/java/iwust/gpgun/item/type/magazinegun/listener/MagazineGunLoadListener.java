package iwust.gpgun.item.type.magazinegun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
import iwust.gpgun.sound.SoundManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class MagazineGunLoadListener implements Listener {
    @EventHandler
    public void onClick(@NonNull final InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if(!event.getClick().isMouseClick()) {
            return;
        }

        ItemStack magazineGunItemStack = event.getCurrentItem();
        if(magazineGunItemStack == null) {
            return;
        }

        ItemStack magazineItemStack = event.getCursor();
        if(magazineItemStack.isEmpty()) {
            return;
        }

        MagazineGun magazineGun = Factory.MAGAZINE_GUN.createCommon(magazineGunItemStack);
        if(magazineGun == null) {
            return;
        }

        if(magazineGun.getAmmo() > -1) {
            return;
        }

        Magazine magazine = Factory.MAGAZINE.createCommon(magazineItemStack);
        if(magazine == null) {
            return;
        }

        if(!magazineGun.getAmmoId().equals(magazine.getId())) {
            return;
        }

        magazineGun.setAmmo(magazine.getAmmo());
        if(Factory.MAGAZINE_GUN.editItemStack(magazineGunItemStack, magazineGun)) {
            event.setCancelled(true);
            magazineItemStack.setAmount(0);
            SoundManager.playSoundToPlayer(player, magazineGun.getLoadSound());
        }
    }
}
