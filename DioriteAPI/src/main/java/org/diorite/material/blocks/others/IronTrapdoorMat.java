package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronTrapdoor" and all its subtypes.
 */
public class IronTrapdoorMat extends TrapdoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_TRAPDOOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_TRAPDOOR__HARDNESS;

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_BOTTOM  = new IronTrapdoorMat();
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_BOTTOM = new IronTrapdoorMat(BlockFace.SOUTH, false, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_BOTTOM  = new IronTrapdoorMat(BlockFace.EAST, false, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_BOTTOM = new IronTrapdoorMat(BlockFace.NORTH, false, false);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_BOTTOM_OEPN  = new IronTrapdoorMat(BlockFace.WEST, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN = new IronTrapdoorMat(BlockFace.SOUTH, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_BOTTOM_OEPN  = new IronTrapdoorMat(BlockFace.EAST, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_BOTTOM_OEPN = new IronTrapdoorMat(BlockFace.NORTH, true, false);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_TOP  = new IronTrapdoorMat(BlockFace.WEST, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_TOP = new IronTrapdoorMat(BlockFace.SOUTH, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_TOP  = new IronTrapdoorMat(BlockFace.EAST, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_TOP = new IronTrapdoorMat(BlockFace.NORTH, false, true);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_TOP_OPEN  = new IronTrapdoorMat(BlockFace.WEST, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_TOP_OPEN = new IronTrapdoorMat(BlockFace.SOUTH, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_TOP_OPEN  = new IronTrapdoorMat(BlockFace.EAST, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_TOP_OPEN = new IronTrapdoorMat(BlockFace.NORTH, true, true);

    private static final Map<String, IronTrapdoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronTrapdoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronTrapdoorMat()
    {
        super("IRON_TRAPDOOR", 167, "minecraft:iron_trapdoor", BlockFace.WEST, false, false);
    }

    protected IronTrapdoorMat(final BlockFace face, final boolean open, final boolean onTop)
    {
        super(IRON_TRAPDOOR_WEST_BOTTOM.name(), IRON_TRAPDOOR_WEST_BOTTOM.ordinal(), IRON_TRAPDOOR_WEST_BOTTOM.getMinecraftId(), face, open, onTop);
    }

    protected IronTrapdoorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean open, final boolean onTop)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, open, onTop);
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
    public IronTrapdoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronTrapdoorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public IronTrapdoorMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.open, this.onTop));
    }

    @Override
    public IronTrapdoorMat getOpen(final boolean open)
    {
        return getByID(combine(this.face, open, this.onTop));
    }

    @Override
    public IronTrapdoorMat getOnTop(final boolean onTop)
    {
        return getByID(combine(this.face, this.open, onTop));
    }

    @Override
    public IronTrapdoorMat getType(final BlockFace face, final boolean open, final boolean onTop)
    {
        return getByID(combine(face, open, onTop));
    }

    @Override
    public IronTrapdoorMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.open, this.onTop));
    }

    /**
     * Returns one of IronTrapdoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronTrapdoor or null
     */
    public static IronTrapdoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronTrapdoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronTrapdoor or null
     */
    public static IronTrapdoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of IronTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param blockFace facing direction of trapdoor.
     * @param open      if trapdoor should be open.
     * @param onTop     if trapdoor should be on top of block.
     *
     * @return sub-type of IronTrapdoor
     */
    public static IronTrapdoorMat getIronTrapdoor(final BlockFace blockFace, final boolean open, final boolean onTop)
    {
        return getByID(combine(blockFace, open, onTop));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronTrapdoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public IronTrapdoorMat[] types()
    {
        return IronTrapdoorMat.ironTrapdoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronTrapdoorMat[] ironTrapdoorTypes()
    {
        return byID.values(new IronTrapdoorMat[byID.size()]);
    }

    static
    {
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_TOP_OPEN);
    }
}
