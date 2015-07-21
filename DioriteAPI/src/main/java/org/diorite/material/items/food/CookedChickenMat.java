package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CookedChickenMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CookedChickenMat COOKED_CHICKEN = new CookedChickenMat();

    private static final Map<String, CookedChickenMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CookedChickenMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected CookedChickenMat()
    {
        super("COOKED_CHICKEN", 366, "minecraft:cooked_chicken", "COOKED_CHICKEN", (short) 0x00, 6, 7.2F);
    }

    protected CookedChickenMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected CookedChickenMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public CookedChickenMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CookedChickenMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of CookedChicken sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CookedChicken or null
     */
    public static CookedChickenMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of CookedChicken sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CookedChicken or null
     */
    public static CookedChickenMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CookedChickenMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CookedChickenMat[] types()
    {
        return CookedChickenMat.cookedChickenTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CookedChickenMat[] cookedChickenTypes()
    {
        return byID.values(new CookedChickenMat[byID.size()]);
    }

    static
    {
        CookedChickenMat.register(COOKED_CHICKEN);
    }
}

