package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "PumpkinStem" and all its subtypes.
 */
public class PumpkinStem extends PlantStem
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PUMPKIN_STEM__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PUMPKIN_STEM__HARDNESS;

    public static final PumpkinStem PUMPKIN_STEM = new PumpkinStem();

    private static final Map<String, PumpkinStem>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinStem> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected PumpkinStem()
    {
        super("PUMPKIN_STEM", 104, "minecraft:pumpkin_stem", "PUMPKIN_STEM", (byte) 0x00);
    }

    public PumpkinStem(final String enumName, final int type)
    {
        super(PUMPKIN_STEM.name(), PUMPKIN_STEM.getId(), PUMPKIN_STEM.getMinecraftId(), enumName, (byte) type);
    }

    public PumpkinStem(final int maxStack, final String typeName, final byte type)
    {
        super(PUMPKIN_STEM.name(), PUMPKIN_STEM.getId(), PUMPKIN_STEM.getMinecraftId(), maxStack, typeName, type);
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
    public PumpkinStem getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinStem getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of PumpkinStem sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStem getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PumpkinStem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStem getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final PumpkinStem element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        PumpkinStem.register(PUMPKIN_STEM);
    }
}
