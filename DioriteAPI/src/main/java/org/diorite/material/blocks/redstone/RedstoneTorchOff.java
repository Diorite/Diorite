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
public class RedstoneTorchOff extends RedstoneTorch
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

    public static final RedstoneTorchOff REDSTONE_TORCH_OFF_WEST  = new RedstoneTorchOff();
    public static final RedstoneTorchOff REDSTONE_TORCH_OFF_EAST  = new RedstoneTorchOff(BlockFace.EAST);
    public static final RedstoneTorchOff REDSTONE_TORCH_OFF_SOUTH = new RedstoneTorchOff(BlockFace.SOUTH);
    public static final RedstoneTorchOff REDSTONE_TORCH_OFF_NORTH = new RedstoneTorchOff(BlockFace.NORTH);
    public static final RedstoneTorchOff REDSTONE_TORCH_OFF_UP    = new RedstoneTorchOff(BlockFace.UP);

    private static final Map<String, RedstoneTorchOff>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOff> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOff()
    {
        super("REDSTONE_TORCH_OFF", 76, "minecraft:redstone_torch", BlockFace.WEST);
    }

    public RedstoneTorchOff(final BlockFace face)
    {
        super(REDSTONE_TORCH_OFF_WEST.name(), REDSTONE_TORCH_OFF_WEST.getId(), REDSTONE_TORCH_OFF_WEST.getMinecraftId(), face);
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
    public RedstoneTorchOff getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOff getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false;
    }

    @Override
    public RedstoneTorchOff getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOff getByID(final int id)
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
    public static RedstoneTorchOff getByEnumName(final String name)
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
    public static RedstoneTorchOff getRedstoneTorchOff(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOff element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF_WEST);
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF_EAST);
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF_SOUTH);
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF_NORTH);
        RedstoneTorchOff.register(REDSTONE_TORCH_OFF_UP);
    }
}
