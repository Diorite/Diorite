package org.diorite.world;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class Dimension extends ASimpleEnum<Dimension>
{
    static
    {
        init(Dimension.class, 3);
    }

    public static final Dimension NETHER    = new Dimension("NETHER", false, - 1);
    public static final Dimension OVERWORLD = new Dimension("OVERWORLD", true, 0);
    public static final Dimension END       = new Dimension("END", false, 1);

    private static final TIntObjectMap<Dimension> byDimID = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);

    private final boolean hasSkyLight;
    private final int     id;

    public Dimension(final String enumName, final int enumId, final boolean hasSkyLight, final int id)
    {
        super(enumName, enumId);
        this.hasSkyLight = hasSkyLight;
        this.id = id;
    }

    public Dimension(final String enumName, final boolean hasSkyLight, final int id)
    {
        super(enumName);
        this.hasSkyLight = hasSkyLight;
        this.id = id;
    }

    public int getDimensionId()
    {
        return this.id;
    }

    public Dimension byDimensionId(final int id)
    {
        return byDimID.get(id);
    }

    public boolean hasSkyLight()
    {
        return this.hasSkyLight;
    }

    public static Dimension getByDimensionId(final int id)
    {
        return byDimID.get(id);
    }

    /**
     * Register new {@link Dimension} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Dimension element)
    {
        ASimpleEnum.register(Dimension.class, element);
        byDimID.put(element.getDimensionId(), element);
    }

    /**
     * Get one of {@link Dimension} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Dimension getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Dimension.class, ordinal);
    }

    /**
     * Get one of Dimension entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Dimension getByEnumName(final String name)
    {
        return getByEnumName(Dimension.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Dimension[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(Dimension.class);
        return (Dimension[]) map.values(new Dimension[map.size()]);
    }

    static
    {
        Dimension.register(NETHER);
        Dimension.register(OVERWORLD);
        Dimension.register(END);
    }
}
