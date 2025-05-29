package iwust.gpgun.command.command;

import iwust.gpgun.annotation.GpgunCommand;
import iwust.gpgun.constant.Message;
import iwust.gpgun.gui.items.ItemsGui;
import iwust.gpgun.item.ItemManager;
import iwust.gpgun.util.PlayerUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

import lombok.NonNull;

@GpgunCommand(name = "items")
public final class ItemsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String @NonNull [] args) {
        if(!sender.isOp()) {
            sender.sendMessage(Message.NO_OP);
            return true;
        }

        if(args.length == 0) {
            ItemsGui.open((Player) sender);
            return true;
        }
        if(args.length == 2) {
            if(!args[0].equals("give")) {
                return true;
            }

            ItemStack itemStack = ItemManager.getItemStackById(args[1]);
            if(itemStack != null) {
                PlayerUtil.addItemStack((Player) sender, itemStack);
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull Command command,
                                      @NonNull String alias,
                                      @NonNull String @NonNull [] args) {
        if(args.length == 1) {
            return List.of("give");
        } else if (args.length == 2) {
            return ItemManager.getIdList();
        }
        return null;
    }
}
