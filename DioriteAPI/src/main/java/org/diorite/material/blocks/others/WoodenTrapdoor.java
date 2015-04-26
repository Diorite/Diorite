package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenTrapdoor" and all its subtypes.
 */
public class WoodenTrapdoor extends Trapdoor
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_TRAPDOOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_TRAPDOOR__HARDNESS;

    public static final WoodenTrapdoor WOODEN_TRAPDOOR_WEST_BOTTOM  = new WoodenTrapdoor();
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_SOUTH_BOTTOM = new WoodenTrapdoor(BlockFace.SOUTH, false, false);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_EAST_BOTTOM  = new WoodenTrapdoor(BlockFace.EAST, false, false);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_NORTH_BOTTOM = new WoodenTrapdoor(BlockFace.NORTH, false, false);

    public static final WoodenTrapdoor WOODEN_TRAPDOOR_WEST_BOTTOM_OEPN  = new WoodenTrapdoor(BlockFace.WEST, true, false);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_SOUTH_BOTTOM_OEPN = new WoodenTrapdoor(BlockFace.SOUTH, true, false);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_EAST_BOTTOM_OEPN  = new WoodenTrapdoor(BlockFace.EAST, true, false);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_NORTH_BOTTOM_OEPN = new WoodenTrapdoor(BlockFace.NORTH, true, false);

    public static final WoodenTrapdoor WOODEN_TRAPDOOR_WEST_TOP  = new WoodenTrapdoor(BlockFace.WEST, false, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_SOUTH_TOP = new WoodenTrapdoor(BlockFace.SOUTH, false, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_EAST_TOP  = new WoodenTrapdoor(BlockFace.EAST, false, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_NORTH_TOP = new WoodenTrapdoor(BlockFace.NORTH, false, true);

    public static final WoodenTrapdoor WOODEN_TRAPDOOR_WEST_TOP_OPEN  = new WoodenTrapdoor(BlockFace.WEST, true, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_SOUTH_TOP_OPEN = new WoodenTrapdoor(BlockFace.SOUTH, true, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_EAST_TOP_OPEN  = new WoodenTrapdoor(BlockFace.EAST, true, true);
    public static final WoodenTrapdoor WOODEN_TRAPDOOR_NORTH_TOP_OPEN = new WoodenTrapdoor(BlockFace.NORTH, true, true);

    private static final Map<String, WoodenTrapdoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenTrapdoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenTrapdoor()
    {
        super("WOODEN_TRAPDOOR", 96, "minecraft:trapdoor", BlockFace.WEST, false, false);
    }

    public WoodenTrapdoor(final BlockFace face, final boolean open, final boolean onTop)
    {
        super(WOODEN_TRAPDOOR_WEST_BOTTOM.name(), WOODEN_TRAPDOOR_WEST_BOTTOM.getId(), WOODEN_TRAPDOOR_WEST_BOTTOM.getMinecraftId(), face, open, onTop);
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
    public WoodenTrapdoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenTrapdoor getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenTrapdoor getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.open, this.onTop));
    }

    @Override
    public WoodenTrapdoor getOpen(final boolean open)
    {
        return getByID(combine(this.face, open, this.onTop));
    }

    @Override
    public WoodenTrapdoor getOnTop(final boolean onTop)
    {
        return getByID(combine(this.face, this.open, onTop));
    }

    @Override
    public WoodenTrapdoor getType(final BlockFace face, final boolean open, final boolean onTop)
    {
        return getByID(combine(face, open, onTop));
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenTrapdoor or null
     */
    public static WoodenTrapdoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenTrapdoor or null
     */
    public static WoodenTrapdoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param blockFace facing direction of trapdoor.
     * @param open      if trapdoor should be open.
     * @param onTop     if trapdoor should be on top of block.
     *
     * @return sub-type of WoodenTrapdoor
     */
    public static WoodenTrapdoor getWoodenTrapdoor(final BlockFace blockFace, final boolean open, final boolean onTop)
    {
        return getByID(combine(blockFace, open, onTop));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenTrapdoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_WEST_BOTTOM);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_SOUTH_BOTTOM);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_EAST_BOTTOM);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_NORTH_BOTTOM);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_WEST_BOTTOM_OEPN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_SOUTH_BOTTOM_OEPN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_EAST_BOTTOM_OEPN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_NORTH_BOTTOM_OEPN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_WEST_TOP);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_SOUTH_TOP);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_EAST_TOP);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_NORTH_TOP);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_WEST_TOP_OPEN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_SOUTH_TOP_OPEN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_EAST_TOP_OPEN);
        WoodenTrapdoor.register(WOODEN_TRAPDOOR_NORTH_TOP_OPEN);
    }
}
