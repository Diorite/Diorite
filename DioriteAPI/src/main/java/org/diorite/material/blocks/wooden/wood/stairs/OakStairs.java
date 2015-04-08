package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class OakStairs extends WoodenStairs
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_STAIRS__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_STAIRS__HARDNESS;

    public static final OakStairs OAK_STAIRS = new OakStairs();

    private static final Map<String, OakStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakStairs()
    {
        super("OAK_STAIRS", 53, "minecraft:oak_stairs", "OAK_STAIRS", WoodType.OAK);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public OakStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakStairs getType(final int id)
    {
        return getByID(id);
    }

    public static OakStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static OakStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final OakStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakStairs.register(OAK_STAIRS);
    }
}