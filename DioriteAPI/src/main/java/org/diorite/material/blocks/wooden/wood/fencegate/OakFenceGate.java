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
 * Class representing block "OakFenceGate" and all its subtypes.
 */
public class OakFenceGate extends WoodenFenceGate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_FENCE_GATE__HARDNESS;

    public static final OakFenceGate OAK_FENCE_GATE_SOUTH = new OakFenceGate();
    public static final OakFenceGate OAK_FENCE_GATE_WEST  = new OakFenceGate("WEST", BlockFace.WEST, false);
    public static final OakFenceGate OAK_FENCE_GATE_NORTH = new OakFenceGate("NORTH", BlockFace.NORTH, false);
    public static final OakFenceGate OAK_FENCE_GATE_EAST  = new OakFenceGate("EAST", BlockFace.EAST, false);

    public static final OakFenceGate OAK_FENCE_GATE_SOUTH_OPEN = new OakFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final OakFenceGate OAK_FENCE_GATE_WEST_OPEN  = new OakFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final OakFenceGate OAK_FENCE_GATE_NORTH_OPEN = new OakFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final OakFenceGate OAK_FENCE_GATE_EAST_OPEN  = new OakFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, OakFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakFenceGate()
    {
        super("OAK_FENCE_GATE", 107, "minecraft:fence_gate", "SOUTH", WoodType.OAK, BlockFace.SOUTH, false);
    }

    public OakFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(OAK_FENCE_GATE_SOUTH.name(), OAK_FENCE_GATE_SOUTH.getId(), OAK_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.OAK, face, open);

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
    public OakFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public OakFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public OakFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public OakFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of OakFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakFenceGate or null
     */
    public static OakFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of OakFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of OakFenceGate or null
     */
    public static OakFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of OakFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of OakFenceGate
     */
    public static OakFenceGate getOakFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakFenceGate.register(OAK_FENCE_GATE_SOUTH);
        OakFenceGate.register(OAK_FENCE_GATE_WEST);
        OakFenceGate.register(OAK_FENCE_GATE_NORTH);
        OakFenceGate.register(OAK_FENCE_GATE_EAST);
        OakFenceGate.register(OAK_FENCE_GATE_SOUTH_OPEN);
        OakFenceGate.register(OAK_FENCE_GATE_WEST_OPEN);
        OakFenceGate.register(OAK_FENCE_GATE_NORTH_OPEN);
        OakFenceGate.register(OAK_FENCE_GATE_EAST_OPEN);
    }
}
