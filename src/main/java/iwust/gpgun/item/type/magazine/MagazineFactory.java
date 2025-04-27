package iwust.gpgun.item.type.magazine;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import lombok.NonNull;

public final class MagazineFactory implements ItemFactory {
    private boolean factory(@NonNull final ItemStack itemStack,
                            @NonNull final Magazine magazine) {
        Factory.COMMON.editItemStack(itemStack, magazine);
        if(!validate(magazine)) {
            return false;
        }
        return applyProperties(itemStack, magazine);
    }

    @Nullable
    @Override
    public ItemStack createItemStack(@NonNull final Common common) {
        if(!(common instanceof Magazine magazine)) {
            return null;
        }
        ItemStack itemStack = Factory.COMMON.createItemStack(common);
        if(factory(itemStack, magazine)) {
            return itemStack;
        }
        return null;
    }

    @Nullable
    @Override
    public Magazine createCommon(@NonNull final ItemStack itemStack) {
        if(!(Factory.COMMON.createCommon(itemStack) instanceof Magazine magazine)) {
            return null;
        }
        Integer ammo = ItemStackUtil.getPDC(itemStack, Key.AMMO, PDT.INTEGER);
        if(ammo == null) {
            return null;
        }
        magazine.setAmmo(ammo);
        return magazine;
    }

    private boolean validate(@NonNull final Magazine magazine) {
        return magazine.getAmmo() >= 0 &&
                magazine.getMaxAmmo() >= 1 &&
                magazine.getAmmo() <= magazine.getMaxAmmo() &&
                ItemManager.getItemStackById(magazine.getAmmoId()) != null &&
                ItemManager.getCommonById(magazine.getAmmoId()) != null;
    }

    private boolean applyProperties(@NonNull final ItemStack itemStack,
                                    @NonNull final Magazine magazine) {
        ItemStackUtil.setMaxStackSize(itemStack, 1);
        ItemStackUtil.setUnbreakable(itemStack, true);
        ItemStackUtil.setPDC(itemStack, Key.AMMO, PDT.INTEGER, magazine.getAmmo());
        return applyLore(itemStack, magazine);
    }

    private boolean applyLore(@NonNull final ItemStack itemStack,
                              @NonNull final Magazine magazine) {
        Common common = ItemManager.getCommonById(magazine.getAmmoId());
        if(common == null) {
            return false;
        }

        if (common.getDisplayName() != null) {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    magazine.getLore(),
                    magazine.getDisplayAmmoName(),
                    common.getDisplayName())
            ) {
                return false;
            }
        } else {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    magazine.getLore(),
                    magazine.getDisplayAmmoName(),
                    common.getCustomName())
            ) {
                return false;
            }
        }
        if(!TypeUtil.applyLoreDisplayAmmoAmount(
                itemStack,
                ItemStackUtil.getLore(itemStack),
                magazine.getDisplayAmmoAmount(),
                magazine.getAmmo(),
                magazine.getMaxAmmo()
        )) {
            return false;
        }
        TypeUtil.applyLoreDisplayAmmoUnload(itemStack, ItemStackUtil.getLore(itemStack), magazine.getDisplayAmmoUnload());
        return true;
    }

    @Override
    public boolean editItemStack(@NonNull final ItemStack itemStack,
                                 @NonNull final Common common) {
        if(!(common instanceof Magazine magazine)) {
            return false;
        }
        return factory(itemStack, magazine);
    }
}
