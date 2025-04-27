package iwust.gpgun.item.type.gun;

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

public final class GunFactory implements ItemFactory {
    private boolean factory(@NonNull final ItemStack itemStack,
                            @NonNull final Gun gun) {
        Factory.COMMON.editItemStack(itemStack, gun);
        if(!validate(gun)) {
            return false;
        }
        return applyProperties(itemStack, gun);
    }

    @Nullable
    @Override
    public ItemStack createItemStack(@NonNull final Common common) {
        if(!(common instanceof Gun gun)) {
            return null;
        }

        ItemStack itemStack = Factory.COMMON.createItemStack(common);
        if(factory(itemStack, gun)) {
            return itemStack;
        }
        return null;
    }

    @Nullable
    @Override
    public Gun createCommon(@NonNull final ItemStack itemStack) {
        if(!(Factory.COMMON.createCommon(itemStack) instanceof Gun gun)) {
            return null;
        }
        Integer ammo = ItemStackUtil.getPDC(itemStack, Key.AMMO, PDT.INTEGER);
        if(ammo == null) {
            return null;
        }
        gun.setAmmo(ammo);
        return gun;
    }

    private boolean validate(@NonNull final Gun gun) {
        return gun.getAmmo() >= 0 &&
                gun.getMaxAmmo() >= 1 &&
                gun.getAmmo() <= gun.getMaxAmmo() &&
                gun.getBulletAmount() > 0 &&
                gun.getSpread() >= 0 &&
                gun.getVelocity() >= 0 &&
                gun.getBulletDamage() >= 0 &&
                ItemManager.getItemStackById(gun.getAmmoId()) != null &&
                ItemManager.getCommonById(gun.getAmmoId()) != null;
    }

    private boolean applyProperties(@NonNull final ItemStack itemStack,
                                    @NonNull final Gun gun) {
        ItemStackUtil.setMaxStackSize(itemStack, 1);
        ItemStackUtil.setDamage(itemStack, gun.getDamage());
        ItemStackUtil.setPDC(itemStack, Key.AMMO, PDT.INTEGER, gun.getAmmo());
        applyModel(itemStack, gun);
        applyScope(itemStack);
        return applyLore(itemStack, gun);
    }

    private boolean applyLore(@NonNull final ItemStack itemStack,
                              @NonNull final Gun gun) {
        Common common = ItemManager.getCommonById(gun.getAmmoId());
        if(common == null) {
            return false;
        }

        if (common.getDisplayName() != null) {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    gun.getLore(),
                    gun.getDisplayAmmoName(),
                    common.getDisplayName())
            ) {
                return false;
            }
        } else {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    gun.getLore(),
                    gun.getDisplayAmmoName(),
                    common.getCustomName())
            ) {
                return false;
            }
        }
        if(!TypeUtil.applyLoreDisplayAmmoAmount(
                itemStack,
                ItemStackUtil.getLore(itemStack),
                gun.getDisplayAmmoAmount(),
                gun.getAmmo(),
                gun.getMaxAmmo()
        )) {
            return false;
        }
        TypeUtil.applyLoreDisplayAmmoUnload(itemStack, ItemStackUtil.getLore(itemStack), gun.getDisplayAmmoUnload());
        return true;
    }

    private void applyModel(@NonNull final ItemStack itemStack,
                            @NonNull final Gun gun) {
        Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
        if(scope != null && scope == gun.getScope()) {
            ItemStackUtil.setItemModel(itemStack, gun.getItemModelScope());
        }
    }

    private void applyScope(@NonNull final ItemStack itemStack) {
        Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
        if(scope == null) {
            ItemStackUtil.setPDC(itemStack, Key.SCOPE, PDT.INTEGER, 0);
        }
    }

    @Override
    public boolean editItemStack(@NonNull final ItemStack itemStack,
                                 @NonNull final Common common) {
        if(!(common instanceof Gun gun)) {
            return false;
        }
        return factory(itemStack, gun);
    }
}
