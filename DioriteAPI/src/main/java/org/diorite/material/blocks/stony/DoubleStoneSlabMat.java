package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.SlabTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DoubleStoneSlab" and all its subtypes.
 */
public class DoubleStoneSlabMat extends StonySlabMat
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

    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE         = new DoubleStoneSlabMat();
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_SANDSTONE     = new DoubleStoneSlabMat("SANDSTONE", SlabTypeMat.FULL, StoneSlabTypeMat.SANDSTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_WOODEN        = new DoubleStoneSlabMat("WOODEN", SlabTypeMat.FULL, StoneSlabTypeMat.WOODEN);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_COBBLESTONE   = new DoubleStoneSlabMat("COBBLESTONE", SlabTypeMat.FULL, StoneSlabTypeMat.COBBLESTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_BRICKS        = new DoubleStoneSlabMat("BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_BRICKS  = new DoubleStoneSlabMat("STONE_BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.STONE_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_NETHER_BRICKS = new DoubleStoneSlabMat("NETHER_BRICKS", SlabTypeMat.FULL, StoneSlabTypeMat.NETHER_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_QUARTZ        = new DoubleStoneSlabMat("QUARTZ", SlabTypeMat.FULL, StoneSlabTypeMat.QUARTZ);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_RED_SANDSTONE = new DoubleStoneSlab2("RED_SANDSTONE", SlabTypeMat.FULL, StoneSlabTypeMat.RED_SANDSTONE);

    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_SMOOTH         = new DoubleStoneSlabMat("STONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.STONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH     = new DoubleStoneSlabMat("SANDSTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.SANDSTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_WOODEN_SMOOTH        = new DoubleStoneSlabMat("WOODEN_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.WOODEN);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH   = new DoubleStoneSlabMat("COBBLESTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.COBBLESTONE);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_BRICKS_SMOOTH        = new DoubleStoneSlabMat("BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH  = new DoubleStoneSlabMat("STONE_BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.STONE_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH = new DoubleStoneSlabMat("NETHER_BRICKS_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.NETHER_BRICKS);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_QUARTZ_SMOOTH        = new DoubleStoneSlabMat("QUARTZ_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.QUARTZ);
    public static final DoubleStoneSlabMat DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH = new DoubleStoneSlab2("RED_SANDSTONE_SMOOTH", SlabTypeMat.SMOOTH_FULL, StoneSlabTypeMat.RED_SANDSTONE);

    private static final Map<String, DoubleStoneSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleStoneSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlabMat()
    {
        super("DOUBLE_STONE_SLAB", 43, "minecraft:double_stone_slab", "STONE", SlabTypeMat.FULL, StoneSlabTypeMat.STONE);
    }

    protected DoubleStoneSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, slabType, stoneType);
    }

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlabMat(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        super(DOUBLE_STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), (stoneType.isSecondStoneSlabID() ? 181 : 43), DOUBLE_STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType);
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
    public DoubleStoneSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleStoneSlabMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DoubleStoneSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleStoneSlab or null
     */
    public static DoubleStoneSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleStoneSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleStoneSlab or null
     */
    public static DoubleStoneSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Return one of DoubleStoneSlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of DoubleStoneSlab
     */
    public static DoubleStoneSlabMat getDoubleStoneSlab(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        return getByID(combine(slabType, stoneType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleStoneSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public DoubleStoneSlabMat[] types()
    {
        return DoubleStoneSlabMat.doubleStoneSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DoubleStoneSlabMat[] doubleStoneSlabTypes()
    {
        return byID.values(new DoubleStoneSlabMat[byID.size()]);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class DoubleStoneSlab2 extends DoubleStoneSlabMat
    {

        public DoubleStoneSlab2(final String enumName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of DoubleStoneSlab sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of DoubleStoneSlab or null
         */
        public static DoubleStoneSlabMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_SANDSTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_WOODEN);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_COBBLESTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_NETHER_BRICKS);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_QUARTZ);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_RED_SANDSTONE);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_WOODEN_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_QUARTZ_SMOOTH);
        DoubleStoneSlabMat.register(DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH);
    }
}
