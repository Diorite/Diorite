package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CookieMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CookieMat COOKIE = new CookieMat();

    private static final Map<String, CookieMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CookieMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected CookieMat()
    {
        super("COOKIE", 357, "minecraft:cookie", "COOKIE", (short) 0x00, 2, 0.5F);
    }

    protected CookieMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected CookieMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public CookieMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CookieMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Cookie sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cookie or null
     */
    public static CookieMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Cookie sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cookie or null
     */
    public static CookieMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CookieMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CookieMat[] types()
    {
        return CookieMat.cookieTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static CookieMat[] cookieTypes()
    {
        return byID.values(new CookieMat[byID.size()]);
    }

    static
    {
        CookieMat.register(COOKIE);
    }
}

