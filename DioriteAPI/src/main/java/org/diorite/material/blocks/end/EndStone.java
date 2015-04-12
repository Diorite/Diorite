package org.diorite.material.blocks.end;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.stony.Stony;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class EndStone extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__END_STONE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__END_STONE__HARDNESS;

    public static final EndStone END_STONE = new EndStone();

    private static final Map<String, EndStone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndStone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EndStone()
    {
        super("END_STONE", 121, "minecraft:end_stone", "END_STONE", (byte) 0x00);
    }

    public EndStone(final String enumName, final int type)
    {
        super(END_STONE.name(), END_STONE.getId(), END_STONE.getMinecraftId(), enumName, (byte) type);
    }

    public EndStone(final int maxStack, final String typeName, final byte type)
    {
        super(END_STONE.name(), END_STONE.getId(), END_STONE.getMinecraftId(), maxStack, typeName, type);
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
    public EndStone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndStone getType(final int id)
    {
        return getByID(id);
    }

    public static EndStone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static EndStone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final EndStone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EndStone.register(END_STONE);
    }
}
