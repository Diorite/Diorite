package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class MushroomStewMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final MushroomStewMat MUSHROOM_STEW = new MushroomStewMat();

    private static final Map<String, MushroomStewMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<MushroomStewMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected MushroomStewMat()
    {
        super("MUSHROOM_STEW", 282, "minecraft:mushroom_stew", 1, "MUSHROOM_STEW", (short) 0x00, 6, 7.2F);
    }

    protected MushroomStewMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, MUSHROOM_STEW.getMaxStack(), typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected MushroomStewMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public MushroomStewMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public MushroomStewMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of MushroomStew sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of MushroomStew or null
     */
    public static MushroomStewMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of MushroomStew sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of MushroomStew or null
     */
    public static MushroomStewMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final MushroomStewMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public MushroomStewMat[] types()
    {
        return MushroomStewMat.mushroomStewTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static MushroomStewMat[] mushroomStewTypes()
    {
        return byID.values(new MushroomStewMat[byID.size()]);
    }

    static
    {
        MushroomStewMat.register(MUSHROOM_STEW);
    }
}

