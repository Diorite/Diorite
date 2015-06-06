package org.diorite.world;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Dimension implements SimpleEnum<Dimension>
{
    public static final Dimension NETHER    = new Dimension("NETHER", 0, false, - 1);
    public static final Dimension OVERWORLD = new Dimension("OVERWORLD", 1, true, 0);
    public static final Dimension END       = new Dimension("END", 2, false, 1);

    private static final Map<String, Dimension>   byName  = new CaseInsensitiveMap<>(3, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Dimension> byID    = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Dimension> byDimID = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);


    private final String  enumName;
    private final int     enumId;
    private final boolean hasSkyLight;
    private final int     id;

    public Dimension(final String enumName, final int enumId, final boolean hasSkyLight, final int id)
    {
        this.enumName = enumName;
        this.enumId = enumId;
        this.hasSkyLight = hasSkyLight;
        this.id = id;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.enumId;
    }

    @Override
    public Dimension byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public Dimension byName(final String name)
    {
        return byName.get(name);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("enumId", this.enumId).toString();
    }

    public static Dimension getByID(final int id)
    {
        return byID.get(id);
    }

    public static Dimension getByDimensionId(final int id)
    {
        return byDimID.get(id);
    }

    public static Dimension getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Dimension element)
    {
        byID.put(element.getId(), element);
        byDimID.put(element.getDimensionId(), element);
        byName.put(element.name(), element);
    }

    /**
     * @return all values in array.
     */
    public static Dimension[] values()
    {
        return byID.values(new Dimension[byID.size()]);
    }

    static
    {
        register(NETHER);
        register(OVERWORLD);
        register(END);
    }
}
