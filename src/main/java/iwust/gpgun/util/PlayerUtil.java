package iwust.gpgun.util;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

public final class PlayerUtil {
    public static void addItemStack(@NonNull final HumanEntity humanEntity,
                                    @NonNull final ItemStack itemStack) {
        addItemStack((Player) humanEntity, itemStack);
    }

    public static void addItemStack(@NonNull final Player player,
                                    @NonNull final ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        if(inventory.firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            inventory.addItem(itemStack);
        }
    }
}
