package iwust.gpgun.item.type.gui;

import iwust.gpgun.constant.Factory;
import iwust.gpgun.item.ItemFactory;
import iwust.gpgun.item.type.common.Common;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Setter
@Getter
public final class Gui extends Common {
    @NonNull
    private final ItemFactory factory = Factory.GUI;

    @NonNull
    private String GuiId;
}
