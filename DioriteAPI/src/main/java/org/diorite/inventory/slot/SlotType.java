package org.diorite.inventory.slot;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

public class SlotType extends ASimpleEnum<SlotType>
{
    static
    {
        init(SlotType.class, 7);
    }

    public static final SlotType CONTAINER = new SlotType("CONTAINER");
    public static final SlotType ARMOR     = new SlotType("ARMOR");
    public static final SlotType CRAFTING  = new SlotType("CRAFTING");
    public static final SlotType FUEL      = new SlotType("FUEL");
    public static final SlotType HOTBAR    = new SlotType("HOTBAR");
    public static final SlotType RESULT    = new SlotType("RESULT");
    public static final SlotType OUTSIDE   = new SlotType("OUTSIDE");

    public SlotType(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public SlotType(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link SlotType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final SlotType element)
    {
        ASimpleEnum.register(SlotType.class, element);
    }

    /**
     * Get one of {@link SlotType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static SlotType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(SlotType.class, ordinal);
    }

    /**
     * Get one of SlotType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static SlotType getByEnumName(final String name)
    {
        return getByEnumName(SlotType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static SlotType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(SlotType.class);
        return (SlotType[]) map.values(new SlotType[map.size()]);
    }

    static
    {
        SlotType.register(CONTAINER);
        SlotType.register(ARMOR);
        SlotType.register(CRAFTING);
        SlotType.register(FUEL);
        SlotType.register(HOTBAR);
        SlotType.register(RESULT);
        SlotType.register(OUTSIDE);
    }
}