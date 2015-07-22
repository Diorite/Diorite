package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class AppleMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final AppleMat APPLE = new AppleMat();

    private static final Map<String, AppleMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<AppleMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected AppleMat()
    {
        super("APPLE", 260, "minecraft:apple", "APPLE", (short) 0x00, 4, 2.4F);
    }

    protected AppleMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected AppleMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public AppleMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public AppleMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Apple sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Apple or null
     */
    public static AppleMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Apple sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Apple or null
     */
    public static AppleMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AppleMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public AppleMat[] types()
    {
        return AppleMat.appleTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AppleMat[] appleTypes()
    {
        return byID.values(new AppleMat[byID.size()]);
    }

    static
    {
        AppleMat.register(APPLE);
    }
}
