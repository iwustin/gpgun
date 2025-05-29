package iwust.gpgun.scheduler;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;

import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitTask;

import lombok.NonNull;
import lombok.Setter;

public final class EquipItemScheduler implements Runnable {
    @NonNull
    private final Player player;
    @NonNull
    private final ItemStack itemStack;

    @Setter
    private BukkitTask task;
    private float equipTime;

    public EquipItemScheduler(@NonNull final Player player,
                              @NonNull final ItemStack itemStack,
                              float equipTime) {
        this.player = player;
        this.itemStack = itemStack;
        this.equipTime = equipTime;
    }

    @Override
    public void run() {
        Boolean equip = ItemStackUtil.getPDC(itemStack, Key.EQUIP, PDT.BOOLEAN);
        if(equip == null || equip) {
            stop(); return;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack heldItemStack = inventory.getItemInMainHand().clone();
        unsetScopeData(heldItemStack);

        if (!heldItemStack.equals(unsetScopeData(itemStack.clone()))) {
            stop(); return;
        }

        Component component = Component.text(String.format("%04.1f", equipTime));
        player.sendActionBar(component);
        if (equipTime <= 0) {
            ItemStack finalItemStack = inventory.getItemInMainHand();
            ItemStackUtil.setPDC(finalItemStack, Key.EQUIP, PDT.BOOLEAN, true);
            TypeUtil.updateHeldItem(player, finalItemStack);
            stop(); return;
        }
        equipTime -= 0.05f;
    }

    private void stop() {
        player.sendActionBar(Component.text(""));
        if (task != null) {
            task.cancel();
        }
    }

    @NonNull
    private ItemStack unsetScopeData(@NonNull final ItemStack itemStack) {
        itemStack.unsetData(DataComponentTypes.ITEM_MODEL);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) {
            return itemStack;
        }

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.remove(Key.SCOPE);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}