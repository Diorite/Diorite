package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneTorchOn" and all its subtypes.
 */
public class RedstoneTorchOnMat extends RedstoneTorchMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 5;

    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_WEST  = new RedstoneTorchOnMat();
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_EAST  = new RedstoneTorchOnMat(BlockFace.EAST);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_SOUTH = new RedstoneTorchOnMat(BlockFace.SOUTH);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_NORTH = new RedstoneTorchOnMat(BlockFace.NORTH);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_UP    = new RedstoneTorchOnMat(BlockFace.UP);

    private static final Map<String, RedstoneTorchOnMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOnMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOnMat()
    {
        super("REDSTONE_TORCH_ON", 76, "minecraft:redstone_torch", BlockFace.WEST, 0, 0);
    }

    protected RedstoneTorchOnMat(final BlockFace face)
    {
        super(REDSTONE_TORCH_ON_WEST.name(), REDSTONE_TORCH_ON_WEST.ordinal(), REDSTONE_TORCH_ON_WEST.getMinecraftId(), face, REDSTONE_TORCH_ON_WEST.getHardness(), REDSTONE_TORCH_ON_WEST.getBlastResistance());
    }

    protected RedstoneTorchOnMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public RedstoneTorchOnMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOnMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    @Override
    public RedstoneTorchOnMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public RedstoneTorchOnMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOnMat getByID(final int id)
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
    public static RedstoneTorchOnMat getByEnumName(final String name)
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
    public static RedstoneTorchOnMat getRedstoneTorchOn(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOnMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneTorchOnMat[] types()
    {
        return RedstoneTorchOnMat.redstoneTorchOnTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneTorchOnMat[] redstoneTorchOnTypes()
    {
        return byID.values(new RedstoneTorchOnMat[byID.size()]);
    }

    static
    {
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_WEST);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_EAST);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_SOUTH);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_NORTH);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_UP);
    }
}
