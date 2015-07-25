package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent type of armor element.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ArmorType extends ASimpleEnum<ArmorType>
{
    static
    {
        init(ArmorType.class, 4);
    }

    public static final ArmorType HELMET     = new ArmorType("HELMET");
    public static final ArmorType CHESTPLATE = new ArmorType("CHESTPLATE");
    public static final ArmorType LEGGINGS   = new ArmorType("LEGGINGS");
    public static final ArmorType BOOTS      = new ArmorType("BOOTS");

    public ArmorType(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ArmorType(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ArmorType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ArmorType element)
    {
        ASimpleEnum.register(ArmorType.class, element);
    }

    /**
     * Get one of {@link ArmorType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ArmorType.class, ordinal);
    }

    /**
     * Get one of ToolType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ArmorType getByEnumName(final String name)
    {
        return getByEnumName(ArmorType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ArmorType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ArmorType.class);
        return (ArmorType[]) map.values(new ArmorType[map.size()]);
    }

    static
    {
        ArmorType.register(HELMET);
        ArmorType.register(CHESTPLATE);
        ArmorType.register(LEGGINGS);
        ArmorType.register(BOOTS);
    }
}