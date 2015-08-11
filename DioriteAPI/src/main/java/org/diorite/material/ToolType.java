package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent type of tool.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class ToolType extends ASimpleEnum<ToolType>
{
    static
    {
        init(ToolType.class, 5);
    }

    public static final ToolType PICKAXE = new ToolType("PICKAXE");
    public static final ToolType SHOVEL  = new ToolType("SHOVEL");
    public static final ToolType AXE     = new ToolType("AXE");
    public static final ToolType HOE     = new ToolType("HOE");
    public static final ToolType UNIQUE  = new ToolType("UNIQUE"); // for tools that don't have any other types, like flint and steel.

    public ToolType(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ToolType(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ToolType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ToolType element)
    {
        ASimpleEnum.register(ToolType.class, element);
    }

    /**
     * Get one of {@link ToolType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ToolType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ToolType.class, ordinal);
    }

    /**
     * Get one of ToolType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ToolType getByEnumName(final String name)
    {
        return getByEnumName(ToolType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ToolType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ToolType.class);
        return (ToolType[]) map.values(new ToolType[map.size()]);
    }

    static
    {
        ToolType.register(PICKAXE);
        ToolType.register(SHOVEL);
        ToolType.register(AXE);
        ToolType.register(HOE);
        ToolType.register(UNIQUE);
    }
}