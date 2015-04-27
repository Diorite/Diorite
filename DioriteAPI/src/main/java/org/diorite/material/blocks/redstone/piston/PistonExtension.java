package org.diorite.material.blocks.redstone.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PistonExtension" and all its subtypes.
 */
public class PistonExtension extends PistonBase
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PISTON_EXTENSION__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PISTON_EXTENSION__HARDNESS;

    public static final PistonExtension PISTON_EXTENSION_DOWN  = new PistonExtension();
    public static final PistonExtension PISTON_EXTENSION_UP    = new PistonExtension("UP", BlockFace.UP, false);
    public static final PistonExtension PISTON_EXTENSION_NORTH = new PistonExtension("NORTH", BlockFace.NORTH, false);
    public static final PistonExtension PISTON_EXTENSION_SOUTH = new PistonExtension("SOUTH", BlockFace.SOUTH, false);
    public static final PistonExtension PISTON_EXTENSION_WEST  = new PistonExtension("WEST", BlockFace.WEST, false);
    public static final PistonExtension PISTON_EXTENSION_EAST  = new PistonExtension("EAST", BlockFace.EAST, false);

    public static final PistonExtension PISTON_EXTENSION_DOWN_EXTENDED  = new PistonExtension("DOWN_EXTENDED", BlockFace.DOWN, true);
    public static final PistonExtension PISTON_EXTENSION_UP_EXTENDED    = new PistonExtension("UP_EXTENDED", BlockFace.UP, true);
    public static final PistonExtension PISTON_EXTENSION_NORTH_EXTENDED = new PistonExtension("NORTH_EXTENDED", BlockFace.NORTH, true);
    public static final PistonExtension PISTON_EXTENSION_SOUTH_EXTENDED = new PistonExtension("SOUTH_EXTENDED", BlockFace.SOUTH, true);
    public static final PistonExtension PISTON_EXTENSION_WEST_EXTENDED  = new PistonExtension("WEST_EXTENDED", BlockFace.WEST, true);
    public static final PistonExtension PISTON_EXTENSION_EAST_EXTENDED  = new PistonExtension("EAST_EXTENDED", BlockFace.EAST, true);

    private static final Map<String, PistonExtension>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonExtension> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PistonExtension()
    {
        super("PISTON_EXTENSION", 36, "minecraft:piston_extension", "DOWN", BlockFace.DOWN, false);
    }

    public PistonExtension(final String enumName, final BlockFace face, final boolean extended)
    {
        super(PISTON_EXTENSION_DOWN.name(), PISTON_EXTENSION_DOWN.getId(), PISTON_EXTENSION_DOWN.getMinecraftId(), enumName, face, extended);
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
    public PistonExtension getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonExtension getExtended(final boolean extended)
    {
        return getPistonExtension(this.facing, extended);
    }

    @Override
    public PistonExtension getType(final BlockFace face, final boolean extended)
    {
        return getPistonExtension(face, extended);
    }

    @Override
    public PistonExtension getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonExtension getBlockFacing(final BlockFace face)
    {
        return getPistonExtension(face, this.extended);
    }

    /**
     * Returns one of PistonExtension sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonExtension or null
     */
    public static PistonExtension getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonExtension sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonExtension or null
     */
    public static PistonExtension getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonExtension sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonExtension
     */
    public static PistonExtension getPistonExtension(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonExtension element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PistonExtension.register(PISTON_EXTENSION_DOWN);
        PistonExtension.register(PISTON_EXTENSION_UP);
        PistonExtension.register(PISTON_EXTENSION_NORTH);
        PistonExtension.register(PISTON_EXTENSION_SOUTH);
        PistonExtension.register(PISTON_EXTENSION_WEST);
        PistonExtension.register(PISTON_EXTENSION_EAST);
        PistonExtension.register(PISTON_EXTENSION_DOWN_EXTENDED);
        PistonExtension.register(PISTON_EXTENSION_UP_EXTENDED);
        PistonExtension.register(PISTON_EXTENSION_NORTH_EXTENDED);
        PistonExtension.register(PISTON_EXTENSION_SOUTH_EXTENDED);
        PistonExtension.register(PISTON_EXTENSION_WEST_EXTENDED);
        PistonExtension.register(PISTON_EXTENSION_EAST_EXTENDED);
    }
}
