package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Slab;
import org.diorite.material.blocks.SlabType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DoubleStoneSlab extends StonySlab
{
    public static final byte  USED_DATA_VALUES = 18;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_SLAB__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_SLAB__HARDNESS;

    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_STONE         = new DoubleStoneSlab();
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_SANDSTONE     = new DoubleStoneSlab("SANDSTONE", SlabType.FULL, StoneSlabType.SANDSTONE);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_WOODEN        = new DoubleStoneSlab("WOODEN", SlabType.FULL, StoneSlabType.WOODEN);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_COBBLESTONE   = new DoubleStoneSlab("COBBLESTONE", SlabType.FULL, StoneSlabType.COBBLESTONE);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_BRICKS        = new DoubleStoneSlab("BRICKS", SlabType.FULL, StoneSlabType.BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_STONE_BRICKS  = new DoubleStoneSlab("STONE_BRICKS", SlabType.FULL, StoneSlabType.STONE_BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_NETHER_BRICKS = new DoubleStoneSlab("NETHER_BRICKS", SlabType.FULL, StoneSlabType.NETHER_BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_QUARTZ        = new DoubleStoneSlab("QUARTZ", SlabType.FULL, StoneSlabType.QUARTZ);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_RED_SANDSTONE = new DoubleStoneSlab2("RED_SANDSTONE", SlabType.FULL, StoneSlabType.RED_SANDSTONE);

    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_STONE_SMOOTH         = new DoubleStoneSlab("STONE_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.STONE);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH     = new DoubleStoneSlab("SANDSTONE_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.SANDSTONE);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_WOODEN_SMOOTH        = new DoubleStoneSlab("WOODEN_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.WOODEN);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH   = new DoubleStoneSlab("COBBLESTONE_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.COBBLESTONE);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_BRICKS_SMOOTH        = new DoubleStoneSlab("BRICKS_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH  = new DoubleStoneSlab("STONE_BRICKS_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.STONE_BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH = new DoubleStoneSlab("NETHER_BRICKS_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.NETHER_BRICKS);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_QUARTZ_SMOOTH        = new DoubleStoneSlab("QUARTZ_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.QUARTZ);
    public static final DoubleStoneSlab DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH = new DoubleStoneSlab2("RED_SANDSTONE_SMOOTH", SlabType.SMOOTH_FULL, StoneSlabType.RED_SANDSTONE);

    private static final Map<String, DoubleStoneSlab>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleStoneSlab> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DoubleStoneSlab()
    {
        super("DOUBLE_STONE_SLAB", 43, "minecraft:double_stone_slab", "STONE", SlabType.FULL, StoneSlabType.STONE);
    }

    @SuppressWarnings("MagicNumber")
    public DoubleStoneSlab(final String enumName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(DOUBLE_STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), (stoneType.isSecondStoneSlabID() ? 181 : 43), DOUBLE_STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), enumName, slabType, stoneType);
    }

    public DoubleStoneSlab(final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(DOUBLE_STONE_SLAB_STONE.name() + (stoneType.isSecondStoneSlabID() ? "2" : ""), (stoneType.isSecondStoneSlabID() ? 181 : 43), DOUBLE_STONE_SLAB_STONE.getMinecraftId() + (stoneType.isSecondStoneSlabID() ? "2" : ""), maxStack, typeName, slabType, stoneType);
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
    public DoubleStoneSlab getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleStoneSlab getType(final int id)
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
                return StoneSlab.getByID(combine(type, this.stoneType));
            case FULL:
            case SMOOTH_FULL:
                return getByID(combine(type, this.stoneType));
            default:
                return null;
        }
    }

    public static DoubleStoneSlab getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DoubleStoneSlab getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DoubleStoneSlab element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_STONE);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_SANDSTONE);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_WOODEN);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_COBBLESTONE);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_BRICKS);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_STONE_BRICKS);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_NETHER_BRICKS);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_QUARTZ);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_RED_SANDSTONE);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_STONE_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_SANDSTONE_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_WOODEN_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_COBBLESTONE_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_BRICKS_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_STONE_BRICKS_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_NETHER_BRICKS_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_QUARTZ_SMOOTH);
        DoubleStoneSlab.register(DOUBLE_STONE_SLAB_RED_SANDSTONE_SMOOTH);
    }

    /**
     * Helper class for second stone slab ID
     */
    public static class DoubleStoneSlab2 extends DoubleStoneSlab
    {
        public DoubleStoneSlab2()
        {
        }

        public DoubleStoneSlab2(final String enumName, final SlabType slabType, final StoneSlabType stoneType)
        {
            super(enumName, slabType, stoneType);
        }

        public DoubleStoneSlab2(final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
        {
            super(maxStack, typeName, slabType, stoneType);
        }

        @SuppressWarnings("MagicNumber")
        public static DoubleStoneSlab getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }
}
