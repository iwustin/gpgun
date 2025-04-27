package iwust.gpgun.item.type.gun.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

@GpgunListener
public final class GunShotListener implements Listener {
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

        Gun gun = Factory.GUN.createCommon(itemStack);
        if(gun == null) {
            return;
        }

        if(gun.getAmmo() < 1) {
            return;
        }

        event.setCancelled(true);
        if(TypeUtil.isTick(itemStack, (int) (gun.getShotDelay() * 20))) {
            TypeUtil.updateTick(itemStack);
            gun.setDamage(gun.getDamage() + 1);
            gun.setAmmo(gun.getAmmo() - 1);
            Factory.GUN.editItemStack(itemStack, gun);
            Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
            TypeUtil.shot(
                    player,
                    gun.getShotSound(),
                    gun.getBulletAmount(),
                    scope,
                    gun.getSpread(),
                    gun.getBulletDamage(),
                    gun.getVelocity()
            );
        }
    }
}