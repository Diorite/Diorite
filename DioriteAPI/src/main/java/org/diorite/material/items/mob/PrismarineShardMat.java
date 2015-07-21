package org.diorite.material.items.mob;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class PrismarineShardMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final PrismarineShardMat PRISMARINE_SHARD = new PrismarineShardMat();

    private static final Map<String, PrismarineShardMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PrismarineShardMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected PrismarineShardMat()
    {
        super("PRISMARINE_SHARD", 409, "minecraft:prismarine_shard", "PRISMARINE_SHARD", (short) 0x00);
    }

    protected PrismarineShardMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected PrismarineShardMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public PrismarineShardMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PrismarineShardMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of PrismarineShard sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PrismarineShard or null
     */
    public static PrismarineShardMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of PrismarineShard sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PrismarineShard or null
     */
    public static PrismarineShardMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PrismarineShardMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PrismarineShardMat[] types()
    {
        return PrismarineShardMat.prismarineShardTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PrismarineShardMat[] prismarineShardTypes()
    {
        return byID.values(new PrismarineShardMat[byID.size()]);
    }

    static
    {
        PrismarineShardMat.register(PRISMARINE_SHARD);
    }
}

