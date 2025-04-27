package iwust.gpgun.nms.inspector;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.world.item.ItemStack;

import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.util.ItemStackUtil;

import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack;

import lombok.NonNull;

import javax.annotation.Nullable;

public class ContainerSetSlotInspector extends ChannelDuplexHandler {
    @Override
    public void write(@NonNull final ChannelHandlerContext context,
                      @NonNull final Object packet,
                      @NonNull final ChannelPromise promise) throws Exception {
        if (packet instanceof ClientboundContainerSetSlotPacket containerSetSlotPacket) {
            ClientboundContainerSetSlotPacket newPacket = createPacket(containerSetSlotPacket);
            if(newPacket != null) {
                super.write(context, newPacket, promise);
                return;
            }
        }
        super.write(context, packet, promise);
    }

    @Nullable
    private ClientboundContainerSetSlotPacket createPacket(@NonNull final ClientboundContainerSetSlotPacket packet) {
        if(packet.getContainerId() != 0) {
            return null;
        }

        ItemStack nmsItemStack = packet.getItem();
        if(nmsItemStack == null) {
            return null;
        }

        org.bukkit.inventory.ItemStack bukkitItemStack = CraftItemStack.asBukkitCopy(nmsItemStack).clone();

        ItemStackUtil.removePDC(bukkitItemStack, Key.EQUIP, PDT.BOOLEAN);

        ItemStack newNmsItemStack = CraftItemStack.asNMSCopy(bukkitItemStack);

        return new ClientboundContainerSetSlotPacket(
                0,
                packet.getStateId(),
                packet.getSlot(),
                newNmsItemStack
        );
    }
}
