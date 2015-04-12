package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class WallSign extends SignBlock
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WALL_SIGN__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WALL_SIGN__HARDNESS;

    public static final WallSign WALL_SIGN = new WallSign();

    private static final Map<String, WallSign>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallSign> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WallSign()
    {
        super("WALL_SIGN", 68, "minecraft:wall_sign", "WALL_SIGN", (byte) 0x00);
    }

    public WallSign(final String enumName, final int type)
    {
        super(WALL_SIGN.name(), WALL_SIGN.getId(), WALL_SIGN.getMinecraftId(), enumName, (byte) type);
    }

    public WallSign(final int maxStack, final String typeName, final byte type)
    {
        super(WALL_SIGN.name(), WALL_SIGN.getId(), WALL_SIGN.getMinecraftId(), maxStack, typeName, type);
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
    public WallSign getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallSign getType(final int id)
    {
        return getByID(id);
    }

    public static WallSign getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static WallSign getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final WallSign element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WallSign.register(WALL_SIGN);
    }
}
