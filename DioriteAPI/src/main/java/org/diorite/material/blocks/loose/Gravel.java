package org.diorite.material.blocks.loose;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Gravel" and all its subtypes.
 */
public class Gravel extends Loose
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GRAVEL__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GRAVEL__HARDNESS;

    public static final Gravel GRAVEL = new Gravel();

    private static final Map<String, Gravel>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Gravel> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Gravel()
    {
        super("GRAVEL", 13, "minecraft:gravel", "GRAVEL", (byte) 0x00);
    }

    public Gravel(final String enumName, final int type)
    {
        super(GRAVEL.name(), GRAVEL.getId(), GRAVEL.getMinecraftId(), enumName, (byte) type);
    }

    public Gravel(final int maxStack, final String typeName, final byte type)
    {
        super(GRAVEL.name(), GRAVEL.getId(), GRAVEL.getMinecraftId(), maxStack, typeName, type);
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
    public Gravel getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Gravel getType(final int id)
    {
        return getByID(id);
    }

    public static Gravel getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Gravel getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Gravel element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Gravel.register(GRAVEL);
    }
}
