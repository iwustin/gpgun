package iwust.gpgun.util;

import org.bukkit.util.Vector;

import java.util.Random;

import lombok.NonNull;

public final class MathUtil {
    /* AI work */
    @NonNull
    public static Vector addSpread(@NonNull final Vector direction,
                                   final double spreadDegrees) {
        Vector result = direction.clone();

        float spreadRadians = (float) Math.toRadians(spreadDegrees);

        float deltaYaw = (new Random().nextFloat() * 2 - 1) * spreadRadians;
        float deltaPitch = (new Random().nextFloat()  * 2 - 1) * spreadRadians;

        float yaw = (float) Math.atan2(result.getZ(), result.getX()) + deltaYaw;
        float pitch = (float) Math.asin(result.getY()) + deltaPitch;

        float cosPitch = (float) Math.cos(pitch);
        result.setX(cosPitch * Math.cos(yaw));
        result.setZ(cosPitch * Math.sin(yaw));
        result.setY(Math.sin(pitch));

        return result.normalize();
    }
}
