package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class BrichFence extends WoodenFence
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BRICH_FENCE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BRICH_FENCE__HARDNESS;

    public static final BrichFence BRICH_FENCE = new BrichFence();

    private static final Map<String, BrichFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BrichFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BrichFence()
    {
        super("BRICH_FENCE", 189, "minecraft:brich_fence", "BRICH_FENCE", WoodType.OAK);
    }

    public BrichFence(final String enumName)
    {
        super(BRICH_FENCE.name(), BRICH_FENCE.getId(), BRICH_FENCE.getMinecraftId(), enumName, WoodType.OAK);
    }

    public BrichFence(final int maxStack, final String typeName)
    {
        super(BRICH_FENCE.name(), BRICH_FENCE.getId(), BRICH_FENCE.getMinecraftId(), maxStack, typeName, WoodType.OAK);
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
    public BrichFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BrichFence getType(final int id)
    {
        return getByID(id);
    }

    public static BrichFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static BrichFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final BrichFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BrichFence.register(BRICH_FENCE);
    }
}