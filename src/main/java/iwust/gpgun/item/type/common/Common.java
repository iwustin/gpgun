package iwust.gpgun.item.type.common;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.constant.Property;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.type.gun.Gun;
import iwust.gpgun.item.type.magazine.Magazine;
import iwust.gpgun.item.type.magazinegun.MagazineGun;

import org.bukkit.Material;

import javax.annotation.Nullable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 * Super class of all item types
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = Property.TYPE
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Common.class, name = Property.COMMON),
        @JsonSubTypes.Type(value = Gun.class, name = Property.GUN),
        @JsonSubTypes.Type(value = MagazineGun.class, name = Property.MAGAZINE_GUN),
        @JsonSubTypes.Type(value = Magazine.class, name = Property.MAGAZINE)
})
@SuperBuilder(toBuilder = true)
@Jacksonized
@Setter
@Getter
public class Common {
    @NonNull
    @JsonIgnore
    private final ItemFactory factory = Factory.COMMON;

    @NonNull
    @JsonProperty(Property.ID)
    private String id;
    @NonNull
    @JsonProperty(Property.MATERIAL)
    private Material material;

    @Nullable
    @JsonProperty(Property.CUSTOM_NAME)
    private String customName;
    @Nullable
    @JsonProperty(Property.LORE)
    private List<String> lore;
    @Nullable
    @JsonProperty(Property.ITEM_MODEL)
    private String itemModel;
    @Nullable
    @JsonProperty(Property.DISPLAY_NAME)
    private String displayName;

    @JsonProperty(Property.MAX_STACK_SIZE)
    private int maxStackSize;
    @JsonProperty(Property.MAX_DAMAGE)
    private int maxDamage;
    @JsonProperty(Property.DAMAGE)
    private int damage;
    @JsonProperty(Property.UNBREAKABLE)
    private boolean unbreakable;
    @JsonProperty(Property.USE_DELAY)
    private float useDelay;
    @JsonProperty(Property.EQUIP_DELAY)
    private float equipDelay;
}