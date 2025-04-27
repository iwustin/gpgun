package iwust.gpgun.nms.inspector;

import com.mojang.datafixers.util.Pair;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import iwust.gpgun.item.type.TypeUtil;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.List;

import lombok.NonNull;

public class SetEquipmentInspector extends ChannelDuplexHandler {
    private static final List<org.bukkit.inventory.ItemStack> projectiles
            = List.of(org.bukkit.inventory.ItemStack.of(Material.ARROW));

    @Override
    public void write(@NonNull final ChannelHandlerContext context,
                      @NonNull final Object packet,
                      @NonNull final ChannelPromise promise) throws Exception {
        if (packet instanceof ClientboundSetEquipmentPacket setEquipmentPacket) {
            modifyPacket(setEquipmentPacket);
            super.write(context, setEquipmentPacket, promise);
            return;
        }
        super.write(context, packet, promise);
    }

    private void modifyPacket(@NonNull final ClientboundSetEquipmentPacket packet) {
        List<Pair<EquipmentSlot, ItemStack>> slots = packet.getSlots();
        Pair<EquipmentSlot, ItemStack> pair = slots.getFirst();

        org.bukkit.inventory.ItemStack bukkitItemStack = CraftItemStack.asBukkitCopy(pair.getSecond());

        if(!TypeUtil.isGunItemStack(bukkitItemStack)) {
            return;
        }

        org.bukkit.inventory.ItemStack finalBukkitItemStack = org.bukkit.inventory.ItemStack.of(Material.CROSSBOW);
        CrossbowMeta crossbowMeta = (CrossbowMeta) finalBukkitItemStack.getItemMeta();
        crossbowMeta.setChargedProjectiles(projectiles);
        finalBukkitItemStack.setItemMeta(crossbowMeta);

        ItemStackUtil.setItemModel(finalBukkitItemStack, ItemStackUtil.getItemModel(bukkitItemStack));
        ItemStack nmsItemStack = CraftItemStack.asNMSCopy(finalBukkitItemStack);

        slots.set(0, new Pair<>(EquipmentSlot.MAINHAND, nmsItemStack));
    }
}