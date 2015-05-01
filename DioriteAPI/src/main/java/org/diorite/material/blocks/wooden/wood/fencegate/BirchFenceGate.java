package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.FenceGate;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchFenceGate" and all its subtypes.
 */
public class BirchFenceGate extends WoodenFenceGate
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

    public static final BirchFenceGate BIRCH_FENCE_GATE_SOUTH = new BirchFenceGate();
    public static final BirchFenceGate BIRCH_FENCE_GATE_WEST  = new BirchFenceGate("WEST", BlockFace.WEST, false);
    public static final BirchFenceGate BIRCH_FENCE_GATE_NORTH = new BirchFenceGate("NORTH", BlockFace.NORTH, false);
    public static final BirchFenceGate BIRCH_FENCE_GATE_EAST  = new BirchFenceGate("EAST", BlockFace.EAST, false);

    public static final BirchFenceGate BIRCH_FENCE_GATE_SOUTH_OPEN = new BirchFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final BirchFenceGate BIRCH_FENCE_GATE_WEST_OPEN  = new BirchFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final BirchFenceGate BIRCH_FENCE_GATE_NORTH_OPEN = new BirchFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final BirchFenceGate BIRCH_FENCE_GATE_EAST_OPEN  = new BirchFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, BirchFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchFenceGate()
    {
        super("BIRCH_FENCE_GATE", 184, "minecraft:brich_fence_gate", "SOUTH", WoodType.BIRCH, BlockFace.SOUTH, false);
    }

    public BirchFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(BIRCH_FENCE_GATE_SOUTH.name(), BIRCH_FENCE_GATE_SOUTH.getId(), BIRCH_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.BIRCH, face, open);

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
    public BirchFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public BirchFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public BirchFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BirchFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of BirchFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchFenceGate or null
     */
    public static BirchFenceGate getByID(final int id)
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
    public static BirchFenceGate getByEnumName(final String name)
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
    public static BirchFenceGate getBirchFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchFenceGate.register(BIRCH_FENCE_GATE_SOUTH);
        BirchFenceGate.register(BIRCH_FENCE_GATE_WEST);
        BirchFenceGate.register(BIRCH_FENCE_GATE_NORTH);
        BirchFenceGate.register(BIRCH_FENCE_GATE_EAST);
        BirchFenceGate.register(BIRCH_FENCE_GATE_SOUTH_OPEN);
        BirchFenceGate.register(BIRCH_FENCE_GATE_WEST_OPEN);
        BirchFenceGate.register(BIRCH_FENCE_GATE_NORTH_OPEN);
        BirchFenceGate.register(BIRCH_FENCE_GATE_EAST_OPEN);
    }
}
