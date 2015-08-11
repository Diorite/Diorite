package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CookedBeefMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CookedBeefMat COOKED_BEEF = new CookedBeefMat();

    private static final Map<String, CookedBeefMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CookedBeefMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected CookedBeefMat()
    {
        super("COOKED_BEEF", 364, "minecraft:cooked_beef", "COOKED_BEEF", (short) 0x00, 8, 12.8F);
    }

    protected CookedBeefMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected CookedBeefMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public CookedBeefMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CookedBeefMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of CookedBeef sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CookedBeef or null
     */
    public static CookedBeefMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of CookedBeef sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CookedBeef or null
     */
    public static CookedBeefMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CookedBeefMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CookedBeefMat[] types()
    {
        return CookedBeefMat.cookedBeefTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CookedBeefMat[] cookedBeefTypes()
    {
        return byID.values(new CookedBeefMat[byID.size()]);
    }

    static
    {
        CookedBeefMat.register(COOKED_BEEF);
    }
}

