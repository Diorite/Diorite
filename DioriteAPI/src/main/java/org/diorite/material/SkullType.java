package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class SkullType extends ASimpleEnum<SkullType>
{
    static
    {
        init(SkullType.class, 5);
    }

    public static final SkullType SKELETON        = new SkullType("SKELETON", 0);
    public static final SkullType WITHER_SKELETON = new SkullType("WITHER_SKELETON", 1);
    public static final SkullType ZOMBIE          = new SkullType("ZOMBIE", 2);
    public static final SkullType PLAYER          = new SkullType("PLAYER", 3);
    public static final SkullType CREEPER         = new SkullType("CREEPER", 4);

    private final short itemTypeID;

    public SkullType(final String enumName, final int enumId, final short itemTypeID)
    {
        super(enumName, enumId);
        this.itemTypeID = itemTypeID;
    }

    public SkullType(final String enumName, final int itemTypeID)
    {
        super(enumName);
        this.itemTypeID = (short) itemTypeID;
    }

    public short getItemTypeID()
    {
        return this.itemTypeID;
    }

    /**
     * Register new {@link SkullType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final SkullType element)
    {
        ASimpleEnum.register(SkullType.class, element);
    }

    /**
     * Get one of {@link SkullType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static SkullType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(SkullType.class, ordinal);
    }

    /**
     * Get one of SkullType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static SkullType getByEnumName(final String name)
    {
        return getByEnumName(SkullType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static SkullType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(SkullType.class);
        return (SkullType[]) map.values(new SkullType[map.size()]);
    }

    static
    {
        SkullType.register(SKELETON);
        SkullType.register(WITHER_SKELETON);
        SkullType.register(ZOMBIE);
        SkullType.register(PLAYER);
        SkullType.register(CREEPER);
    }
}