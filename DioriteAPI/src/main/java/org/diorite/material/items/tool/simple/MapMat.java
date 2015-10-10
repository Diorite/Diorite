package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Map' item material in minecraft. <br>
 * ID of material: 395 <br>
 * String ID of material: minecraft:map <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class MapMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MapMat MAP = new MapMat();

    private static final Map<String, MapMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<MapMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected MapMat()
    {
        super("MAP", 395, "minecraft:map", "MAP", (short) 0x00);
    }

    protected MapMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected MapMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public MapMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public MapMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Map sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Map or null
     */
    public static MapMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Map sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Map or null
     */
    public static MapMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MapMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MapMat[] types()
    {
        return MapMat.mapTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static MapMat[] mapTypes()
    {
        return byID.values(new MapMat[byID.size()]);
    }

    static
    {
        MapMat.register(MAP);
    }
}

