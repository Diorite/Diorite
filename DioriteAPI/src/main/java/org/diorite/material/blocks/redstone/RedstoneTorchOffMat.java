package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneTorchOff" and all its subtypes.
 */
public class RedstoneTorchOffMat extends RedstoneTorchMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_TORCH_OFF__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_TORCH_OFF__HARDNESS;

    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_WEST  = new RedstoneTorchOffMat();
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_EAST  = new RedstoneTorchOffMat(BlockFace.EAST);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_SOUTH = new RedstoneTorchOffMat(BlockFace.SOUTH);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_NORTH = new RedstoneTorchOffMat(BlockFace.NORTH);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_UP    = new RedstoneTorchOffMat(BlockFace.UP);

    private static final Map<String, RedstoneTorchOffMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOffMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOffMat()
    {
        super("REDSTONE_TORCH_OFF", 76, "minecraft:redstone_torch", BlockFace.WEST);
    }

    protected RedstoneTorchOffMat(final BlockFace face)
    {
        super(REDSTONE_TORCH_OFF_WEST.name(), REDSTONE_TORCH_OFF_WEST.getId(), REDSTONE_TORCH_OFF_WEST.getMinecraftId(), face);
    }

    protected RedstoneTorchOffMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face);
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
    public RedstoneTorchOffMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOffMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public RedstoneTorchOffMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public RedstoneTorchOffMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOffMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOffMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on facing direction.
     * It will never return null.
     *
     * @param face facing direction of RedstoneTorchOff.
     *
     * @return sub-type of RedstoneTorchOff
     */
    public static RedstoneTorchOffMat getRedstoneTorchOff(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOffMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_WEST);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_EAST);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_SOUTH);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_NORTH);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_UP);
    }
}
