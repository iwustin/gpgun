package iwust.gpgun.sound.block;

import iwust.gpgun.constant.Property;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Setter
@Getter
public final class BlockHitSounds {
    @Nullable
    @JsonProperty(Property.CANVAS)
    private String canvas;
    @Nullable
    @JsonProperty(Property.GLASS)
    private String glass;
    @Nullable
    @JsonProperty(Property.METAL)
    private String metal;
    @Nullable
    @JsonProperty(Property.STONE)
    private String stone;
    @Nullable
    @JsonProperty(Property.WOOD)
    private String wood;
    @Nullable
    @JsonProperty(Property.WATER)
    private String water;
    @Nullable
    @JsonProperty(Property.DIRT)
    private String dirt;
    @Nullable
    @JsonProperty(Property.FOLIAGE)
    private String foliage;
    @Nullable
    @JsonProperty(Property.MASONRY)
    private String masonry;
    @Nullable
    @JsonProperty(Property.SNOW)
    private String snow;
}
