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
 * Class representing block "AcaciaFenceGate" and all its subtypes.
 */
public class AcaciaFenceGate extends WoodenFenceGate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACACIA_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACACIA_FENCE_GATE__HARDNESS;

    public static final AcaciaFenceGate ACACIA_FENCE_GATE_SOUTH = new AcaciaFenceGate();
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_WEST  = new AcaciaFenceGate("WEST", BlockFace.WEST, false);
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_NORTH = new AcaciaFenceGate("NORTH", BlockFace.NORTH, false);
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_EAST  = new AcaciaFenceGate("EAST", BlockFace.EAST, false);

    public static final AcaciaFenceGate ACACIA_FENCE_GATE_SOUTH_OPEN = new AcaciaFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_WEST_OPEN  = new AcaciaFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_NORTH_OPEN = new AcaciaFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final AcaciaFenceGate ACACIA_FENCE_GATE_EAST_OPEN  = new AcaciaFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, AcaciaFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected AcaciaFenceGate()
    {
        super("ACACIA_FENCE_GATE", 187, "minecraft:acacia_fence_gate", "SOUTH", WoodType.ACACIA, BlockFace.SOUTH, false);
    }

    public AcaciaFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(ACACIA_FENCE_GATE_SOUTH.name(), ACACIA_FENCE_GATE_SOUTH.getId(), ACACIA_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.ACACIA, face, open);

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
    public AcaciaFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public AcaciaFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public AcaciaFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AcaciaFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaFenceGate or null
     */
    public static AcaciaFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of AcaciaFenceGate or null
     */
    public static AcaciaFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of AcaciaFenceGate
     */
    public static AcaciaFenceGate getAcaciaFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_SOUTH);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_WEST);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_NORTH);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_EAST);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_SOUTH_OPEN);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_WEST_OPEN);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_NORTH_OPEN);
        AcaciaFenceGate.register(ACACIA_FENCE_GATE_EAST_OPEN);
    }
}
