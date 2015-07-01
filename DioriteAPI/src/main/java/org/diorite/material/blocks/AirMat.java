package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Air" and all its subtypes.
 */
public class AirMat extends BlockMaterialData
{
    /**
     *  Sub-ids used by diorite/minecraft by default
     */
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
    /**
     * Blast resistance of block, can be changed only before server start.
     *  Final copy of blast resistance from {@link MagicNumbers} class.
     */
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__AIR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     *  Final copy of hardness from {@link MagicNumbers} class.
     */
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__AIR__HARDNESS;

    public static final AirMat AIR = new AirMat();

    private static final Map<String, AirMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AirMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected AirMat()
    {
        super("AIR", 0, "minecraft:air", 0, "AIR", (byte) 0x00);
    }

    protected AirMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public AirMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AirMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isSolid()
    {
        return false;
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

    /**
     * Returns one of Air sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Air or null
     */
    public static AirMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Air sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Air or null
     */
    public static AirMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AirMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public AirMat[] types()
    {
        return AirMat.airTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AirMat[] airTypes()
    {
        return byID.values(new AirMat[byID.size()]);
    }

    static
    {
        AirMat.register(AIR);
    }
}
