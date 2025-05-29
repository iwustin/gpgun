package iwust.gpgun.command.command;

import iwust.gpgun.annotation.GpgunCommand;
import iwust.gpgun.constant.Message;
import iwust.gpgun.gui.menu.MenuGui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

import lombok.NonNull;

@GpgunCommand(name = "menu")
public final class MenuCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String @NonNull [] args) {
        if(sender.isOp()) {
            MenuGui.open((Player) sender);
        } else {
            sender.sendMessage(Message.NO_OP);
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender,
                                      @NonNull Command command,
                                      @NonNull String alias,
                                      @NonNull String @NonNull [] args) {
        return null;
    }
}
