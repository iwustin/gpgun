package iwust.gpgun.item.type.gun;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Property;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.type.common.Common;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder(toBuilder = true)
@Jacksonized
@Setter
@Getter
public final class Gun extends Common {
    @NonNull
    @JsonIgnore
    private final ItemFactory factory = Factory.GUN;

    @NonNull
    @JsonProperty(Property.AMMO_ID)
    private String ammoId;

    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_NAME)
    private String displayAmmoName;
    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_AMOUNT)
    private String displayAmmoAmount;
    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_UNLOAD)
    private String displayAmmoUnload;
    @Nullable
    @JsonProperty(Property.SHOT_SOUND)
    private String shotSound;
    @Nullable
    @JsonProperty(Property.LOAD_SOUND)
    private String loadSound;
    @Nullable
    @JsonProperty(Property.UNLOAD_SOUND)
    private String unloadSound;
    @Nullable
    @JsonProperty(Property.ITEM_MODEL_SCOPE)
    private String itemModelScope;

    @JsonProperty(Property.MAX_AMMO)
    private int maxAmmo;
    @JsonProperty(Property.AMMO)
    private int ammo;
    @JsonProperty(Property.BULLET_AMOUNT)
    private int bulletAmount;
    @JsonProperty(Property.SCOPE)
    private int scope;
    @JsonProperty(Property.SPREAD)
    private float spread;
    @JsonProperty(Property.VELOCITY)
    private float velocity;
    @JsonProperty(Property.BULLET_DAMAGE)
    private float bulletDamage;
    @JsonProperty(Property.SHOT_DELAY)
    private float shotDelay;
}