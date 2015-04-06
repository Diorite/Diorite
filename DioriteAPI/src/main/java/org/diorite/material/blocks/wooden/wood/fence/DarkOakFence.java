package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DarkOakFence extends WoodenFence
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_FENCE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_FENCE__HARDNESS;

    public static final DarkOakFence DARK_OAK_FENCE = new DarkOakFence();

    private static final Map<String, DarkOakFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakFence()
    {
        super("DARK_OAK_FENCE", 191, "minecraft:dark_oak_fence", "DARK_OAK_FENCE", WoodType.OAK);
    }

    public DarkOakFence(final String enumName)
    {
        super(DARK_OAK_FENCE.name(), DARK_OAK_FENCE.getId(), DARK_OAK_FENCE.getMinecraftId(), enumName, WoodType.OAK);
    }

    public DarkOakFence(final int maxStack, final String typeName)
    {
        super(DARK_OAK_FENCE.name(), DARK_OAK_FENCE.getId(), DARK_OAK_FENCE.getMinecraftId(), maxStack, typeName, WoodType.OAK);
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
    public DarkOakFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakFence getType(final int id)
    {
        return getByID(id);
    }

    public static DarkOakFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DarkOakFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DarkOakFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakFence.register(DARK_OAK_FENCE);
    }
}