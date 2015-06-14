package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.FenceGateMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchFenceGate" and all its subtypes.
 */
public class BirchFenceGateMat extends WoodenFenceGateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_FENCE_GATE__HARDNESS;

    public static final BirchFenceGateMat BIRCH_FENCE_GATE_SOUTH = new BirchFenceGateMat();
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_WEST  = new BirchFenceGateMat(BlockFace.WEST, false);
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_NORTH = new BirchFenceGateMat(BlockFace.NORTH, false);
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_EAST  = new BirchFenceGateMat(BlockFace.EAST, false);

    public static final BirchFenceGateMat BIRCH_FENCE_GATE_SOUTH_OPEN = new BirchFenceGateMat(BlockFace.SOUTH, true);
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_WEST_OPEN  = new BirchFenceGateMat(BlockFace.WEST, true);
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_NORTH_OPEN = new BirchFenceGateMat(BlockFace.NORTH, true);
    public static final BirchFenceGateMat BIRCH_FENCE_GATE_EAST_OPEN  = new BirchFenceGateMat(BlockFace.EAST, true);

    private static final Map<String, BirchFenceGateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchFenceGateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchFenceGateMat()
    {
        super("BIRCH_FENCE_GATE", 184, "minecraft:brich_fence_gate", WoodTypeMat.BIRCH, BlockFace.SOUTH, false);
    }

    protected BirchFenceGateMat(final BlockFace face, final boolean open)
    {
        super(BIRCH_FENCE_GATE_SOUTH.name(), BIRCH_FENCE_GATE_SOUTH.ordinal(), BIRCH_FENCE_GATE_SOUTH.getMinecraftId(), WoodTypeMat.BIRCH, face, open);
    }

    protected BirchFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, face, open);
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
    public BirchFenceGateMat getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGateMat.combine(face, this.open));
    }

    @Override
    public BirchFenceGateMat getOpen(final boolean open)
    {
        return getByID(FenceGateMat.combine(this.face, open));
    }

    @Override
    public BirchFenceGateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchFenceGateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BirchFenceGateMat getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGateMat.combine(face, open));
    }

    /**
     * Returns one of BirchFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchFenceGate or null
     */
    public static BirchFenceGateMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchFenceGate or null
     */
    public static BirchFenceGateMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BirchFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of BirchFenceGate
     */
    public static BirchFenceGateMat getBirchFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGateMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchFenceGateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public BirchFenceGateMat[] types()
    {
        return BirchFenceGateMat.birchFenceGateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BirchFenceGateMat[] birchFenceGateTypes()
    {
        return byID.values(new BirchFenceGateMat[byID.size()]);
    }

    static
    {
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_SOUTH);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_WEST);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_NORTH);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_EAST);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_SOUTH_OPEN);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_WEST_OPEN);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_NORTH_OPEN);
        BirchFenceGateMat.register(BIRCH_FENCE_GATE_EAST_OPEN);
    }
}
