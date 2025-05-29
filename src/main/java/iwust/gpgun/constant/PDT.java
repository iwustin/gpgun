package iwust.gpgun.constant;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import lombok.NonNull;

/**
 *  Shorten variant of PersistentDataType
 *  */
public final class PDT {
    @NonNull
    public static final PersistentDataType<Byte, Byte> BYTE = PersistentDataType.BYTE;
    @NonNull
    public static final PersistentDataType<byte[], byte[]> BYTE_ARRAY = PersistentDataType.BYTE_ARRAY;
    @NonNull
    public static final PersistentDataType<Short, Short> SHORT = PersistentDataType.SHORT;
    @NonNull
    public static final PersistentDataType<Integer, Integer> INTEGER = PersistentDataType.INTEGER;
    @NonNull
    public static final PersistentDataType<int[], int[]> INTEGER_ARRAY = PersistentDataType.INTEGER_ARRAY;
    @NonNull
    public static final PersistentDataType<Long, Long> LONG = PersistentDataType.LONG;
    @NonNull
    public static final PersistentDataType<long[], long[]> LONG_ARRAY = PersistentDataType.LONG_ARRAY;
    @NonNull
    public static final PersistentDataType<Float, Float> FLOAT = PersistentDataType.FLOAT;
    @NonNull
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    @NonNull
    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    @NonNull
    public static final PersistentDataType<Byte, Boolean> BOOLEAN = PersistentDataType.BOOLEAN;
    @NonNull
    public static final PersistentDataType<PersistentDataContainer, PersistentDataContainer> CONTAINER = PersistentDataType.TAG_CONTAINER;
}