package iwust.gpgun.listener.listener;

import iwust.gpgun.Gpgun;
import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.scheduler.EquipItemScheduler;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;

import lombok.NonNull;

@GpgunListener
public final class EquipItemListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player player) {
            resetEquip(event.getCurrentItem());
            resetEquip(event.getCursor());
            Bukkit.getScheduler().runTask(Gpgun.getInstance(), new EquipItem(player));
        }
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack previousItemStack = player.getInventory().getItem(event.getPreviousSlot());
        ItemStack newItemStack = player.getInventory().getItem(event.getNewSlot());

        resetEquip(previousItemStack);
        resetEquip(newItemStack);
        Bukkit.getScheduler().runTask(Gpgun.getInstance(), new EquipItem(player));
    }

    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        resetEquip(itemStack);
        Bukkit.getScheduler().runTask(Gpgun.getInstance(), new EquipItem(event.getPlayer()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        resetEquip(itemStack);
        Bukkit.getScheduler().runTask(Gpgun.getInstance(), new EquipItem(player));
    }

    private void resetEquip(@Nullable final ItemStack itemStack) {
        if(itemStack == null) {
            return;
        }
        ItemStackUtil.setPDC(itemStack, Key.EQUIP, PDT.BOOLEAN, false);
    }

    static class EquipItem implements Runnable {
        @NonNull
        private final Player player;

        EquipItem(@NonNull final Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            PlayerInventory inventory = player.getInventory();
            ItemStack itemStack = inventory.getItemInMainHand();

            Boolean equip = ItemStackUtil.getPDC(itemStack, Key.EQUIP, PDT.BOOLEAN);
            if(equip == null || equip) {
                return;
            }

            Common common = ItemStackUtil.getType(itemStack, Common.class);
            if(common == null) {
                return;
            }

            if(common.getEquipDelay() <= 0) {
                return;
            }

            EquipItemScheduler equipItemScheduler = new EquipItemScheduler(
                    player,
                    itemStack,
                    common.getEquipDelay());
            BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(
                    Gpgun.getInstance(),
                    equipItemScheduler,
                    0L,
                    1L);
            equipItemScheduler.setTask(bukkitTask);
        }
    }
}
