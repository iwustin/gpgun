package iwust.gpgun.sound;

import iwust.gpgun.constant.Property;
import iwust.gpgun.sound.block.BlockHitSounds;

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
public final class Sounds {
    @Nullable
    @JsonProperty(Property.BLOCK_HIT)
    private BlockHitSounds blockHitSounds;

    @Nullable
    @JsonProperty(Property.SCOPE)
    private String scope;
}