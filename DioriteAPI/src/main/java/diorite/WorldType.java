package diorite;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.collections.SimpleStringHashMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class WorldType
{
    public static final WorldType NORMAL                 = new WorldType("NORMAL", 0, "default", 1);
    public static final WorldType FLAT                   = new WorldType("FLAT", 1, "flat");
    public static final WorldType LARGE_BIOMES           = new WorldType("LARGE_BIOMES", 2, "largeBiomes");
    public static final WorldType AMPLIFIED              = new WorldType("AMPLIFIED", 3, "amplified");
    public static final WorldType CUSTOMIZED             = new WorldType("CUSTOMIZED", 4, "customized");
    public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType("DEBUG_ALL_BLOCK_STATES", 5, "debug_all_block_states");
    public static final WorldType NORMAL_1_1             = new WorldType("NORMAL_1_1", 8, "default_1_1", 0);

    private final String enumName;
    private final int    id;
    private final String name;
    private final int    version;
    private static final Map<String, WorldType>   byName = new SimpleStringHashMap<>(7, .1f);
    private static final TIntObjectMap<WorldType> byID   = new TIntObjectHashMap<>(7, .1f);

    public WorldType(final String enumName, final int id, final String name, final int version)
    {
        this.enumName = enumName;
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public WorldType(final String enumName, final int id, final String name)
    {
        this(enumName, id, name, 0);
    }

    public String name()
    {
        return this.enumName;
    }

    public int getId()
    {
        return this.id;
    }

    public int getVersion()
    {
        return this.version;
    }

    public String getName()
    {
        return this.name;
    }

    public static WorldType getByID(final int id)
    {
        if ((id > Byte.MAX_VALUE) || (id < 0))
        {
            return null;
        }
        return byID.get((byte) id);
    }

    public static WorldType getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final WorldType element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
    }

    static
    {
        register(NORMAL);
        register(FLAT);
        register(LARGE_BIOMES);
        register(AMPLIFIED);
        register(CUSTOMIZED);
        register(DEBUG_ALL_BLOCK_STATES);
        register(NORMAL_1_1);
    }

    public static WorldType getType(final String name)
    {
        for (final WorldType type : WorldType.byName.values())
        {
            if (type.getName().equals(name))
            {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("name", this.name).append("version", this.version).toString();
    }
}
