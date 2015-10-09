package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CookedPorkchopMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final CookedPorkchopMat COOKED_PORKCHOP = new CookedPorkchopMat();

    private static final Map<String, CookedPorkchopMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CookedPorkchopMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected CookedPorkchopMat()
    {
        super("COOKED_PORKCHOP", 320, "minecraft:cooked_porkchop", "COOKED_PORKCHOP", (short) 0x00, 8, 12.8F);
    }

    protected CookedPorkchopMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected CookedPorkchopMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public CookedPorkchopMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CookedPorkchopMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of CookedPorkchop sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CookedPorkchop or null
     */
    public static CookedPorkchopMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of CookedPorkchop sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CookedPorkchop or null
     */
    public static CookedPorkchopMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CookedPorkchopMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CookedPorkchopMat[] types()
    {
        return CookedPorkchopMat.cookedPorkchopTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static CookedPorkchopMat[] cookedPorkchopTypes()
    {
        return byID.values(new CookedPorkchopMat[byID.size()]);
    }

    static
    {
        CookedPorkchopMat.register(COOKED_PORKCHOP);
    }
}

