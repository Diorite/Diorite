package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class BirchStairs extends WoodenStairs
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_STAIRS__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_STAIRS__HARDNESS;

    public static final BirchStairs BIRCH_STAIRS = new BirchStairs();

    private static final Map<String, BirchStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchStairs()
    {
        super("BIRCH_STAIRS", 135, "minecraft:birch_stairs", "BIRCH_STAIRS", WoodType.BIRCH);
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
    public BirchStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchStairs getType(final int id)
    {
        return getByID(id);
    }

    public static BirchStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static BirchStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final BirchStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchStairs.register(BIRCH_STAIRS);
    }
}