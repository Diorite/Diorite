package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "CoalOre" and all its subtypes.
 */
public class CoalOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COAL_ORE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COAL_ORE__HARDNESS;

    public static final CoalOreMat COAL_ORE = new CoalOreMat();

    private static final Map<String, CoalOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CoalOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CoalOreMat()
    {
        super("COAL_ORE", 16, "minecraft:coal_ore", "COAL_ORE", (byte) 0x00);
    }

    protected CoalOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
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
    public CoalOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CoalOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CoalOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CoalOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CoalOre or null
     */
    public static CoalOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalOreMat[] types()
    {
        return CoalOreMat.coalOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CoalOreMat[] coalOreTypes()
    {
        return byID.values(new CoalOreMat[byID.size()]);
    }

    static
    {
        CoalOreMat.register(COAL_ORE);
    }
}
