package org.diorite.material.blocks.redstone.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PistonSticky extends PistonBase
{
    public static final byte  USED_DATA_VALUES = 12;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STICKY_PISTON__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STICKY_PISTON__HARDNESS;

    public static final PistonSticky STICKY_PISTON_DOWN  = new PistonSticky();
    public static final PistonSticky STICKY_PISTON_UP    = new PistonSticky("UP", BlockFace.UP, false);
    public static final PistonSticky STICKY_PISTON_NORTH = new PistonSticky("NORTH", BlockFace.NORTH, false);
    public static final PistonSticky STICKY_PISTON_SOUTH = new PistonSticky("SOUTH", BlockFace.SOUTH, false);
    public static final PistonSticky STICKY_PISTON_WEST  = new PistonSticky("WEST", BlockFace.WEST, false);
    public static final PistonSticky STICKY_PISTON_EAST  = new PistonSticky("EAST", BlockFace.EAST, false);

    public static final PistonSticky STICKY_PISTON_DOWN_EXTENDED  = new PistonSticky("DOWN_EXTENDED", BlockFace.DOWN, true);
    public static final PistonSticky STICKY_PISTON_UP_EXTENDED    = new PistonSticky("UP_EXTENDED", BlockFace.UP, true);
    public static final PistonSticky STICKY_PISTON_NORTH_EXTENDED = new PistonSticky("NORTH_EXTENDED", BlockFace.NORTH, true);
    public static final PistonSticky STICKY_PISTON_SOUTH_EXTENDED = new PistonSticky("SOUTH_EXTENDED", BlockFace.SOUTH, true);
    public static final PistonSticky STICKY_PISTON_WEST_EXTENDED  = new PistonSticky("WEST_EXTENDED", BlockFace.WEST, true);
    public static final PistonSticky STICKY_PISTON_EAST_EXTENDED  = new PistonSticky("EAST_EXTENDED", BlockFace.EAST, true);

    private static final Map<String, PistonSticky>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonSticky> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PistonSticky()
    {
        super("STICKY_PISTON", 29, "minecraft:sticky_piston", "DOWN", BlockFace.DOWN, false);
    }

    public PistonSticky(final String enumName, final BlockFace face, final boolean extended)
    {
        super(STICKY_PISTON_DOWN.name(), STICKY_PISTON_DOWN.getId(), STICKY_PISTON_DOWN.getMinecraftId(), enumName, face, extended);
    }

    public PistonSticky(final int maxStack, final String typeName, final BlockFace face, final boolean extended)
    {
        super(STICKY_PISTON_DOWN.name(), STICKY_PISTON_DOWN.getId(), STICKY_PISTON_DOWN.getMinecraftId(), maxStack, typeName, face, extended);
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
    public PistonSticky getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonSticky getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonSticky getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public PistonSticky getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public PistonSticky getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    public static PistonSticky getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PistonSticky getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static PistonSticky getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    public static void register(final PistonSticky element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PistonSticky.register(STICKY_PISTON_DOWN);
        PistonSticky.register(STICKY_PISTON_UP);
        PistonSticky.register(STICKY_PISTON_NORTH);
        PistonSticky.register(STICKY_PISTON_SOUTH);
        PistonSticky.register(STICKY_PISTON_WEST);
        PistonSticky.register(STICKY_PISTON_EAST);
        PistonSticky.register(STICKY_PISTON_DOWN_EXTENDED);
        PistonSticky.register(STICKY_PISTON_UP_EXTENDED);
        PistonSticky.register(STICKY_PISTON_NORTH_EXTENDED);
        PistonSticky.register(STICKY_PISTON_SOUTH_EXTENDED);
        PistonSticky.register(STICKY_PISTON_WEST_EXTENDED);
        PistonSticky.register(STICKY_PISTON_EAST_EXTENDED);
    }
}
