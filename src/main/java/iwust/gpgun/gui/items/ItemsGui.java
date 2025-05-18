package iwust.gpgun.gui.items;

import iwust.gpgun.constant.*;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import lombok.NonNull;

public final class ItemsGui {
    public static void open(@NonNull final Player player) {
        Inventory inventory = Bukkit.createInventory(player, 36, Title.ITEMS);

        for(ItemStack itemStack : getButtonList()) {
            inventory.addItem(itemStack);
        }

        player.openInventory(inventory);
        player.sendMessage(Message.OPEN_ITEMS);
    }

    @NonNull
    private static List<ItemStack> getButtonList() {
        List<ItemStack> itemStackList = ItemManager.getItemStackList();
        itemStackList.forEach(ItemsGui::setItemStackToButton);
        return itemStackList;
    }

    private static void setItemStackToButton(@NonNull final ItemStack itemStack) {
        ItemStackUtil.setPDC(itemStack, Key.GUI_ID, PDT.STRING, GuiId.ITEM_BUTTON);
    }
}
