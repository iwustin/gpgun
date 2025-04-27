package iwust.gpgun.nms;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.InspectorId;
import iwust.gpgun.constant.Pipeline;
import iwust.gpgun.nms.inspector.SetEquipmentInspector;
import iwust.gpgun.nms.inspector.ContainerSetSlotInspector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

@GpgunListener
public final class PlayerChannelHandler implements Listener {
    @EventHandler
    public void onJoin(@NonNull final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Channel channel = NMSUtil.getChanel(player);
        if(channel == null) {
            return;
        }

        HashMap<String, ChannelDuplexHandler> handlerHashMap = new HashMap<>();
        handlerHashMap.put(InspectorId.EQUIPMENT, new SetEquipmentInspector());
        handlerHashMap.put(InspectorId.SET_SLOT, new ContainerSetSlotInspector());

        for(Map.Entry<String, ChannelDuplexHandler> entry : handlerHashMap.entrySet()) {
            channel.pipeline().addBefore(
                    Pipeline.PACKET_HANDLER,
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }
}
