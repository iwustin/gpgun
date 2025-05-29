package iwust.gpgun.item.type.gui;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

import lombok.NonNull;

public final class GuiFactory implements ItemFactory {
    private boolean factory(@NonNull final ItemStack itemStack,
                            @NonNull final Gui gui) {
        Factory.COMMON.editItemStack(itemStack, gui);
        ItemStackUtil.setPDC(itemStack, Key.GUI_ID, PDT.STRING, gui.getGuiId());
        return true;
    }

    @Nullable
    @Override
    public ItemStack createItemStack(@NonNull final Common common) {
        if(!(common instanceof Gui gui)) {
            return null;
        }

        ItemStack itemStack = Factory.COMMON.createItemStack(common);
        factory(itemStack, gui);
        return itemStack;
    }

    @Nullable
    @Override
    public Gui createCommon(@NonNull final ItemStack itemStack) {
        if(!(Factory.COMMON.createCommon(itemStack) instanceof Gui gui)) {
            return null;
        }
        return gui;
    }

    @Override
    public boolean editItemStack(@NonNull final ItemStack itemStack,
                                 @NonNull final Common common) {
        if(!(common instanceof Gui gui)) {
            return false;
        }
        return factory(itemStack, gui);
    }
}
