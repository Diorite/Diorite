package org.diorite.material.blocks.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class PistonHead extends PistonBase
{
    public static final byte  USED_DATA_VALUES = 12;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PISTON_HEAD__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PISTON_HEAD__HARDNESS;

    public static final PistonHead PISTON_HEAD_DOWN  = new PistonHead();
    public static final PistonHead PISTON_HEAD_UP    = new PistonHead("UP", BlockFace.UP, false);
    public static final PistonHead PISTON_HEAD_NORTH = new PistonHead("NORTH", BlockFace.NORTH, false);
    public static final PistonHead PISTON_HEAD_SOUTH = new PistonHead("SOUTH", BlockFace.SOUTH, false);
    public static final PistonHead PISTON_HEAD_WEST  = new PistonHead("WEST", BlockFace.WEST, false);
    public static final PistonHead PISTON_HEAD_EAST  = new PistonHead("EAST", BlockFace.EAST, false);

    public static final PistonHead PISTON_HEAD_DOWN_EXTENDED  = new PistonHead("DOWN_EXTENDED", BlockFace.DOWN, true);
    public static final PistonHead PISTON_HEAD_UP_EXTENDED    = new PistonHead("UP_EXTENDED", BlockFace.UP, true);
    public static final PistonHead PISTON_HEAD_NORTH_EXTENDED = new PistonHead("NORTH_EXTENDED", BlockFace.NORTH, true);
    public static final PistonHead PISTON_HEAD_SOUTH_EXTENDED = new PistonHead("SOUTH_EXTENDED", BlockFace.SOUTH, true);
    public static final PistonHead PISTON_HEAD_WEST_EXTENDED  = new PistonHead("WEST_EXTENDED", BlockFace.WEST, true);
    public static final PistonHead PISTON_HEAD_EAST_EXTENDED  = new PistonHead("EAST_EXTENDED", BlockFace.EAST, true);

    private static final Map<String, PistonHead>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonHead> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PistonHead()
    {
        super("PISTON_HEAD", 34, "minecraft:piston_head", "DOWN", BlockFace.DOWN, false);
    }

    public PistonHead(final String enumName, final BlockFace face, final boolean extended)
    {
        super(PISTON_HEAD_DOWN.name(), PISTON_HEAD_DOWN.getId(), PISTON_HEAD_DOWN.getMinecraftId(), enumName, face, extended);
    }

    public PistonHead(final int maxStack, final String typeName, final BlockFace face, final boolean extended)
    {
        super(PISTON_HEAD_DOWN.name(), PISTON_HEAD_DOWN.getId(), PISTON_HEAD_DOWN.getMinecraftId(), maxStack, typeName, face, extended);
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
    public PistonHead getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonHead getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonHead getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public PistonHead getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public PistonHead getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    public static PistonHead getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static PistonHead getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static PistonHead getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    public static void register(final PistonHead element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PistonHead.register(PISTON_HEAD_DOWN);
        PistonHead.register(PISTON_HEAD_UP);
        PistonHead.register(PISTON_HEAD_NORTH);
        PistonHead.register(PISTON_HEAD_SOUTH);
        PistonHead.register(PISTON_HEAD_WEST);
        PistonHead.register(PISTON_HEAD_EAST);
        PistonHead.register(PISTON_HEAD_DOWN_EXTENDED);
        PistonHead.register(PISTON_HEAD_UP_EXTENDED);
        PistonHead.register(PISTON_HEAD_NORTH_EXTENDED);
        PistonHead.register(PISTON_HEAD_SOUTH_EXTENDED);
        PistonHead.register(PISTON_HEAD_WEST_EXTENDED);
        PistonHead.register(PISTON_HEAD_EAST_EXTENDED);
    }
}
