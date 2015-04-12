package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class DoubleFlowers extends Plant
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DOUBLE_FLOWERS__HARDNESS;

    public static final DoubleFlowers DOUBLE_FLOWERS = new DoubleFlowers();

    private static final Map<String, DoubleFlowers>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleFlowers> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DoubleFlowers()
    {
        super("DOUBLE_FLOWERS", 175, "minecraft:double_plant", "DOUBLE_FLOWERS", (byte) 0x00);
    }

    public DoubleFlowers(final String enumName, final int type)
    {
        super(DOUBLE_FLOWERS.name(), DOUBLE_FLOWERS.getId(), DOUBLE_FLOWERS.getMinecraftId(), enumName, (byte) type);
    }

    public DoubleFlowers(final int maxStack, final String typeName, final byte type)
    {
        super(DOUBLE_FLOWERS.name(), DOUBLE_FLOWERS.getId(), DOUBLE_FLOWERS.getMinecraftId(), maxStack, typeName, type);
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
    public DoubleFlowers getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleFlowers getType(final int id)
    {
        return getByID(id);
    }

    public static DoubleFlowers getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static DoubleFlowers getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final DoubleFlowers element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        DoubleFlowers.register(DOUBLE_FLOWERS);
    }
}
