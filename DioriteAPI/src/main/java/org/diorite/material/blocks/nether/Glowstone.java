package org.diorite.material.blocks.nether;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Glowstone" and all its subtypes.
 */
public class Glowstone extends BlockMaterialData
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GLOWSTONE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GLOWSTONE__HARDNESS;

    public static final Glowstone GLOWSTONE = new Glowstone();

    private static final Map<String, Glowstone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Glowstone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Glowstone()
    {
        super("GLOWSTONE", 89, "minecraft:glowstone", "GLOWSTONE", (byte) 0x00);
    }

    public Glowstone(final String enumName, final int type)
    {
        super(GLOWSTONE.name(), GLOWSTONE.getId(), GLOWSTONE.getMinecraftId(), enumName, (byte) type);
    }

    public Glowstone(final int maxStack, final String typeName, final byte type)
    {
        super(GLOWSTONE.name(), GLOWSTONE.getId(), GLOWSTONE.getMinecraftId(), maxStack, typeName, type);
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
    public Glowstone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Glowstone getType(final int id)
    {
        return getByID(id);
    }

    public static Glowstone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Glowstone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Glowstone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Glowstone.register(GLOWSTONE);
    }
}
