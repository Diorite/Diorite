package diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class Dimension
{
    public static final Dimension NETHER    = new Dimension("NETHER", - 1);
    public static final Dimension OVERWORLD = new Dimension("OVERWORLD", 0);
    public static final Dimension END       = new Dimension("END", 1);
    private static final Map<String, Dimension>   byName = new SimpleStringHashMap<>(3, .1f);
    private static final TIntObjectMap<Dimension> byID   = new TIntObjectHashMap<>(3, .1f);
    private final String enumName;
    private final int    id;

    public Dimension(final String enumName, final int id)
    {
        this.enumName = enumName;
        this.id = id;
    }

    public String name()
    {
        return this.enumName;
    }

    public int getId()
    {
        return this.id;
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
    static
    {
        register(NETHER);
        register(OVERWORLD);
        register(END);
    }
}
