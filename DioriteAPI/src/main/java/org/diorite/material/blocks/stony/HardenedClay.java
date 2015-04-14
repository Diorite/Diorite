package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "HardenedClay" and all its subtypes.
 */
public class HardenedClay extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__HARDENED_CLAY__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__HARDENED_CLAY__HARDNESS;

    public static final HardenedClay HARDENED_CLAY = new HardenedClay();

    private static final Map<String, HardenedClay>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HardenedClay> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected HardenedClay()
    {
        super("HARDENED_CLAY", 172, "minecraft:hardened_clay", "HARDENED_CLAY", (byte) 0x00);
    }

    public HardenedClay(final String enumName, final int type)
    {
        super(HARDENED_CLAY.name(), HARDENED_CLAY.getId(), HARDENED_CLAY.getMinecraftId(), enumName, (byte) type);
    }

    public HardenedClay(final int maxStack, final String typeName, final byte type)
    {
        super(HARDENED_CLAY.name(), HARDENED_CLAY.getId(), HARDENED_CLAY.getMinecraftId(), maxStack, typeName, type);
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
    public HardenedClay getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HardenedClay getType(final int id)
    {
        return getByID(id);
    }

    public static HardenedClay getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static HardenedClay getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final HardenedClay element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        HardenedClay.register(HARDENED_CLAY);
    }
}
