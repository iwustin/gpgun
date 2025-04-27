package iwust.gpgun.item.type.magazinegun;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import lombok.NonNull;

public final class MagazineGunFactory implements ItemFactory {
    private boolean factory(@NonNull final ItemStack itemStack,
                            @NonNull final MagazineGun magazineGun) {
        Factory.COMMON.editItemStack(itemStack, magazineGun);
        if(!validate(magazineGun)) {
            return false;
        }
        return applyProperties(itemStack, magazineGun);
    }

    @Nullable
    @Override
    public ItemStack createItemStack(@NonNull final Common common) {
        if(!(common instanceof MagazineGun magazineGun)) {
            return null;
        }
        ItemStack itemStack = Factory.COMMON.createItemStack(common);
        if(factory(itemStack, magazineGun)) {
            return itemStack;
        }
        return null;
    }

    @Nullable
    @Override
    public MagazineGun createCommon(@NonNull final ItemStack itemStack) {
        if(!(Factory.COMMON.createCommon(itemStack) instanceof MagazineGun magazineGun)) {
            return null;
        }
        Integer ammo = ItemStackUtil.getPDC(itemStack, Key.AMMO, PDT.INTEGER);
        if(ammo == null) {
            return null;
        }
        magazineGun.setAmmo(ammo);
        return magazineGun;
    }

    private boolean validate(@NonNull final MagazineGun magazineGun) {
        return magazineGun.getAmmo() >= -1 &&
                magazineGun.getMaxAmmo() >= 1 &&
                magazineGun.getAmmo() <= magazineGun.getMaxAmmo() &&
                magazineGun.getBulletAmount() > 0 &&
                magazineGun.getSpread() >= 0 &&
                magazineGun.getVelocity() >= 0 &&
                magazineGun.getBulletDamage() >= 0 &&
                ItemManager.getItemStackById(magazineGun.getAmmoId()) != null &&
                ItemManager.getCommonById(magazineGun.getAmmoId()) != null;
    }

    private boolean applyProperties(@NonNull final ItemStack itemStack,
                                    @NonNull final MagazineGun magazineGun) {
        ItemStackUtil.setMaxStackSize(itemStack, 1);
        ItemStackUtil.setPDC(itemStack, Key.AMMO, PDT.INTEGER, magazineGun.getAmmo());
        applyModel(itemStack, magazineGun);
        applyScope(itemStack);
        return applyLore(itemStack, magazineGun);
    }

    private boolean applyLore(@NonNull final ItemStack itemStack,
                              @NonNull final MagazineGun magazineGun) {
        Common common = ItemManager.getCommonById(magazineGun.getAmmoId());
        if(!(common instanceof Magazine magazine)) {
            return false;
        }

        if (magazine.getDisplayName() != null) {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    magazineGun.getLore(),
                    magazineGun.getDisplayAmmoName(),
                    magazine.getDisplayName())
            ) {
                return false;
            }
        } else {
            if(!TypeUtil.applyLoreDisplayName(
                    itemStack,
                    magazineGun.getLore(),
                    magazineGun.getDisplayAmmoName(),
                    common.getCustomName())
            ) {
                return false;
            }
        }
        if(magazineGun.getAmmo() > -1) {
            if(!TypeUtil.applyLoreDisplayAmmoState(
                    itemStack,
                    ItemStackUtil.getLore(itemStack),
                    magazineGun.getDisplayAmmoState(),
                    magazineGun.getDisplayAmmoStateYes()
            )) {
                return false;
            }
        } else {
            if(!TypeUtil.applyLoreDisplayAmmoState(
                    itemStack,
                    ItemStackUtil.getLore(itemStack),
                    magazineGun.getDisplayAmmoState(),
                    magazineGun.getDisplayAmmoStateNo()
            )) {
                return false;
            }
        }
        TypeUtil.applyLoreDisplayAmmoUnload(itemStack, ItemStackUtil.getLore(itemStack), magazineGun.getDisplayAmmoUnload());
        return true;
    }

    private void applyModel(@NonNull final ItemStack itemStack,
                            @NonNull final MagazineGun magazineGun) {
        Integer scope = ItemStackUtil.getPDC(itemStack, Key.SCOPE, PDT.INTEGER);
        if(scope != null) {
            if (scope != magazineGun.getScope() && magazineGun.getAmmo() > -1) {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getItemModel());
            }
            if (scope == magazineGun.getScope() && magazineGun.getAmmo() > -1) {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getItemModelScope());
            }
            if (scope == magazineGun.getScope() && magazineGun.getAmmo() < 0) {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getWithoutItemModelScope());
            }
            if (scope != magazineGun.getScope() && magazineGun.getAmmo() < 0) {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getWithoutItemModel());
            }
        } else {
            if(magazineGun.getAmmo() > -1) {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getItemModel());
            } else {
                ItemStackUtil.setItemModel(itemStack, magazineGun.getWithoutItemModel());
            }
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
        if(!(common instanceof MagazineGun magazineGun)) {
            return false;
        }
        return factory(itemStack, magazineGun);
    }
}
