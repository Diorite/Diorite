package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class FishMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final FishMat FISH = new FishMat();

    private static final Map<String, FishMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<FishMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected FishMat()
    {
        super("FISH", 349, "minecraft:fish", "FISH", (short) 0x00, 2, 0.4F);
    }

    protected FishMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected FishMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public FishMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public FishMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Fish sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Fish or null
     */
    public static FishMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Fish sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Fish or null
     */
    public static FishMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FishMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FishMat[] types()
    {
        return FishMat.fishTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FishMat[] fishTypes()
    {
        return byID.values(new FishMat[byID.size()]);
    }

    static
    {
        FishMat.register(FISH);
    }
}

