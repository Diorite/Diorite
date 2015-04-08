package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class OakFence extends WoodenFence
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_FENCE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_FENCE__HARDNESS;

    public static final OakFence OAK_FENCE = new OakFence();

    private static final Map<String, OakFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakFence()
    {
        super("OAK_FENCE", 85, "minecraft:fence", "OAK_FENCE", WoodType.OAK);
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
    public OakFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFence getType(final int id)
    {
        return getByID(id);
    }

    public static OakFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static OakFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final OakFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakFence.register(OAK_FENCE);
    }
}