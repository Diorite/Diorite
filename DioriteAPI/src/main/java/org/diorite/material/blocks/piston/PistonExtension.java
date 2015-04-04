package org.diorite.material.blocks.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PistonExtension extends PistonBase
{
    public static final byte  USED_DATA_VALUES = 12;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PISTON_EXTENSION__BLAST_RESISTANCE;
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

    public PistonExtension(final int maxStack, final String typeName, final BlockFace face, final boolean extended)
    {
        super(PISTON_EXTENSION_DOWN.name(), PISTON_EXTENSION_DOWN.getId(), PISTON_EXTENSION_DOWN.getMinecraftId(), maxStack, typeName, face, extended);
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
    public PistonExtension getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonExtension getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public PistonExtension getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public PistonExtension getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    public static PistonExtension getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PistonExtension getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static PistonExtension getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

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
