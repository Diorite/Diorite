package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SeaLantren" and all its subtypes.
 */
public class SeaLantrenMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SeaLantrenMat SEA_LANTREN = new SeaLantrenMat();

    private static final Map<String, SeaLantrenMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SeaLantrenMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SeaLantrenMat()
    {
        super("SEA_LANTREN", 169, "minecraft:sea_lantren", "SEA_LANTREN", (byte) 0x00, 0.3f, 1.5f);
    }

    protected SeaLantrenMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public SeaLantrenMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SeaLantrenMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SeaLantren sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SeaLantren or null
     */
    public static SeaLantrenMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SeaLantren sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SeaLantren or null
     */
    public static SeaLantrenMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SeaLantrenMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SeaLantrenMat[] types()
    {
        return SeaLantrenMat.seaLantrenTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SeaLantrenMat[] seaLantrenTypes()
    {
        return byID.values(new SeaLantrenMat[byID.size()]);
    }

    static
    {
        SeaLantrenMat.register(SEA_LANTREN);
    }
}
