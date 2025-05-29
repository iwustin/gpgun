package iwust.gpgun.nms;

import io.netty.channel.Channel;

import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

import lombok.NonNull;

import javax.annotation.Nullable;

public final class NMSUtil {
    @Nullable
    public static Channel getChanel(@NonNull final Player player) {
        if(!(player instanceof CraftPlayer craftPlayer)) {
            return null;
        }
        ServerGamePacketListenerImpl listener = craftPlayer.getHandle().connection;

        Field field = getField(listener);
        if(field == null) {
            return null;
        }

        Connection connection;
        try {
            connection = (Connection) field.get(listener);
        } catch (IllegalAccessException exception) {
            return null;
        }

        return connection.channel;
    }

    @Nullable
    private static Field getField(@NonNull final ServerGamePacketListenerImpl listener) {
        for(Field field : listener.getClass().getFields()) {
            if(field.getType().equals(Connection.class)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }
}
