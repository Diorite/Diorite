package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Slab;
import org.diorite.material.blocks.SlabType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StoneSlab" and all its subtypes.
 */
public class StoneSlab extends StonySlab
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

    public static final StoneSlab STONE_SLAB_STONE         = new StoneSlab();
    public static final StoneSlab STONE_SLAB_SANDSTONE     = new StoneSlab("SANDSTONE", SlabType.BOTTOM, StoneSlabType.SANDSTONE);
    public static final StoneSlab STONE_SLAB_WOODEN        = new StoneSlab("WOODEN", SlabType.BOTTOM, StoneSlabType.WOODEN);
    public static final StoneSlab STONE_SLAB_COBBLESTONE   = new StoneSlab("COBBLESTONE", SlabType.BOTTOM, StoneSlabType.COBBLESTONE);
    public static final StoneSlab STONE_SLAB_BRICKS        = new StoneSlab("BRICKS", SlabType.BOTTOM, StoneSlabType.BRICKS);
    public static final StoneSlab STONE_SLAB_STONE_BRICKS  = new StoneSlab("STONE_BRICKS", SlabType.BOTTOM, StoneSlabType.STONE_BRICKS);
    public static final StoneSlab STONE_SLAB_NETHER_BRICKS = new StoneSlab("NETHER_BRICKS", SlabType.BOTTOM, StoneSlabType.NETHER_BRICKS);
    public static final StoneSlab STONE_SLAB_QUARTZ        = new StoneSlab("QUARTZ", SlabType.BOTTOM, StoneSlabType.QUARTZ);
    public static final StoneSlab STONE_SLAB_RED_SANDSTONE = new StoneSlab2("RED_SANDSTONE", SlabType.BOTTOM, StoneSlabType.RED_SANDSTONE);

    public static final StoneSlab STONE_SLAB_STONE_UPPER         = new StoneSlab("STONE_UPPER", SlabType.UPPER, StoneSlabType.STONE);
    public static final StoneSlab STONE_SLAB_SANDSTONE_UPPER     = new StoneSlab("SANDSTONE_UPPER", SlabType.UPPER, StoneSlabType.SANDSTONE);
    public static final StoneSlab STONE_SLAB_WOODEN_UPPER        = new StoneSlab("WOODEN_UPPER", SlabType.UPPER, StoneSlabType.WOODEN);
    public static final StoneSlab STONE_SLAB_COBBLESTONE_UPPER   = new StoneSlab("COBBLESTONE_UPPER", SlabType.UPPER, StoneSlabType.COBBLESTONE);
    public static final StoneSlab STONE_SLAB_BRICKS_UPPER        = new StoneSlab("BRICKS_UPPER", SlabType.UPPER, StoneSlabType.BRICKS);
    public static final StoneSlab STONE_SLAB_STONE_BRICKS_UPPER  = new StoneSlab("STONE_BRICKS_UPPER", SlabType.UPPER, StoneSlabType.STONE_BRICKS);
    public static final StoneSlab STONE_SLAB_NETHER_BRICKS_UPPER = new StoneSlab("NETHER_BRICKS_UPPER", SlabType.UPPER, StoneSlabType.NETHER_BRICKS);
    public static final StoneSlab STONE_SLAB_QUARTZ_UPPER        = new StoneSlab("QUARTZ_UPPER", SlabType.UPPER, StoneSlabType.QUARTZ);
    public static final StoneSlab STONE_SLAB_RED_SANDSTONE_UPPER = new StoneSlab2("RED_SANDSTONE_UPPER", SlabType.UPPER, StoneSlabType.RED_SANDSTONE);

    private static final Map<String, StoneSlab>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneSlab> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneSlab()
    {
        super("STONE_SLAB", 44, "minecraft:stone_slab", "STONE", SlabType.BOTTOM, StoneSlabType.STONE);
    }

    @SuppressWarnings("MagicNumber")
    public StoneSlab(final String enumName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), stoneType.isSecondStoneSlabID() ? 182 : 44, STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType);
    }

    @SuppressWarnings("MagicNumber")
    public StoneSlab(final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), stoneType.isSecondStoneSlabID() ? 182 : 44, STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), maxStack, typeName, slabType, stoneType);
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
    public StoneSlab getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneSlab getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public Slab getSlab(final SlabType type)
    {
        switch (type)
        {
            case BOTTOM:
            case UPPER:
                return getByID(combine(type, this.stoneType));
            case FULL:
            case SMOOTH_FULL:
                return DoubleStoneSlab.getByID(combine(type, this.stoneType));
            default:
                return null;
        }
    }

    /**
     * Returns one of StoneSlab sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlab getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StoneSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlab getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final StoneSlab element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneSlab.register(STONE_SLAB_STONE);
        StoneSlab.register(STONE_SLAB_SANDSTONE);
        StoneSlab.register(STONE_SLAB_WOODEN);
        StoneSlab.register(STONE_SLAB_COBBLESTONE);
        StoneSlab.register(STONE_SLAB_BRICKS);
        StoneSlab.register(STONE_SLAB_STONE_BRICKS);
        StoneSlab.register(STONE_SLAB_NETHER_BRICKS);
        StoneSlab.register(STONE_SLAB_QUARTZ);
        StoneSlab.register(STONE_SLAB_RED_SANDSTONE);
        StoneSlab.register(STONE_SLAB_STONE_UPPER);
        StoneSlab.register(STONE_SLAB_SANDSTONE_UPPER);
        StoneSlab.register(STONE_SLAB_WOODEN_UPPER);
        StoneSlab.register(STONE_SLAB_COBBLESTONE_UPPER);
        StoneSlab.register(STONE_SLAB_BRICKS_UPPER);
        StoneSlab.register(STONE_SLAB_STONE_BRICKS_UPPER);
        StoneSlab.register(STONE_SLAB_NETHER_BRICKS_UPPER);
        StoneSlab.register(STONE_SLAB_QUARTZ_UPPER);
        StoneSlab.register(STONE_SLAB_RED_SANDSTONE_UPPER);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class StoneSlab2 extends StoneSlab
    {
        public StoneSlab2()
        {
        }

        public StoneSlab2(final String enumName, final SlabType slabType, final StoneSlabType stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        public StoneSlab2(final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
        {
            super(maxStack, typeName, slabType, stoneType);
        }

        @SuppressWarnings("MagicNumber")
        /**
     * Returns one of StoneSlab sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of StoneSlab or null
     */
    public static StoneSlab getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }
}
