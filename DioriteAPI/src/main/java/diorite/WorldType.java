package diorite;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum WorldType
{
    NORMAL(0, "default", 1),
    FLAT(1, "flat"),
    LARGE_BIOMES(2, "largeBiomes"),
    AMPLIFIED(3, "amplified"),
    CUSTOMIZED(4, "customized"),
    DEBUG_ALL_BLOCK_STATES(5, "debug_all_block_states"),
    NORMAL_1_1(8, "default_1_1", 0);

    private final int    id;
    private final String name;
    private final int    version;
    private static final TByteObjectMap<WorldType> elements = new TByteObjectHashMap<>();

    WorldType(final int id, final String name, final int version)
    {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    WorldType(final int id, final String name)
    {
        this(id, name, 0);
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
        return elements.get((byte) id);
    }

    static
    {
        for (final WorldType type : WorldType.values())
        {
            elements.put((byte) type.id, type);
        }
    }

    public static WorldType getType(final String name)
    {
        for (final WorldType type : WorldType.values())
        {
            if (type.getName().equals(name))
            {
                return type;
            }
        }
        return null;
    }
}
