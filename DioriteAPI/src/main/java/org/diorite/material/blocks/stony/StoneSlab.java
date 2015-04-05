package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Slab;
import org.diorite.material.blocks.SlabType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StoneSlab extends StonySlab
{
    public static final byte  USED_DATA_VALUES = 16;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_SLAB__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_SLAB__HARDNESS;

    public static final StoneSlab STONE_SLAB_STONE         = new StoneSlab();
    public static final StoneSlab STONE_SLAB_SANDSTONE     = new StoneSlab("SANDSTONE", SlabType.BOTTOM, StoneSlabType.SANDSTONE);
    public static final StoneSlab STONE_SLAB_WOODEN        = new StoneSlab("WOODEN", SlabType.BOTTOM, StoneSlabType.WOODEN);
    public static final StoneSlab STONE_SLAB_COBBLESTONE   = new StoneSlab("COBBLESTONE", SlabType.BOTTOM, StoneSlabType.COBBLESTONE);
    public static final StoneSlab STONE_SLAB_BRICKS        = new StoneSlab("BRICKS", SlabType.BOTTOM, StoneSlabType.BRICKS);
    public static final StoneSlab STONE_SLAB_STONE_BRICKS  = new StoneSlab("STONE_BRICKS", SlabType.BOTTOM, StoneSlabType.STONE_BRICKS);
    public static final StoneSlab STONE_SLAB_NETHER_BRICKS = new StoneSlab("NETHER_BRICKS", SlabType.BOTTOM, StoneSlabType.NETHER_BRICKS);
    public static final StoneSlab STONE_SLAB_QUARTZ        = new StoneSlab("QUARTZ", SlabType.BOTTOM, StoneSlabType.QUARTZ);

    public static final StoneSlab STONE_SLAB_STONE_SMOOTH         = new StoneSlab("STONE_SMOOTH", SlabType.UPPER, StoneSlabType.STONE);
    public static final StoneSlab STONE_SLAB_SANDSTONE_SMOOTH     = new StoneSlab("SANDSTONE_SMOOTH", SlabType.UPPER, StoneSlabType.SANDSTONE);
    public static final StoneSlab STONE_SLAB_WOODEN_SMOOTH        = new StoneSlab("WOODEN_SMOOTH", SlabType.UPPER, StoneSlabType.WOODEN);
    public static final StoneSlab STONE_SLAB_COBBLESTONE_SMOOTH   = new StoneSlab("COBBLESTONE_SMOOTH", SlabType.UPPER, StoneSlabType.COBBLESTONE);
    public static final StoneSlab STONE_SLAB_BRICKS_SMOOTH        = new StoneSlab("BRICKS_SMOOTH", SlabType.UPPER, StoneSlabType.BRICKS);
    public static final StoneSlab STONE_SLAB_STONE_BRICKS_SMOOTH  = new StoneSlab("STONE_BRICKS_SMOOTH", SlabType.UPPER, StoneSlabType.STONE_BRICKS);
    public static final StoneSlab STONE_SLAB_NETHER_BRICKS_SMOOTH = new StoneSlab("NETHER_BRICKS_SMOOTH", SlabType.UPPER, StoneSlabType.NETHER_BRICKS);
    public static final StoneSlab STONE_SLAB_QUARTZ_SMOOTH        = new StoneSlab("QUARTZ_SMOOTH", SlabType.UPPER, StoneSlabType.QUARTZ);

    private static final Map<String, StoneSlab>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneSlab> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneSlab()
    {
        super("STONE_SLAB", 44, "minecraft:stone_slab", "STONE", SlabType.BOTTOM, StoneSlabType.STONE);
    }

    public StoneSlab(final String enumName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(STONE_SLAB_STONE.name(), STONE_SLAB_STONE.getId(), STONE_SLAB_STONE.getMinecraftId(), enumName, slabType, stoneType);
    }

    public StoneSlab(final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(STONE_SLAB_STONE.name(), STONE_SLAB_STONE.getId(), STONE_SLAB_STONE.getMinecraftId(), maxStack, typeName, slabType, stoneType);
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

    public static StoneSlab getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StoneSlab getByEnumName(final String name)
    {
        return byName.get(name);
    }

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
        StoneSlab.register(STONE_SLAB_STONE_SMOOTH);
        StoneSlab.register(STONE_SLAB_SANDSTONE_SMOOTH);
        StoneSlab.register(STONE_SLAB_WOODEN_SMOOTH);
        StoneSlab.register(STONE_SLAB_COBBLESTONE_SMOOTH);
        StoneSlab.register(STONE_SLAB_BRICKS_SMOOTH);
        StoneSlab.register(STONE_SLAB_STONE_BRICKS_SMOOTH);
        StoneSlab.register(STONE_SLAB_NETHER_BRICKS_SMOOTH);
        StoneSlab.register(STONE_SLAB_QUARTZ_SMOOTH);
    }
}
