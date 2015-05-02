package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.FenceGateMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DarkOakFenceGate" and all its subtypes.
 */
public class DarkOakFenceGateMat extends WoodenFenceGateMat
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

    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_SOUTH = new DarkOakFenceGateMat();
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_WEST  = new DarkOakFenceGateMat(BlockFace.WEST, false);
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_NORTH = new DarkOakFenceGateMat(BlockFace.NORTH, false);
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_EAST  = new DarkOakFenceGateMat(BlockFace.EAST, false);

    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_SOUTH_OPEN = new DarkOakFenceGateMat(BlockFace.SOUTH, true);
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_WEST_OPEN  = new DarkOakFenceGateMat(BlockFace.WEST, true);
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_NORTH_OPEN = new DarkOakFenceGateMat(BlockFace.NORTH, true);
    public static final DarkOakFenceGateMat DARK_OAK_FENCE_GATE_EAST_OPEN  = new DarkOakFenceGateMat(BlockFace.EAST, true);

    private static final Map<String, DarkOakFenceGateMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakFenceGateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DarkOakFenceGateMat()
    {
        super("DARK_OAK_FENCE_GATE", 186, "minecraft:fark_oak_fence_gate", WoodTypeMat.DARK_OAK, BlockFace.SOUTH, false);
    }

    protected DarkOakFenceGateMat(final BlockFace face, final boolean open)
    {
        super(DARK_OAK_FENCE_GATE_SOUTH.name(), DARK_OAK_FENCE_GATE_SOUTH.getId(), DARK_OAK_FENCE_GATE_SOUTH.getMinecraftId(), WoodTypeMat.DARK_OAK, face, open);
    }

    protected DarkOakFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open)
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
    public DarkOakFenceGateMat getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGateMat.combine(face, this.open));
    }

    @Override
    public DarkOakFenceGateMat getOpen(final boolean open)
    {
        return getByID(FenceGateMat.combine(this.face, open));
    }

    @Override
    public DarkOakFenceGateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakFenceGateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DarkOakFenceGateMat getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGateMat.combine(face, open));
    }

    /**
     * Returns one of DarkOakFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakFenceGate or null
     */
    public static DarkOakFenceGateMat getByID(final int id)
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
    public static DarkOakFenceGateMat getByEnumName(final String name)
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
    public static DarkOakFenceGateMat getDarkOakFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGateMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakFenceGateMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_SOUTH);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_WEST);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_NORTH);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_EAST);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_SOUTH_OPEN);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_WEST_OPEN);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_NORTH_OPEN);
        DarkOakFenceGateMat.register(DARK_OAK_FENCE_GATE_EAST_OPEN);
    }
}
