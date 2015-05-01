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
 * Class representing block "DarkOakFenceGate" and all its subtypes.
 */
public class DarkOakFenceGate extends WoodenFenceGate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 8;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DARK_OAK_FENCE_GATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DARK_OAK_FENCE_GATE__HARDNESS;

    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_SOUTH = new DarkOakFenceGate();
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_WEST  = new DarkOakFenceGate("WEST", BlockFace.WEST, false);
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_NORTH = new DarkOakFenceGate("NORTH", BlockFace.NORTH, false);
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_EAST  = new DarkOakFenceGate("EAST", BlockFace.EAST, false);

    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_SOUTH_OPEN = new DarkOakFenceGate("SOUTH_OPEN", BlockFace.SOUTH, true);
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_WEST_OPEN  = new DarkOakFenceGate("WEST_OPEN", BlockFace.WEST, true);
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_NORTH_OPEN = new DarkOakFenceGate("NORTH_OPEN", BlockFace.NORTH, true);
    public static final DarkOakFenceGate DARK_OAK_FENCE_GATE_EAST_OPEN  = new DarkOakFenceGate("EAST_OPEN", BlockFace.EAST, true);

    private static final Map<String, DarkOakFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakFenceGate()
    {
        super("DARK_OAK_FENCE_GATE", 186, "minecraft:fark_oak_fence_gate", "SOUTH", WoodType.DARK_OAK, BlockFace.SOUTH, false);
    }

    public DarkOakFenceGate(final String enumName, final BlockFace face, final boolean open)
    {
        super(DARK_OAK_FENCE_GATE_SOUTH.name(), DARK_OAK_FENCE_GATE_SOUTH.getId(), DARK_OAK_FENCE_GATE_SOUTH.getMinecraftId(), enumName, WoodType.DARK_OAK, face, open);

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
    public DarkOakFenceGate getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGate.combine(face, this.open));
    }

    @Override
    public DarkOakFenceGate getOpen(final boolean open)
    {
        return getByID(FenceGate.combine(this.face, open));
    }

    @Override
    public DarkOakFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakFenceGate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DarkOakFenceGate getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGate.combine(face, open));
    }

    /**
     * Returns one of DarkOakFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakFenceGate or null
     */
    public static DarkOakFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DarkOakFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DarkOakFenceGate or null
     */
    public static DarkOakFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DarkOakFenceGate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of DarkOakFenceGate
     */
    public static DarkOakFenceGate getDarkOakFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGate.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_SOUTH);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_WEST);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_NORTH);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_EAST);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_SOUTH_OPEN);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_WEST_OPEN);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_NORTH_OPEN);
        DarkOakFenceGate.register(DARK_OAK_FENCE_GATE_EAST_OPEN);
    }
}
