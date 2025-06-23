package iwust.gpgun.listener.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

import lombok.NonNull;

@GpgunListener
public final class ScopeListener implements Listener {

    @EventHandler
    public void onSwap(@NonNull final PlayerSwapHandItemsEvent event) {
        ItemStack itemStack = event.getOffHandItem();

        int maxScope = TypeUtil.getScope(itemStack);
        if(maxScope == -1) {
            return;
        }

        Player player = event.getPlayer();

        Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
        if(scope != null) {
            event.setCancelled(true);
            applyScope(player, itemStack, scope, maxScope);
            updateScope(itemStack);
            TypeUtil.updateHeldItem(player, itemStack);
        }
    }

    @EventHandler
    public void onDrop(@NonNull final PlayerDropItemEvent event) {
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if(TypeUtil.isGunItemStack(itemStack)) {
            resetScope(event.getPlayer(), itemStack);
            event.getItemDrop().setItemStack(itemStack);
        }
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack previousItemStack = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItemStack = player.getInventory().getItem(event.getNewSlot());

        if(previousItemStack != null && TypeUtil.isGunItemStack(previousItemStack)) {
            resetScope(player, previousItemStack);
            player.getInventory().setItem(event.getPreviousSlot(), previousItemStack);
        }
        if(newItemStack != null && TypeUtil.isGunItemStack(newItemStack)) {
            resetScope(player, newItemStack);
            player.getInventory().setItem(event.getNewSlot(), newItemStack);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack currentItemStack = event.getCurrentItem();
        ItemStack cursorItemStack = event.getCursor();

        if(currentItemStack != null && TypeUtil.isGunItemStack(currentItemStack)) {
            resetScope(player, currentItemStack);
            event.setCurrentItem(currentItemStack);
        }
        if(TypeUtil.isGunItemStack(cursorItemStack)) {
            resetScope(player, cursorItemStack);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            return;
        }

        Map<Integer, ItemStack> newItems = event.getNewItems();
        for(Map.Entry<Integer, ItemStack> entry : newItems.entrySet()) {
            if(entry.getKey() != null &&
                    entry.getKey() == 40 &&
                    entry.getValue() != null &&
                    TypeUtil.isGunItemStack(entry.getValue())
            ) {
                event.setCancelled(true);
            }
        }
    }

    private void applyScope(@NonNull final Player player,
                            @NonNull final ItemStack itemStack,
                            final int scope,
                            final int maxScope) {
        if(scope == maxScope) {
            resetScope(player, itemStack);
            return;
        }
        for(int i = 0;i < maxScope;i++) {
            if(scope == i) {
                ItemStackUtil.setPDC(itemStack, Key.SCOPE, PDT.INTEGER, i + 1);
                PotionEffect effect = new PotionEffect(
                        PotionEffectType.SLOWNESS,
                        -1,
                        i + 2,
                        false,
                        false,
                        false
                );
                player.addPotionEffect(effect);
                updateScope(itemStack);
            }
        }
    }

    private void resetScope(@NonNull final Player player,
                            @NonNull final ItemStack itemStack) {
        player.removePotionEffect(PotionEffectType.SLOWNESS);
        ItemStackUtil.setPDC(itemStack, Key.SCOPE, PDT.INTEGER, 0);
        updateScope(itemStack);
    }

    private void updateScope(@NonNull final ItemStack itemStack) {
        Gun gun = Factory.GUN.createCommon(itemStack);
        MagazineGun magazineGun = Factory.MAGAZINE_GUN.createCommon(itemStack);
        if(gun != null) {
            gun.getFactory().editItemStack(itemStack, gun);
        } else if(magazineGun != null) {
            magazineGun.getFactory().editItemStack(itemStack, magazineGun);
        }
    }
}