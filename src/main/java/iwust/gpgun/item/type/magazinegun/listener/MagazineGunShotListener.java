package iwust.gpgun.item.type.magazinegun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class MagazineGunShotListener implements Listener {
    @EventHandler
    public void onInteract(@NonNull final PlayerInteractEvent event) {
        TypeUtil.scheduleRepeated(() -> handleInteract(event));
        handleInteract(event);
    }

    private void handleInteract(@NonNull final PlayerInteractEvent event) {
        if(!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.isEmpty()) {
            return;
        }

        Boolean isEquip = ItemStackUtil.getPDC(itemStack, Key.EQUIP, PDT.BOOLEAN);
        if(isEquip == null || !isEquip) {
            return;
        }

        MagazineGun magazineGun = Factory.MAGAZINE_GUN.createCommon(itemStack);
        if(magazineGun == null) {
            return;
        }

        if(magazineGun.getAmmo() < 1) {
            return;
        }

        event.setCancelled(true);
        if(TypeUtil.isTick(itemStack, (int) (magazineGun.getShotDelay() * 20))) {
            TypeUtil.updateTick(itemStack);
            magazineGun.setDamage(ItemStackUtil.getDamage(itemStack) + 1);
            magazineGun.setAmmo(magazineGun.getAmmo() - 1);
            Factory.MAGAZINE_GUN.editItemStack(itemStack, magazineGun);
            Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
            TypeUtil.shot(
                    player,
                    magazineGun.getShotSound(),
                    magazineGun.getBulletAmount(),
                    scope,
                    magazineGun.getSpread(),
                    magazineGun.getBulletDamage(),
                    magazineGun.getVelocity()
            );
        }
    }
}
