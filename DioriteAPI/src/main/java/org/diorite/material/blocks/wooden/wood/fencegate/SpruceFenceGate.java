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
 * Class representing block "SpruceFenceGate" and all its subtypes.
 */
public class SpruceFenceGate extends WoodenFenceGate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPRUCE_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPRUCE_FENCE_GATE__HARDNESS;

    public static final SpruceFenceGate SPRUCE_FENCE_GATE_SOUTH = new SpruceFenceGate();
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_WEST  = new SpruceFenceGate("WEST", BlockFace.WEST, false);
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_NORTH = new SpruceFenceGate("NORTH", BlockFace.NORTH, false);
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_EAST  = new SpruceFenceGate("EAST", BlockFace.EAST, false);

    public static final SpruceFenceGate SPRUCE_FENCE_GATE_SOUTH_OPEN = new SpruceFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_WEST_OPEN  = new SpruceFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_NORTH_OPEN = new SpruceFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final SpruceFenceGate SPRUCE_FENCE_GATE_EAST_OPEN  = new SpruceFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, SpruceFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceFenceGate()
    {
        super("SPRUCE_FENCE_GATE", 187, "minecraft:acacia_fence_gate", "SOUTH", WoodType.SPRUCE, BlockFace.SOUTH, false);
    }

    public SpruceFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(SPRUCE_FENCE_GATE_SOUTH.name(), SPRUCE_FENCE_GATE_SOUTH.getId(), SPRUCE_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.SPRUCE, face, open);

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
    public SpruceFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public SpruceFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public SpruceFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public SpruceFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceFenceGate or null
     */
    public static SpruceFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpruceFenceGate or null
     */
    public static SpruceFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SpruceFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of SpruceFenceGate
     */
    public static SpruceFenceGate getSpruceFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_SOUTH);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_WEST);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_NORTH);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_EAST);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_SOUTH_OPEN);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_WEST_OPEN);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_NORTH_OPEN);
        SpruceFenceGate.register(SPRUCE_FENCE_GATE_EAST_OPEN);
    }
}
