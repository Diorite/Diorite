package org.diorite.nbt;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings({"MagicNumber", "rawtypes"})
public enum NbtTagType
{
    END(0, null, () -> null),
    BYTE(1, NbtTagByte.class, NbtTagByte::new),
    SHORT(2, NbtTagShort.class, NbtTagShort::new),
    INTEGER(3, NbtTagInt.class, NbtTagInt::new),
    LONG(4, NbtTagLong.class, NbtTagLong::new),
    FLOAT(5, NbtTagFloat.class, NbtTagFloat::new),
    DOUBLE(6, NbtTagDouble.class, NbtTagDouble::new),
    BYTE_ARRAY(7, NbtTagByteArray.class, NbtTagString::new),
    STRING(8, NbtTagString.class, NbtTagString::new),
    LIST(9, NbtTagList.class, NbtTagList::new),
    COMPOUND(10, NbtTagCompound.class, NbtTagCompound::new),
    INTEGER_ARRAY(11, NbtTagIntArray.class, NbtTagIntArray::new);
    private static final Map<Byte, NbtTagType>                            typeMap;
    private static final Map<Class<? extends NbtAbstractTag>, NbtTagType> classMap;

    static
    {
        {
            final ImmutableMap.Builder<Byte, NbtTagType> mapBuilder = new ImmutableMap.Builder<>();
            for (final NbtTagType type : values())
            {
                mapBuilder.put(type.typeID, type);
            }
            typeMap = mapBuilder.build();
        }
        {
            final ImmutableMap.Builder<Class<? extends NbtAbstractTag>, NbtTagType> mapBuilder = new ImmutableMap.Builder<>();
            for (final NbtTagType type : values())
            {
                mapBuilder.put(type.typeClass, type);
            }
            classMap = mapBuilder.build();
        }
    }

    private final byte                            typeID;
    private final Class<? extends NbtAbstractTag> typeClass;
    private final Supplier<NbtAbstractTag<?>>     getInstance;

    private NbtTagType(final int typeID, final Class<? extends NbtAbstractTag> type, final Supplier<NbtAbstractTag<?>> getInstance)
    {
        this.typeID = ((byte) typeID);
        this.typeClass = type;
        this.getInstance = getInstance;
    }

    public byte getTypeID()
    {
        return this.typeID;
    }

    public Class<? extends NbtAbstractTag> getTypeClass()
    {
        return this.typeClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag<?>> T newInstance()
    {
        return (T) this.getInstance.get();
    }

    public static Map<Byte, NbtTagType> getTypeMap()
    {
        return typeMap;
    }

    public static Map<Class<? extends NbtAbstractTag>, NbtTagType> getClassMap()
    {
        return classMap;
    }

    public static NbtTagType valueOf(final byte typeID)
    {
        return typeMap.get(typeID);
    }

    public static NbtTagType valueOf(final Class<?> clazz)
    {
        return classMap.get(clazz);
    }
}