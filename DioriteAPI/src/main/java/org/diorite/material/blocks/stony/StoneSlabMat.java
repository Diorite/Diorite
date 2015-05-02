package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.SlabTypeMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneSlab" and all its subtypes.
 */
public class StoneSlabMat extends StonySlabMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 18;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_SLAB__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_SLAB__HARDNESS;

    public static final StoneSlabMat STONE_SLAB_STONE         = new StoneSlabMat();
    public static final StoneSlabMat STONE_SLAB_SANDSTONE     = new StoneSlabMat("SANDSTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.SANDSTONE);
    public static final StoneSlabMat STONE_SLAB_WOODEN        = new StoneSlabMat("WOODEN", SlabTypeMat.BOTTOM, StoneSlabTypeMat.WOODEN);
    public static final StoneSlabMat STONE_SLAB_COBBLESTONE   = new StoneSlabMat("COBBLESTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.COBBLESTONE);
    public static final StoneSlabMat STONE_SLAB_BRICKS        = new StoneSlabMat("BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.BRICKS);
    public static final StoneSlabMat STONE_SLAB_STONE_BRICKS  = new StoneSlabMat("STONE_BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.STONE_BRICKS);
    public static final StoneSlabMat STONE_SLAB_NETHER_BRICKS = new StoneSlabMat("NETHER_BRICKS", SlabTypeMat.BOTTOM, StoneSlabTypeMat.NETHER_BRICKS);
    public static final StoneSlabMat STONE_SLAB_QUARTZ        = new StoneSlabMat("QUARTZ", SlabTypeMat.BOTTOM, StoneSlabTypeMat.QUARTZ);
    public static final StoneSlabMat STONE_SLAB_RED_SANDSTONE = new StoneSlab2("RED_SANDSTONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.RED_SANDSTONE);

    public static final StoneSlabMat STONE_SLAB_STONE_UPPER         = new StoneSlabMat("STONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.STONE);
    public static final StoneSlabMat STONE_SLAB_SANDSTONE_UPPER     = new StoneSlabMat("SANDSTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.SANDSTONE);
    public static final StoneSlabMat STONE_SLAB_WOODEN_UPPER        = new StoneSlabMat("WOODEN_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.WOODEN);
    public static final StoneSlabMat STONE_SLAB_COBBLESTONE_UPPER   = new StoneSlabMat("COBBLESTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.COBBLESTONE);
    public static final StoneSlabMat STONE_SLAB_BRICKS_UPPER        = new StoneSlabMat("BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.BRICKS);
    public static final StoneSlabMat STONE_SLAB_STONE_BRICKS_UPPER  = new StoneSlabMat("STONE_BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.STONE_BRICKS);
    public static final StoneSlabMat STONE_SLAB_NETHER_BRICKS_UPPER = new StoneSlabMat("NETHER_BRICKS_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.NETHER_BRICKS);
    public static final StoneSlabMat STONE_SLAB_QUARTZ_UPPER        = new StoneSlabMat("QUARTZ_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.QUARTZ);
    public static final StoneSlabMat STONE_SLAB_RED_SANDSTONE_UPPER = new StoneSlab2("RED_SANDSTONE_UPPER", SlabTypeMat.UPPER, StoneSlabTypeMat.RED_SANDSTONE);

    private static final Map<String, StoneSlabMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneSlabMat()
    {
        super("STONE_SLAB", 44, "minecraft:stone_slab", "STONE", SlabTypeMat.BOTTOM, StoneSlabTypeMat.STONE);
    }

    @SuppressWarnings("MagicNumber")
    protected StoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), stoneType.isSecondStoneSlabID() ? 182 : 44, STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType);
    }

    protected StoneSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, slabType, stoneType);
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
    public StoneSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneSlabMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of StoneSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Return one of StoneSlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of StoneSlab
     */
    public static StoneSlabMat getStoneSlab(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        return getByID(combine(slabType, stoneType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StoneSlabMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class StoneSlab2 extends StoneSlabMat
    {
        public StoneSlab2(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of StoneSlab sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of StoneSlab or null
         */
        public static StoneSlabMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        StoneSlabMat.register(STONE_SLAB_STONE);
        StoneSlabMat.register(STONE_SLAB_SANDSTONE);
        StoneSlabMat.register(STONE_SLAB_WOODEN);
        StoneSlabMat.register(STONE_SLAB_COBBLESTONE);
        StoneSlabMat.register(STONE_SLAB_BRICKS);
        StoneSlabMat.register(STONE_SLAB_STONE_BRICKS);
        StoneSlabMat.register(STONE_SLAB_NETHER_BRICKS);
        StoneSlabMat.register(STONE_SLAB_QUARTZ);
        StoneSlabMat.register(STONE_SLAB_RED_SANDSTONE);
        StoneSlabMat.register(STONE_SLAB_STONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_SANDSTONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_WOODEN_UPPER);
        StoneSlabMat.register(STONE_SLAB_COBBLESTONE_UPPER);
        StoneSlabMat.register(STONE_SLAB_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_STONE_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_NETHER_BRICKS_UPPER);
        StoneSlabMat.register(STONE_SLAB_QUARTZ_UPPER);
        StoneSlabMat.register(STONE_SLAB_RED_SANDSTONE_UPPER);
    }
}
