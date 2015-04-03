package org.diorite.material.blocks.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Piston extends PistonBase
{
    public static final byte  USED_DATA_VALUES = 12;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PISTON__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PISTON__HARDNESS;

    public static final Piston PISTON_DOWN  = new Piston();
    public static final Piston PISTON_UP    = new Piston("UP", BlockFace.UP, false);
    public static final Piston PISTON_NORTH = new Piston("NORTH", BlockFace.NORTH, false);
    public static final Piston PISTON_SOUTH = new Piston("SOUTH", BlockFace.SOUTH, false);
    public static final Piston PISTON_WEST  = new Piston("WEST", BlockFace.WEST, false);
    public static final Piston PISTON_EAST  = new Piston("EAST", BlockFace.EAST, false);

    public static final Piston PISTON_DOWN_EXTENDED  = new Piston("DOWN_EXTENDED", BlockFace.DOWN, true);
    public static final Piston PISTON_UP_EXTENDED    = new Piston("UP_EXTENDED", BlockFace.UP, true);
    public static final Piston PISTON_NORTH_EXTENDED = new Piston("NORTH_EXTENDED", BlockFace.NORTH, true);
    public static final Piston PISTON_SOUTH_EXTENDED = new Piston("SOUTH_EXTENDED", BlockFace.SOUTH, true);
    public static final Piston PISTON_WEST_EXTENDED  = new Piston("WEST_EXTENDED", BlockFace.WEST, true);
    public static final Piston PISTON_EAST_EXTENDED  = new Piston("EAST_EXTENDED", BlockFace.EAST, true);

    private static final Map<String, Piston>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Piston> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected Piston()
    {
        super("PISTON", 33, "minecraft:piston", "DOWN", BlockFace.DOWN, false);
    }

    public Piston(final String enumName, final BlockFace face, final boolean extended)
    {
        super(PISTON_DOWN.name(), PISTON_DOWN.getId(), PISTON_DOWN.getMinecraftId(), enumName, face, extended);
    }

    public Piston(final int maxStack, final String typeName, final BlockFace face, final boolean extended)
    {
        super(PISTON_DOWN.name(), PISTON_DOWN.getId(), PISTON_DOWN.getMinecraftId(), maxStack, typeName, face, extended);
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
    public Piston getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Piston getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public Piston getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public Piston getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public Piston getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    public static Piston getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Piston getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static Piston getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    public static void register(final Piston element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Piston.register(PISTON_DOWN);
        Piston.register(PISTON_UP);
        Piston.register(PISTON_NORTH);
        Piston.register(PISTON_SOUTH);
        Piston.register(PISTON_WEST);
        Piston.register(PISTON_EAST);
        Piston.register(PISTON_DOWN_EXTENDED);
        Piston.register(PISTON_UP_EXTENDED);
        Piston.register(PISTON_NORTH_EXTENDED);
        Piston.register(PISTON_SOUTH_EXTENDED);
        Piston.register(PISTON_WEST_EXTENDED);
        Piston.register(PISTON_EAST_EXTENDED);
    }
}
