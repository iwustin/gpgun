package iwust.gpgun.sound;

import iwust.gpgun.Gpgun;
import iwust.gpgun.sound.block.BlockHitSounds;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

import lombok.NonNull;

public final class SoundManager {
    public static void playSoundToPlayer(@NonNull final Player player,
                                         @Nullable final String sound) {
        if(sound != null) {
            player.playSound(
                    player.getLocation(),
                    sound,
                    1.0f,
                    1.0f
            );
        }
    }

    public static void playSoundAtLocation(@NonNull final Location location,
                                           @Nullable final String sound,
                                           int radius) {
        if(sound != null) {
            location.getWorld().playSound(
                    location,
                    sound,
                    (float) radius / 16,
                    1.0f
            );
        }
    }

    @Nullable
    public static BlockHitSounds getBlockHitSounds() {
        Sounds sounds = Gpgun.getConfiguration().getConfig().getSounds();
        if(sounds == null) {
            return null;
        }
        return sounds.getBlockHitSounds();
    }
}
