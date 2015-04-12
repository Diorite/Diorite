package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Netherrack extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__NETHERRACK__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__NETHERRACK__HARDNESS;

    public static final Netherrack NETHERRACK = new Netherrack();

    private static final Map<String, Netherrack>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Netherrack> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Netherrack()
    {
        super("NETHERRACK", 87, "minecraft:netherrack", "NETHERRACK", (byte) 0x00);
    }

    public Netherrack(final String enumName, final int type)
    {
        super(NETHERRACK.name(), NETHERRACK.getId(), NETHERRACK.getMinecraftId(), enumName, (byte) type);
    }

    public Netherrack(final int maxStack, final String typeName, final byte type)
    {
        super(NETHERRACK.name(), NETHERRACK.getId(), NETHERRACK.getMinecraftId(), maxStack, typeName, type);
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
    public Netherrack getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Netherrack getType(final int id)
    {
        return getByID(id);
    }

    public static Netherrack getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Netherrack getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Netherrack element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Netherrack.register(NETHERRACK);
    }
}
