package iwust.gpgun.item;

import iwust.gpgun.item.type.common.Common;

import org.bukkit.inventory.ItemStack;

import lombok.NonNull;

public interface ItemFactory {
    ItemStack createItemStack(@NonNull final Common common);
    Common createCommon(@NonNull final ItemStack itemStack);
    boolean editItemStack(@NonNull final ItemStack itemStack,
                          @NonNull final Common common);
}
