package org.diorite.material.items.mob.tool;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SaddleMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SaddleMat SADDLE = new SaddleMat();

    private static final Map<String, SaddleMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SaddleMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected SaddleMat()
    {
        super("SADDLE", 329, "minecraft:saddle", "SADDLE", (short) 0x00);
    }

    protected SaddleMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected SaddleMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public SaddleMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SaddleMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Saddle sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Saddle or null
     */
    public static SaddleMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Saddle sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Saddle or null
     */
    public static SaddleMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SaddleMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SaddleMat[] types()
    {
        return SaddleMat.saddleTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SaddleMat[] saddleTypes()
    {
        return byID.values(new SaddleMat[byID.size()]);
    }

    static
    {
        SaddleMat.register(SADDLE);
    }
}

