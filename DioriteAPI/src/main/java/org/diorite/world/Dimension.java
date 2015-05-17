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
    public static final Dimension NETHER    = new Dimension("NETHER", - 1, false);
    public static final Dimension OVERWORLD = new Dimension("OVERWORLD", 0, true);
    public static final Dimension END       = new Dimension("END", 1, false);

    private static final Map<String, Dimension>   byName = new CaseInsensitiveMap<>(3, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Dimension> byID   = new TIntObjectHashMap<>(3, SMALL_LOAD_FACTOR);

    private final String  enumName;
    private final int     id;
    private final boolean hasSkyLight;

    public Dimension(final String enumName, final int id, final boolean hasSkyLight)
    {
        this.enumName = enumName;
        this.id = id;
        this.hasSkyLight = hasSkyLight;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
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

    public boolean hasSkyLight()
    {
        return this.hasSkyLight;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).toString();
    }

    public static Dimension getByID(final int id)
    {
        return byID.get(id);
    }

    public static Dimension getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Dimension element)
    {
        byID.put(element.getId(), element);
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
