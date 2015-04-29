package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneTorchOn" and all its subtypes.
 */
public class RedstoneTorchOn extends RedstoneTorch
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_TORCH_ON__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_TORCH_ON__HARDNESS;

    public static final RedstoneTorchOn REDSTONE_TORCH_ON_WEST  = new RedstoneTorchOn();
    public static final RedstoneTorchOn REDSTONE_TORCH_ON_EAST  = new RedstoneTorchOn(BlockFace.EAST);
    public static final RedstoneTorchOn REDSTONE_TORCH_ON_SOUTH = new RedstoneTorchOn(BlockFace.SOUTH);
    public static final RedstoneTorchOn REDSTONE_TORCH_ON_NORTH = new RedstoneTorchOn(BlockFace.NORTH);
    public static final RedstoneTorchOn REDSTONE_TORCH_ON_UP    = new RedstoneTorchOn(BlockFace.UP);

    private static final Map<String, RedstoneTorchOn>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOn> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOn()
    {
        super("REDSTONE_TORCH_ON", 76, "minecraft:redstone_torch", BlockFace.WEST);
    }

    public RedstoneTorchOn(final BlockFace face)
    {
        super(REDSTONE_TORCH_ON_WEST.name(), REDSTONE_TORCH_ON_WEST.getId(), REDSTONE_TORCH_ON_WEST.getMinecraftId(), face);
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
    public RedstoneTorchOn getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOn getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return true;
    }

    @Override
    public RedstoneTorchOn getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on facing direction.
     * It will never return null.
     *
     * @param face facing direction of RedstoneTorchOn.
     *
     * @return sub-type of RedstoneTorchOn
     */
    public static RedstoneTorchOn getRedstoneTorchOn(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOn element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneTorchOn.register(REDSTONE_TORCH_ON_WEST);
        RedstoneTorchOn.register(REDSTONE_TORCH_ON_EAST);
        RedstoneTorchOn.register(REDSTONE_TORCH_ON_SOUTH);
        RedstoneTorchOn.register(REDSTONE_TORCH_ON_NORTH);
        RedstoneTorchOn.register(REDSTONE_TORCH_ON_UP);
    }
}
