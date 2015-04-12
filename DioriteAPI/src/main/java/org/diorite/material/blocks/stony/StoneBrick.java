package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class StoneBrick extends Stony
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_BRICK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_BRICK__HARDNESS;

    public static final StoneBrick STONE_BRICK = new StoneBrick();

    private static final Map<String, StoneBrick>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StoneBrick> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StoneBrick()
    {
        super("STONE_BRICK", 98, "minecraft:stonebrick", "STONE_BRICK", (byte) 0x00);
    }

    public StoneBrick(final String enumName, final int type)
    {
        super(STONE_BRICK.name(), STONE_BRICK.getId(), STONE_BRICK.getMinecraftId(), enumName, (byte) type);
    }

    public StoneBrick(final int maxStack, final String typeName, final byte type)
    {
        super(STONE_BRICK.name(), STONE_BRICK.getId(), STONE_BRICK.getMinecraftId(), maxStack, typeName, type);
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
    public StoneBrick getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StoneBrick getType(final int id)
    {
        return getByID(id);
    }

    public static StoneBrick getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static StoneBrick getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final StoneBrick element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StoneBrick.register(STONE_BRICK);
    }
}
