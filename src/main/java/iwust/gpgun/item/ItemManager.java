package iwust.gpgun.item;

import iwust.gpgun.Gpgun;
import iwust.gpgun.item.type.common.Common;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public final class ItemManager {
    @NonNull
    public static List<String> getIdList() {
        return Gpgun.getConfiguration()
                .getConfig()
                .getItems()
                .stream()
                .map(Common::getId)
                .toList();
    }

    @Nullable
    public static ItemStack getItemStackById(@NonNull final String id) {
        List<Common> itemList = Gpgun.getConfiguration().getConfig().getItems();
        for (Common common : itemList) {
            if (common.getId().equals(id)) {
                return  common.getFactory().createItemStack(common);
            }
        }
        return null;
    }

    @NonNull
    public static List<ItemStack> getItemStackList() {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (String id : getIdList()) {
            ItemStack itemStack = getItemStackById(id);
            if (itemStack != null) {
                itemStackList.add(itemStack);
            }
        }
        return itemStackList;
    }

    @Nullable
    public static Common getCommonById(@NonNull final String id) {
        return getCommonById(id, false);
    }

    @Nullable
    public static Common getCommonById(@NonNull final String id, final boolean original) {
        List<Common> itemList = Gpgun.getConfiguration().getConfig().getItems();
        for (Common common : itemList) {
            if (common.getId().equals(id)) {
                if(original) {
                    return common;
                } else {
                    return common.toBuilder().build();
                }
            }
        }
        return null;
    }
}
