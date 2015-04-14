package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Stairs;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "QuartzStairs" and all its subtypes.
 */
public class QuartzStairs extends BlockMaterialData implements Stairs
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_STAIRS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_STAIRS__HARDNESS;

    public static final QuartzStairs QUARTZ_STAIRS = new QuartzStairs();

    private static final Map<String, QuartzStairs>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzStairs> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected QuartzStairs()
    {
        super("QUARTZ_STAIRS", 156, "minecraft:quartz_brick_stairs", "QUARTZ_STAIRS", (byte) 0x00);
    }

    public QuartzStairs(final String enumName, final int type)
    {
        super(QUARTZ_STAIRS.name(), QUARTZ_STAIRS.getId(), QUARTZ_STAIRS.getMinecraftId(), enumName, (byte) type);
    }

    public QuartzStairs(final int maxStack, final String typeName, final byte type)
    {
        super(QUARTZ_STAIRS.name(), QUARTZ_STAIRS.getId(), QUARTZ_STAIRS.getMinecraftId(), maxStack, typeName, type);
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
    public QuartzStairs getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzStairs getType(final int id)
    {
        return getByID(id);
    }

    public static QuartzStairs getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static QuartzStairs getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final QuartzStairs element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzStairs.register(QUARTZ_STAIRS);
    }
}
