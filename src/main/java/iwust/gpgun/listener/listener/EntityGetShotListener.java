package iwust.gpgun.listener.listener;

import iwust.gpgun.annotation.GpgunListener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import lombok.NonNull;

@GpgunListener
public final class EntityGetShotListener implements Listener {
    @EventHandler
    public void getShot(@NonNull final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof SpectralArrow spectralArrow &&
                event.getEntity() instanceof LivingEntity livingEntity) {
            event.setCancelled(true);
            spectralArrow.remove();
            double damage = spectralArrow.getDamage();
            double health = livingEntity.getHealth();
            livingEntity.damage(0.01);
            if(health - damage >= 0) {
                livingEntity.setHealth(health - damage);
            } else {
                livingEntity.setHealth(0);
            }
        }
    }
}
