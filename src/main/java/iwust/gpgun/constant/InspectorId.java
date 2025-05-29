package iwust.gpgun.constant;

import lombok.NonNull;

public final class InspectorId {
    @NonNull
    private static final String INSPECTOR = "_inspector";

    @NonNull
    public static final String EQUIPMENT = "equipment" + INSPECTOR;
    @NonNull
    public static final String SET_SLOT = "set_slot" + INSPECTOR;
}
