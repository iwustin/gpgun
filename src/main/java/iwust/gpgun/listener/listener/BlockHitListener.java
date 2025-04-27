package iwust.gpgun.listener.listener;

import iwust.gpgun.annotation.GpgunListener;
import iwust.gpgun.constant.MaterialList;
import iwust.gpgun.sound.SoundManager;
import iwust.gpgun.sound.block.BlockHitSounds;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import lombok.NonNull;

@GpgunListener
public final class BlockHitListener implements Listener {
    @EventHandler
    public void onHit(@NonNull final ProjectileHitEvent event) {
        Block block = event.getHitBlock();
        if(block == null) {
            return;
        }
        if(!(event.getEntity() instanceof SpectralArrow)) {
            return;
        }

        BlockHitSounds blockHitSounds = SoundManager.getBlockHitSounds();
        if(blockHitSounds == null) {
            return;
        }
        Material material = block.getType();

        if(MaterialList.METAL.contains(material)) {
            playSoundFrom(block, blockHitSounds.getMetal());
        } else if(MaterialList.GLASS.contains(material)) {
            playSoundFrom(block, blockHitSounds.getGlass());
        } else if(MaterialList.WOOD.contains(material)) {
            playSoundFrom(block, blockHitSounds.getWood());
        } else if(MaterialList.STONE.contains(material)) {
            playSoundFrom(block, blockHitSounds.getStone());
        } else if(MaterialList.WATER.contains(material)) {
            playSoundFrom(block, blockHitSounds.getWater());
        } else if(MaterialList.SNOW.contains(material)) {
            playSoundFrom(block, blockHitSounds.getSnow());
        } else if(MaterialList.DIRT.contains(material)) {
            playSoundFrom(block, blockHitSounds.getDirt());
        } else if(MaterialList.FOLIAGE.contains(material)) {
            playSoundFrom(block, blockHitSounds.getFoliage());
        } else if(MaterialList.MASONRY.contains(material)) {
            playSoundFrom(block, blockHitSounds.getMasonry());
        } else {
            playSoundFrom(block, blockHitSounds.getCanvas());
        }
    }

    private void playSoundFrom(@NonNull final Block block,
                               final String sound) {
        SoundManager.playSoundAtLocation(block.getLocation(), sound,16);
    }
}
