package iwust.gpgun.gui.menu;

import iwust.gpgun.constant.Item;
import iwust.gpgun.constant.Message;
import iwust.gpgun.constant.Title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import lombok.NonNull;

public final class MenuGui {
    public static void open(@NonNull final Player player) {
        Inventory inventory = Bukkit.createInventory(player, 9, Title.MENU);

        for(ItemStack itemStack : getButtonList()) {
            inventory.addItem(itemStack);
        }

        player.openInventory(inventory);
        player.sendMessage(Message.OPEN_MENU);
    }

    @NonNull
    private static List<ItemStack> getButtonList() {
        return List.of(
                Item.ITEMS_GUI
        );
    }
}
