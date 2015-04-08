package org.diorite.material.blocks.wooden.wood.stairs;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DarkOakStairs extends WoodenStairs
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_STAIRS__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_STAIRS__HARDNESS;

    public static final DarkOakStairs DARK_OAK_STAIRS = new DarkOakStairs();

    private static final Map<String, DarkOakStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakStairs()
    {
        super("DARK_OAK_STAIRS", 164, "minecraft:dark_oak_stairs", "DARK_OAK_STAIRS", WoodType.DARK_OAK);
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
    public DarkOakStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakStairs getType(final int id)
    {
        return getByID(id);
    }

    public static DarkOakStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DarkOakStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DarkOakStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakStairs.register(DARK_OAK_STAIRS);
    }
}