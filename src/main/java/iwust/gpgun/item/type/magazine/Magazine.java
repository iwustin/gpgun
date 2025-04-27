package iwust.gpgun.item.type.magazine;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Property;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.type.common.Common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@SuperBuilder(toBuilder = true)
@Jacksonized
@Getter
@Setter
public final class Magazine extends Common {
    @NonNull
    @JsonIgnore
    private final ItemFactory factory = Factory.MAGAZINE;

    @NonNull
    @JsonProperty(Property.AMMO_ID)
    private String ammoId;

    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_AMOUNT)
    private String displayAmmoAmount;
    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_NAME)
    private String displayAmmoName;
    @Nullable
    @JsonProperty(Property.DISPLAY_AMMO_UNLOAD)
    private String displayAmmoUnload;
    @Nullable
    @JsonProperty(Property.LOAD_SOUND)
    private String loadSound;
    @Nullable
    @JsonProperty(Property.UNLOAD_SOUND)
    private String unloadSound;

    @JsonProperty(Property.MAX_AMMO)
    private int maxAmmo;
    @JsonProperty(Property.AMMO)
    private int ammo;
}