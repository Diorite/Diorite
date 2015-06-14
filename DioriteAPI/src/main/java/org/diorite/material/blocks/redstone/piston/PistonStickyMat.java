package org.diorite.material.blocks.redstone.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PistonSticky" and all its subtypes.
 */
public class PistonStickyMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STICKY_PISTON__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STICKY_PISTON__HARDNESS;

    public static final PistonStickyMat STICKY_PISTON_DOWN  = new PistonStickyMat();
    public static final PistonStickyMat STICKY_PISTON_UP    = new PistonStickyMat(BlockFace.UP, false);
    public static final PistonStickyMat STICKY_PISTON_NORTH = new PistonStickyMat(BlockFace.NORTH, false);
    public static final PistonStickyMat STICKY_PISTON_SOUTH = new PistonStickyMat(BlockFace.SOUTH, false);
    public static final PistonStickyMat STICKY_PISTON_WEST  = new PistonStickyMat(BlockFace.WEST, false);
    public static final PistonStickyMat STICKY_PISTON_EAST  = new PistonStickyMat(BlockFace.EAST, false);

    public static final PistonStickyMat STICKY_PISTON_DOWN_EXTENDED  = new PistonStickyMat(BlockFace.DOWN, true);
    public static final PistonStickyMat STICKY_PISTON_UP_EXTENDED    = new PistonStickyMat(BlockFace.UP, true);
    public static final PistonStickyMat STICKY_PISTON_NORTH_EXTENDED = new PistonStickyMat(BlockFace.NORTH, true);
    public static final PistonStickyMat STICKY_PISTON_SOUTH_EXTENDED = new PistonStickyMat(BlockFace.SOUTH, true);
    public static final PistonStickyMat STICKY_PISTON_WEST_EXTENDED  = new PistonStickyMat(BlockFace.WEST, true);
    public static final PistonStickyMat STICKY_PISTON_EAST_EXTENDED  = new PistonStickyMat(BlockFace.EAST, true);

    private static final Map<String, PistonStickyMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonStickyMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PistonStickyMat()
    {
        super("STICKY_PISTON", 29, "minecraft:sticky_piston", BlockFace.DOWN, false);
    }

    protected PistonStickyMat(final BlockFace face, final boolean extended)
    {
        super(STICKY_PISTON_DOWN.name(), STICKY_PISTON_DOWN.ordinal(), STICKY_PISTON_DOWN.getMinecraftId(), face, extended);
    }

    protected PistonStickyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended);
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
    public PistonStickyMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonStickyMat getExtended(final boolean extended)
    {
        return getPistonSticky(this.facing, extended);
    }

    @Override
    public PistonStickyMat getType(final BlockFace face, final boolean extended)
    {
        return getPistonSticky(face, extended);
    }

    @Override
    public PistonStickyMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonStickyMat getBlockFacing(final BlockFace face)
    {
        return getPistonSticky(face, this.extended);
    }

    @Override
    public PistonStickyMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of PistonSticky sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonSticky or null
     */
    public static PistonStickyMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonSticky sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonSticky or null
     */
    public static PistonStickyMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonSticky sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonSticky
     */
    public static PistonStickyMat getPistonSticky(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonStickyMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public PistonStickyMat[] types()
    {
        return PistonStickyMat.pistonStickyTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonStickyMat[] pistonStickyTypes()
    {
        return byID.values(new PistonStickyMat[byID.size()]);
    }

    static
    {
        PistonStickyMat.register(STICKY_PISTON_DOWN);
        PistonStickyMat.register(STICKY_PISTON_UP);
        PistonStickyMat.register(STICKY_PISTON_NORTH);
        PistonStickyMat.register(STICKY_PISTON_SOUTH);
        PistonStickyMat.register(STICKY_PISTON_WEST);
        PistonStickyMat.register(STICKY_PISTON_EAST);
        PistonStickyMat.register(STICKY_PISTON_DOWN_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_UP_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_NORTH_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_SOUTH_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_WEST_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_EAST_EXTENDED);
    }
}
