package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class WheatMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final WheatMat WHEAT = new WheatMat();

    private static final Map<String, WheatMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<WheatMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected WheatMat()
    {
        super("WHEAT", 296, "minecraft:wheat", "WHEAT", (short) 0x00);
    }

    protected WheatMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected WheatMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public WheatMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public WheatMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Wheat sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Wheat or null
     */
    public static WheatMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Wheat sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Wheat or null
     */
    public static WheatMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WheatMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WheatMat[] types()
    {
        return WheatMat.wheatTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WheatMat[] wheatTypes()
    {
        return byID.values(new WheatMat[byID.size()]);
    }

    static
    {
        WheatMat.register(WHEAT);
    }
}

