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
 * Class representing block "AcaciaFenceGate" and all its subtypes.
 */
public class AcaciaFenceGateMat extends WoodenFenceGateMat
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

    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_SOUTH = new AcaciaFenceGateMat();
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_WEST  = new AcaciaFenceGateMat(BlockFace.WEST, false);
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_NORTH = new AcaciaFenceGateMat(BlockFace.NORTH, false);
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_EAST  = new AcaciaFenceGateMat(BlockFace.EAST, false);

    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_SOUTH_OPEN = new AcaciaFenceGateMat(BlockFace.SOUTH, true);
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_WEST_OPEN  = new AcaciaFenceGateMat(BlockFace.WEST, true);
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_NORTH_OPEN = new AcaciaFenceGateMat(BlockFace.NORTH, true);
    public static final AcaciaFenceGateMat ACACIA_FENCE_GATE_EAST_OPEN  = new AcaciaFenceGateMat(BlockFace.EAST, true);

    private static final Map<String, AcaciaFenceGateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaFenceGateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected AcaciaFenceGateMat()
    {
        super("ACACIA_FENCE_GATE", 187, "minecraft:acacia_fence_gate", WoodTypeMat.ACACIA, BlockFace.SOUTH, false);
    }

    protected AcaciaFenceGateMat(final BlockFace face, final boolean open)
    {
        super(ACACIA_FENCE_GATE_SOUTH.name(), ACACIA_FENCE_GATE_SOUTH.ordinal(), ACACIA_FENCE_GATE_SOUTH.getMinecraftId(), WoodTypeMat.ACACIA, face, open);
    }

    protected AcaciaFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open)
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
    public AcaciaFenceGateMat getBlockFacing(final BlockFace face)
    {
        return getByID(FenceGateMat.combine(face, this.open));
    }

    @Override
    public AcaciaFenceGateMat getOpen(final boolean open)
    {
        return getByID(FenceGateMat.combine(this.face, open));
    }

    @Override
    public AcaciaFenceGateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaFenceGateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AcaciaFenceGateMat getType(final BlockFace face, final boolean open)
    {
        return getByID(FenceGateMat.combine(face, open));
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaFenceGate or null
     */
    public static AcaciaFenceGateMat getByID(final int id)
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
    public static AcaciaFenceGateMat getByEnumName(final String name)
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
    public static AcaciaFenceGateMat getAcaciaFenceGate(final BlockFace blockFace, final boolean open)
    {
        return getByID(FenceGateMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaFenceGateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public AcaciaFenceGateMat[] types()
    {
        return AcaciaFenceGateMat.acaciaFenceGateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AcaciaFenceGateMat[] acaciaFenceGateTypes()
    {
        return byID.values(new AcaciaFenceGateMat[byID.size()]);
    }

    static
    {
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_SOUTH);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_WEST);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_NORTH);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_EAST);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_SOUTH_OPEN);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_WEST_OPEN);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_NORTH_OPEN);
        AcaciaFenceGateMat.register(ACACIA_FENCE_GATE_EAST_OPEN);
    }
}
