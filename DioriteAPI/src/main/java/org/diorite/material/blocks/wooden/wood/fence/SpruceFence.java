package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SpruceFence extends WoodenFence
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPRUCE_FENCE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPRUCE_FENCE__HARDNESS;

    public static final SpruceFence SPRUCE_FENCE = new SpruceFence();

    private static final Map<String, SpruceFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceFence()
    {
        super("SPRUCE_FENCE", 188, "minecraft:spruce_fence", "SPRUCE_FENCE", WoodType.OAK);
    }

    public SpruceFence(final String enumName)
    {
        super(SPRUCE_FENCE.name(), SPRUCE_FENCE.getId(), SPRUCE_FENCE.getMinecraftId(), enumName, WoodType.OAK);
    }

    public SpruceFence(final int maxStack, final String typeName)
    {
        super(SPRUCE_FENCE.name(), SPRUCE_FENCE.getId(), SPRUCE_FENCE.getMinecraftId(), maxStack, typeName, WoodType.OAK);
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
    public SpruceFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceFence getType(final int id)
    {
        return getByID(id);
    }

    public static SpruceFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SpruceFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SpruceFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceFence.register(SPRUCE_FENCE);
    }
}