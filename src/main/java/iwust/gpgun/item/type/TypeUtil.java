package iwust.gpgun.item.type;

import iwust.gpgun.Gpgun;
import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Key;
import iwust.gpgun.constant.PDT;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.item.type.magazinegun.MagazineGun;
import iwust.gpgun.sound.SoundManager;
import iwust.gpgun.util.ItemStackUtil;
import iwust.gpgun.util.MathUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class TypeUtil {
    public static boolean isTick(@NonNull final ItemStack itemStack,
                                 final int delta) {
        Integer lastTick = ItemStackUtil.getPDC(itemStack, Key.TICK, PDT.INTEGER);
        if(lastTick == null) {
            return true;
        }

        Common common = ItemStackUtil.getType(itemStack, Common.class);
        if(common == null) {
            return true;
        }

        if(Bukkit.getCurrentTick() - lastTick < 0) {
            return true;
        }
        return Bukkit.getCurrentTick() - lastTick > delta;
    }

    public static void updateTick(@NonNull final ItemStack itemStack) {
        ItemStackUtil.setPDC(itemStack, Key.TICK, PDT.INTEGER, Bukkit.getCurrentTick());
    }

    public static boolean isGunItemStack(@NonNull final ItemStack itemStack) {
        Gun gun = ItemStackUtil.getType(itemStack, Gun.class);
        MagazineGun magazineGun = ItemStackUtil.getType(itemStack, MagazineGun.class);
        return gun != null || magazineGun != null;
    }

    public static void shot(@NonNull final Player player,
                            @Nullable final String shotSound,
                            final int bulletAmount,
                            final Integer scope,
                            float spread,
                            final float bulletDamage,
                            float velocity) {
        SoundManager.playSoundAtLocation(player.getLocation(), shotSound, 48);

        boolean gravity;
        if(velocity > 4) {
            gravity = false;
            velocity = 4;
        } else {
            gravity = true;
        }

        if(scope != null) {
            spread /= scope + 1;
        }

        for(int i = 0;i < bulletAmount;i++) {
            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();

            Vector spreadDirection = MathUtil.addSpread(direction, spread);

            Location spawnLocation = eyeLocation.clone().add(direction.clone().multiply(0.6));

            SpectralArrow arrow = player.getWorld().spawnArrow(
                    spawnLocation,
                    spreadDirection,
                    velocity,
                    0,
                    SpectralArrow.class);
            arrow.setGlowingTicks(0);
            arrow.setCritical(false);
            arrow.setShooter(player);
            arrow.setDamage(bulletDamage);
            arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            arrow.setGravity(gravity);

            Bukkit.getScheduler().runTaskLater(
                    Gpgun.getInstance(),
                    arrow::remove,
                    100L
            );
        }
    }

    public static boolean applyLoreDisplayName(@NonNull final ItemStack itemStack,
                                               @Nullable final List<String> lore,
                                               @Nullable final String displayAmmoName,
                                               @Nullable final String ammoName) {
        if(displayAmmoName == null || ammoName == null) {
            return true;
        }

        List<String> newLore;
        if(lore != null) {
            newLore = new ArrayList<>(lore);
        } else {
            newLore = new ArrayList<>();
        }

        try {
            newLore.add(String.format(displayAmmoName, ammoName));
            ItemStackUtil.setLore(itemStack, newLore);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean applyLoreDisplayAmmoAmount(@NonNull final ItemStack itemStack,
                                                     @Nullable final List<String> lore,
                                                     @Nullable final String displayAmmoAmount,
                                                     final int ammo,
                                                     final int maxAmmo) {
        if(displayAmmoAmount == null) {
            return true;
        }

        List<String> newLore;
        if(lore != null) {
            newLore = new ArrayList<>(lore);
        } else {
            newLore = new ArrayList<>();
        }

        try {
            newLore.add(String.format(displayAmmoAmount, ammo, maxAmmo));
            ItemStackUtil.setLore(itemStack, newLore);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static void applyLoreDisplayAmmoUnload(@NonNull final ItemStack itemStack,
                                                  @Nullable final List<String> lore,
                                                  @Nullable final String displayAmmoUnload) {
        if(displayAmmoUnload == null) {
            return;
        }

        List<String> newLore;
        if(lore != null) {
            newLore = new ArrayList<>(lore);
        } else {
            newLore = new ArrayList<>();
        }

        newLore.add("");
        newLore.add(displayAmmoUnload);

        ItemStackUtil.setLore(itemStack, newLore);
    }

    public static boolean applyLoreDisplayAmmoState(@NonNull final ItemStack itemStack,
                                                    @Nullable final List<String> lore,
                                                    @Nullable final String displayAmmoState,
                                                    @Nullable final String ammoState) {
        if(displayAmmoState == null || ammoState == null) {
            return true;
        }

        List<String> newLore;
        if(lore != null) {
            newLore = new ArrayList<>(lore);
        } else {
            newLore = new ArrayList<>();
        }

        try {
            newLore.add(String.format(displayAmmoState, ammoState));
            ItemStackUtil.setLore(itemStack, newLore);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static void scheduleRepeated(@NonNull final Runnable runnable) {
        scheduleRepeated(runnable, 1, 5);
    }

    public static void scheduleRepeated(@NonNull final Runnable runnable,
                                        int delay,
                                        int finish) {
        for (int tick = delay; tick < finish; tick++) {
            Bukkit.getScheduler().runTaskLater(
                    Gpgun.getInstance(),
                    runnable,
                    tick
            );
        }
    }

    public static void updateHeldItem(@NonNull final Player player,
                                      @NonNull final ItemStack itemStack) {
        PlayerInventory inventory = player.getInventory();
        inventory.setItem(inventory.getHeldItemSlot(), itemStack);
    }

    /* return -1 if not gun or magazine gun */
    public static int getScope(@NonNull final ItemStack itemStack) {
        Gun gun = Factory.GUN.createCommon(itemStack);
        if(gun != null) {
            return gun.getScope();
        }
        MagazineGun magazineGun = Factory.MAGAZINE_GUN.createCommon(itemStack);
        if(magazineGun != null) {
            return magazineGun.getScope();
        }
        return -1;
    }
}
