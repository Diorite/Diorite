package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronTrapdoor" and all its subtypes.
 */
public class IronTrapdoor extends Trapdoor
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

    public static final IronTrapdoor IRON_TRAPDOOR_WEST_BOTTOM  = new IronTrapdoor();
    public static final IronTrapdoor IRON_TRAPDOOR_SOUTH_BOTTOM = new IronTrapdoor(BlockFace.SOUTH, false, false);
    public static final IronTrapdoor IRON_TRAPDOOR_EAST_BOTTOM  = new IronTrapdoor(BlockFace.EAST, false, false);
    public static final IronTrapdoor IRON_TRAPDOOR_NORTH_BOTTOM = new IronTrapdoor(BlockFace.NORTH, false, false);

    public static final IronTrapdoor IRON_TRAPDOOR_WEST_BOTTOM_OEPN  = new IronTrapdoor(BlockFace.WEST, true, false);
    public static final IronTrapdoor IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN = new IronTrapdoor(BlockFace.SOUTH, true, false);
    public static final IronTrapdoor IRON_TRAPDOOR_EAST_BOTTOM_OEPN  = new IronTrapdoor(BlockFace.EAST, true, false);
    public static final IronTrapdoor IRON_TRAPDOOR_NORTH_BOTTOM_OEPN = new IronTrapdoor(BlockFace.NORTH, true, false);

    public static final IronTrapdoor IRON_TRAPDOOR_WEST_TOP  = new IronTrapdoor(BlockFace.WEST, false, true);
    public static final IronTrapdoor IRON_TRAPDOOR_SOUTH_TOP = new IronTrapdoor(BlockFace.SOUTH, false, true);
    public static final IronTrapdoor IRON_TRAPDOOR_EAST_TOP  = new IronTrapdoor(BlockFace.EAST, false, true);
    public static final IronTrapdoor IRON_TRAPDOOR_NORTH_TOP = new IronTrapdoor(BlockFace.NORTH, false, true);

    public static final IronTrapdoor IRON_TRAPDOOR_WEST_TOP_OPEN  = new IronTrapdoor(BlockFace.WEST, true, true);
    public static final IronTrapdoor IRON_TRAPDOOR_SOUTH_TOP_OPEN = new IronTrapdoor(BlockFace.SOUTH, true, true);
    public static final IronTrapdoor IRON_TRAPDOOR_EAST_TOP_OPEN  = new IronTrapdoor(BlockFace.EAST, true, true);
    public static final IronTrapdoor IRON_TRAPDOOR_NORTH_TOP_OPEN = new IronTrapdoor(BlockFace.NORTH, true, true);

    private static final Map<String, IronTrapdoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronTrapdoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronTrapdoor()
    {
        super("IRON_TRAPDOOR", 167, "minecraft:iron_trapdoor", BlockFace.WEST, false, false);
    }

    public IronTrapdoor(final BlockFace face, final boolean open, final boolean onTop)
    {
        super(IRON_TRAPDOOR_WEST_BOTTOM.name(), IRON_TRAPDOOR_WEST_BOTTOM.getId(), IRON_TRAPDOOR_WEST_BOTTOM.getMinecraftId(), face, open, onTop);
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
    public IronTrapdoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronTrapdoor getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public IronTrapdoor getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.open, this.onTop));
    }

    @Override
    public IronTrapdoor getOpen(final boolean open)
    {
        return getByID(combine(this.face, open, this.onTop));
    }

    @Override
    public IronTrapdoor getOnTop(final boolean onTop)
    {
        return getByID(combine(this.face, this.open, onTop));
    }

    @Override
    public IronTrapdoor getType(final BlockFace face, final boolean open, final boolean onTop)
    {
        return getByID(combine(face, open, onTop));
    }

    /**
     * Returns one of IronTrapdoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronTrapdoor or null
     */
    public static IronTrapdoor getByID(final int id)
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
    public static IronTrapdoor getByEnumName(final String name)
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
    public static IronTrapdoor getIronTrapdoor(final BlockFace blockFace, final boolean open, final boolean onTop)
    {
        return getByID(combine(blockFace, open, onTop));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronTrapdoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronTrapdoor.register(IRON_TRAPDOOR_WEST_BOTTOM);
        IronTrapdoor.register(IRON_TRAPDOOR_SOUTH_BOTTOM);
        IronTrapdoor.register(IRON_TRAPDOOR_EAST_BOTTOM);
        IronTrapdoor.register(IRON_TRAPDOOR_NORTH_BOTTOM);
        IronTrapdoor.register(IRON_TRAPDOOR_WEST_BOTTOM_OEPN);
        IronTrapdoor.register(IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN);
        IronTrapdoor.register(IRON_TRAPDOOR_EAST_BOTTOM_OEPN);
        IronTrapdoor.register(IRON_TRAPDOOR_NORTH_BOTTOM_OEPN);
        IronTrapdoor.register(IRON_TRAPDOOR_WEST_TOP);
        IronTrapdoor.register(IRON_TRAPDOOR_SOUTH_TOP);
        IronTrapdoor.register(IRON_TRAPDOOR_EAST_TOP);
        IronTrapdoor.register(IRON_TRAPDOOR_NORTH_TOP);
        IronTrapdoor.register(IRON_TRAPDOOR_WEST_TOP_OPEN);
        IronTrapdoor.register(IRON_TRAPDOOR_SOUTH_TOP_OPEN);
        IronTrapdoor.register(IRON_TRAPDOOR_EAST_TOP_OPEN);
        IronTrapdoor.register(IRON_TRAPDOOR_NORTH_TOP_OPEN);
    }
}
