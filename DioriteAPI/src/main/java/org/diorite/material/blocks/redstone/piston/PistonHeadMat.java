package org.diorite.material.blocks.redstone.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PistonHead" and all its subtypes.
 */
public class PistonHeadMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PISTON_HEAD__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PISTON_HEAD__HARDNESS;

    public static final PistonHeadMat PISTON_HEAD_DOWN  = new PistonHeadMat();
    public static final PistonHeadMat PISTON_HEAD_UP    = new PistonHeadMat(BlockFace.UP, false);
    public static final PistonHeadMat PISTON_HEAD_NORTH = new PistonHeadMat(BlockFace.NORTH, false);
    public static final PistonHeadMat PISTON_HEAD_SOUTH = new PistonHeadMat(BlockFace.SOUTH, false);
    public static final PistonHeadMat PISTON_HEAD_WEST  = new PistonHeadMat(BlockFace.WEST, false);
    public static final PistonHeadMat PISTON_HEAD_EAST  = new PistonHeadMat(BlockFace.EAST, false);

    public static final PistonHeadMat PISTON_HEAD_DOWN_EXTENDED  = new PistonHeadMat(BlockFace.DOWN, true);
    public static final PistonHeadMat PISTON_HEAD_UP_EXTENDED    = new PistonHeadMat(BlockFace.UP, true);
    public static final PistonHeadMat PISTON_HEAD_NORTH_EXTENDED = new PistonHeadMat(BlockFace.NORTH, true);
    public static final PistonHeadMat PISTON_HEAD_SOUTH_EXTENDED = new PistonHeadMat(BlockFace.SOUTH, true);
    public static final PistonHeadMat PISTON_HEAD_WEST_EXTENDED  = new PistonHeadMat(BlockFace.WEST, true);
    public static final PistonHeadMat PISTON_HEAD_EAST_EXTENDED  = new PistonHeadMat(BlockFace.EAST, true);

    private static final Map<String, PistonHeadMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonHeadMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PistonHeadMat()
    {
        super("PISTON_HEAD", 34, "minecraft:piston_head", BlockFace.DOWN, false);
    }

    protected PistonHeadMat(final BlockFace face, final boolean extended)
    {
        super(PISTON_HEAD_DOWN.name(), PISTON_HEAD_DOWN.getId(), PISTON_HEAD_DOWN.getMinecraftId(), face, extended);
    }

    protected PistonHeadMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended)
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
    public PistonHeadMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonHeadMat getExtended(final boolean extended)
    {
        return getPistonHead(this.facing, extended);
    }

    @Override
    public PistonHeadMat getType(final BlockFace face, final boolean extended)
    {
        return getPistonHead(face, extended);
    }

    @Override
    public PistonHeadMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonHeadMat getBlockFacing(final BlockFace face)
    {
        return getPistonHead(face, this.extended);
    }

    @Override
    public PistonHeadMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of PistonHead sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonHead or null
     */
    public static PistonHeadMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonHead sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonHead or null
     */
    public static PistonHeadMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonHead sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonHead
     */
    public static PistonHeadMat getPistonHead(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonHeadMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PistonHeadMat.register(PISTON_HEAD_DOWN);
        PistonHeadMat.register(PISTON_HEAD_UP);
        PistonHeadMat.register(PISTON_HEAD_NORTH);
        PistonHeadMat.register(PISTON_HEAD_SOUTH);
        PistonHeadMat.register(PISTON_HEAD_WEST);
        PistonHeadMat.register(PISTON_HEAD_EAST);
        PistonHeadMat.register(PISTON_HEAD_DOWN_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_UP_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_NORTH_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_SOUTH_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_WEST_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_EAST_EXTENDED);
    }
}
