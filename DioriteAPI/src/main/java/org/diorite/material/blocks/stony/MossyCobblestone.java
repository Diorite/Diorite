package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "MossyCobblestone" and all its subtypes.
 */
public class MossyCobblestone extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MOSSY_COBBLESTONE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MOSSY_COBBLESTONE__HARDNESS;

    public static final MossyCobblestone MOSSY_COBBLESTONE = new MossyCobblestone();

    private static final Map<String, MossyCobblestone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<MossyCobblestone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected MossyCobblestone()
    {
        super("MOSSY_COBBLESTONE", 48, "minecraft:mossy_cobblestone", "MOSSY_COBBLESTONE", (byte) 0x00);
    }

    public MossyCobblestone(final String enumName, final int type)
    {
        super(MOSSY_COBBLESTONE.name(), MOSSY_COBBLESTONE.getId(), MOSSY_COBBLESTONE.getMinecraftId(), enumName, (byte) type);
    }

    public MossyCobblestone(final int maxStack, final String typeName, final byte type)
    {
        super(MOSSY_COBBLESTONE.name(), MOSSY_COBBLESTONE.getId(), MOSSY_COBBLESTONE.getMinecraftId(), maxStack, typeName, type);
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
    public MossyCobblestone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public MossyCobblestone getType(final int id)
    {
        return getByID(id);
    }

    public static MossyCobblestone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static MossyCobblestone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final MossyCobblestone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        MossyCobblestone.register(MOSSY_COBBLESTONE);
    }
}
