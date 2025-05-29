package iwust.gpgun.util;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.Unbreakable;
import net.kyori.adventure.text.Component;

import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.common.Common;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public final class ItemStackUtil {
    public static void unsetExtraData(@NonNull final ItemStack itemStack) {
        itemStack.unsetData(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        itemStack.unsetData(DataComponentTypes.TOOL);
        itemStack.unsetData(DataComponentTypes.RARITY);
    }

    public static void setCustomName(@NonNull final ItemStack itemStack,
                                     @Nullable final String customName) {
        if(customName != null) {
            Component customNameComponent = StringUtil.deserialize(customName);
            itemStack.setData(DataComponentTypes.CUSTOM_NAME, customNameComponent);
        }
    }

    @Nullable
    public static String getCustomName(@NonNull final ItemStack itemStack) {
        Component customNameComponent = itemStack.getData(DataComponentTypes.CUSTOM_NAME);
        return customNameComponent != null ? StringUtil.serialize(customNameComponent) : null;
    }

    public static void setLore(@NonNull final ItemStack itemStack,
                               @Nullable final List<String> lore) {
        if(lore != null) {
            ItemLore.Builder itemLore = ItemLore.lore();
            lore.stream()
                    .map(StringUtil::deserialize)
                    .forEach(itemLore::addLine);
            itemStack.setData(DataComponentTypes.LORE, itemLore);
        }
    }

    @NonNull
    public static List<String> getLore(@NonNull final ItemStack itemStack) {
        ItemLore itemLore = itemStack.getData(DataComponentTypes.LORE);
        List<String> lore = new ArrayList<>();
        if(itemLore != null) {
            for(Component line : itemLore.lines()) {
                lore.add(StringUtil.serialize(line));
            }
            return lore;
        }
        return lore;
    }

    public static void setItemModel(@NonNull final ItemStack itemStack,
                                    final String itemModel) {
        if(itemModel != null) {
            net.kyori.adventure.key.Key key = net.kyori.adventure.key.Key.key(itemModel);
            itemStack.setData(DataComponentTypes.ITEM_MODEL, key);
        }
    }

    @Nullable
    public static String getItemModel(@NonNull final ItemStack itemStack) {
        net.kyori.adventure.key.Key key = itemStack.getData(DataComponentTypes.ITEM_MODEL);
        if(key != null) {
            return key.asString();
        }
        return null;
    }

    public static void setMaxStackSize(@NonNull final ItemStack itemStack,
                                       final int maxStackSize) {
        if(maxStackSize > 0) {
            itemStack.setData(DataComponentTypes.MAX_STACK_SIZE, maxStackSize);
        }
    }

    public static int getMaxStackSize(@NonNull final ItemStack itemStack) {
        Integer maxStackSize = itemStack.getData(DataComponentTypes.MAX_STACK_SIZE);
        return maxStackSize != null ? maxStackSize : itemStack.getType().getMaxStackSize();
    }

    public static void setMaxDamage(@NonNull final ItemStack itemStack,
                                    final int maxDamage) {
        if(maxDamage > 0) {
            itemStack.setData(DataComponentTypes.MAX_DAMAGE, maxDamage);
        }
    }

    public static int getMaxDamage(@NonNull final ItemStack itemStack) {
        Integer maxDamage = itemStack.getData(DataComponentTypes.MAX_DAMAGE);
        return maxDamage != null ? maxDamage : itemStack.getType().getMaxDurability();
    }

    public static void setDamage(@NonNull final ItemStack itemStack,
                                 final int damage) {
        Integer maxDamage = itemStack.getData(DataComponentTypes.MAX_DAMAGE);
        if(maxDamage != null) {
            if(damage == maxDamage) {
                itemStack.setAmount(0);
            }
            if(damage >= 0 && damage < maxDamage) {
                itemStack.setData(DataComponentTypes.DAMAGE, damage);
            }
        }
    }

    public static int getDamage(@NonNull final ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return ((Damageable) itemMeta).getDamage();
    }

    public static void setUnbreakable(@NonNull final ItemStack itemStack,
                                      final boolean unbreakable) {

        if(unbreakable) {
            itemStack.setData(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable(false));
        } else {
            itemStack.unsetData(DataComponentTypes.UNBREAKABLE);
        }
    }

    public static boolean isUnbreakable(@NonNull final ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.isUnbreakable();
    }

    public static <T, Z> void setPDC(@NonNull final ItemStack itemStack,
                                     @NonNull final NamespacedKey key,
                                     @NonNull final PersistentDataType<T, Z> dataType,
                                     @NonNull final Z value) {
        itemStack.editMeta(itemMeta -> {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.set(key, dataType, value);
        });
    }

    @Nullable
    public static <T, Z> Z getPDC(@NonNull final ItemStack itemStack,
                                  @NonNull final NamespacedKey key,
                                  @NonNull final PersistentDataType<T, Z> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) {
            return null;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(key, dataType);
    }

    public static <T, Z> boolean hasPDC(@NonNull final ItemStack itemStack,
                                        @NonNull final NamespacedKey key,
                                        @NonNull final PersistentDataType<T, Z> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) {
            return false;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(key, dataType);
    }

    public static <T, Z> void removePDC(@NonNull final ItemStack itemStack,
                                        @NonNull final NamespacedKey key,
                                        @NonNull final PersistentDataType<T, Z> dataType) {
        itemStack.editMeta(itemMeta -> {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(key);
        });
    }

    @Nullable
    public static <T extends Common> T getType(@NonNull ItemStack itemStack,
                                               @NonNull Class<T> type) {
        String id = ItemStackUtil.getPDC(itemStack, Key.ID, PDT.STRING);
        if (id == null) {
            return null;
        }

        Common common = ItemManager.getCommonById(id);
        if (type.isInstance(common)) {
            return type.cast(common);
        }
        return null;
    }
}