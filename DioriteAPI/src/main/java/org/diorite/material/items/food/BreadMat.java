package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BreadMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BreadMat BREAD = new BreadMat();

    private static final Map<String, BreadMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BreadMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected BreadMat()
    {
        super("BREAD", 297, "minecraft:bread", "BREAD", (short) 0x00, 5, 6);
    }

    protected BreadMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected BreadMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public BreadMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BreadMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Bread sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bread or null
     */
    public static BreadMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Bread sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bread or null
     */
    public static BreadMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BreadMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BreadMat[] types()
    {
        return BreadMat.breadTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BreadMat[] breadTypes()
    {
        return byID.values(new BreadMat[byID.size()]);
    }

    static
    {
        BreadMat.register(BREAD);
    }
}

