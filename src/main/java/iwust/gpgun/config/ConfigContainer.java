package iwust.gpgun.config;

import iwust.gpgun.constant.Property;
import iwust.gpgun.item.type.common.Common;
import iwust.gpgun.sound.Sounds;

import javax.annotation.Nullable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public final class ConfigContainer {
    @NonNull
    @JsonProperty(Property.ITEMS)
    private List<Common> items;

    @Nullable
    @JsonProperty(Property.SOUNDS)
    private Sounds sounds;
}
