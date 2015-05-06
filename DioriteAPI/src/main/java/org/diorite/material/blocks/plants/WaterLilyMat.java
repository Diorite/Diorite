package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WaterLily" and all its subtypes.
 */
public class WaterLilyMat extends PlantMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WATER_LILY__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WATER_LILY__HARDNESS;

    public static final WaterLilyMat WATER_LILY = new WaterLilyMat();

    private static final Map<String, WaterLilyMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WaterLilyMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WaterLilyMat()
    {
        super("WATER_LILY", 111, "minecraft:waterlily", "WATER_LILY", (byte) 0x00);
    }

    protected WaterLilyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
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
    public WaterLilyMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WaterLilyMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WaterLily sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WaterLily sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WaterLilyMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WaterLilyMat.register(WATER_LILY);
    }
}
