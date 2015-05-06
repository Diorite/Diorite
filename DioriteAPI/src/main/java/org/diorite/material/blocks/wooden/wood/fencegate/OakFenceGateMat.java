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
 * Class representing block "OakFenceGate" and all its subtypes.
 */
public class OakFenceGateMat extends WoodenFenceGateMat
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

    public static final OakFenceGateMat OAK_FENCE_GATE_SOUTH = new OakFenceGateMat();
    public static final OakFenceGateMat OAK_FENCE_GATE_WEST  = new OakFenceGateMat(BlockFace.WEST, false);
    public static final OakFenceGateMat OAK_FENCE_GATE_NORTH = new OakFenceGateMat(BlockFace.NORTH, false);
    public static final OakFenceGateMat OAK_FENCE_GATE_EAST  = new OakFenceGateMat(BlockFace.EAST, false);

    public static final OakFenceGateMat OAK_FENCE_GATE_SOUTH_OPEN = new OakFenceGateMat(BlockFace.SOUTH, true);
    public static final OakFenceGateMat OAK_FENCE_GATE_WEST_OPEN  = new OakFenceGateMat(BlockFace.WEST, true);
    public static final OakFenceGateMat OAK_FENCE_GATE_NORTH_OPEN = new OakFenceGateMat(BlockFace.NORTH, true);
    public static final OakFenceGateMat OAK_FENCE_GATE_EAST_OPEN  = new OakFenceGateMat(BlockFace.EAST, true);

    private static final Map<String, OakFenceGateMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFenceGateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakFenceGateMat()
    {
        super("OAK_FENCE_GATE", 107, "minecraft:fence_gate", WoodTypeMat.OAK, BlockFace.SOUTH, false);
    }

    protected OakFenceGateMat(final BlockFace face, final boolean open)
    {
        super(OAK_FENCE_GATE_SOUTH.name(), OAK_FENCE_GATE_SOUTH.getId(), OAK_FENCE_GATE_SOUTH.getMinecraftId(), WoodTypeMat.OAK, face, open);
    }

    protected OakFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open)
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
    public OakFenceGateMat getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGateMat.combine(face, this.open));
    }

    @Override
    public OakFenceGateMat getOpen(final boolean open)
    {
        return getByID(FenceGateMat.combine(this.face, open));
    }

    @Override
    public OakFenceGateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFenceGateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public OakFenceGateMat getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGateMat.combine(face, open));
    }

    /**
     * Returns one of OakFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakFenceGate or null
     */
    public static OakFenceGateMat getByID(final int id)
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
    public static OakFenceGateMat getByEnumName(final String name)
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
    public static OakFenceGateMat getOakFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGateMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakFenceGateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakFenceGateMat.register(OAK_FENCE_GATE_SOUTH);
        OakFenceGateMat.register(OAK_FENCE_GATE_WEST);
        OakFenceGateMat.register(OAK_FENCE_GATE_NORTH);
        OakFenceGateMat.register(OAK_FENCE_GATE_EAST);
        OakFenceGateMat.register(OAK_FENCE_GATE_SOUTH_OPEN);
        OakFenceGateMat.register(OAK_FENCE_GATE_WEST_OPEN);
        OakFenceGateMat.register(OAK_FENCE_GATE_NORTH_OPEN);
        OakFenceGateMat.register(OAK_FENCE_GATE_EAST_OPEN);
    }
}
