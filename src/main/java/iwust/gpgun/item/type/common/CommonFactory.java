package iwust.gpgun.item.type.common;

import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import lombok.NonNull;

public final class CommonFactory implements ItemFactory {
    private boolean factory(@NonNull final ItemStack itemStack,
                            @NonNull final Common common) {
        applyProperties(itemStack, common);
        return true;
    }

    @NonNull
    @Override
    public ItemStack createItemStack(@NonNull final Common common) {
        ItemStack itemStack = ItemStack.of(common.getMaterial());
        factory(itemStack, common);
        return itemStack;
    }

    private void applyProperties(@NonNull final ItemStack itemStack,
                                 @NonNull final Common common) {
        ItemStackUtil.unsetExtraData(itemStack);
        ItemStackUtil.setCustomName(itemStack,  common.getCustomName());
        ItemStackUtil.setLore(itemStack,  common.getLore());
        ItemStackUtil.setMaxStackSize(itemStack,  common.getMaxStackSize());
        ItemStackUtil.setMaxDamage(itemStack,  common.getMaxDamage());
        ItemStackUtil.setDamage(itemStack,  common.getDamage());
        ItemStackUtil.setUnbreakable(itemStack,  common.isUnbreakable());
        ItemStackUtil.setItemModel(itemStack,  common.getItemModel());

        ItemStackUtil.setPDC(itemStack, Key.ID, PDT.STRING, common.getId());
        ItemStackUtil.setPDC(itemStack, Key.EQUIP, PDT.BOOLEAN, false);
    }

    @Nullable
    @Override
    public Common createCommon(@NonNull final ItemStack itemStack) {
        Common common = ItemStackUtil.getType(itemStack, Common.class);
        if(common == null) {
            return null;
        }
        common.setDamage(ItemStackUtil.getDamage(itemStack));
        return common;
    }

    @Override
    public boolean editItemStack(@NonNull final ItemStack itemStack,
                                 @NonNull final Common common) {
        return factory(itemStack, common);
    }
}
