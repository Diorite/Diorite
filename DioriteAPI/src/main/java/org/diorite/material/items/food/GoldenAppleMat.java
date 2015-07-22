package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class GoldenAppleMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final GoldenAppleMat GOLDED_APPLE           = new GoldenAppleMat();
    public static final GoldenAppleMat GOLDED_ENCHANTED_APPLE = new GoldenAppleMat("ENCHANTED", 0x01);

    private static final Map<String, GoldenAppleMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<GoldenAppleMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected GoldenAppleMat()
    {
        super("GOLDED_APPLE", 322, "minecraft:golden_apple", "GOLDED_APPLE", (short) 0x00, 4, 9.6F);
    }

    protected GoldenAppleMat(final String name, final int type)
    {
        super(GOLDED_APPLE.name(), GOLDED_APPLE.getId(), GOLDED_APPLE.getMinecraftId(), name, (short) type, 4, 9.6F);
    }

    protected GoldenAppleMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected GoldenAppleMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public GoldenAppleMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public GoldenAppleMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of GoldenApple sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenApple or null
     */
    public static GoldenAppleMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of GoldenApple sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenApple or null
     */
    public static GoldenAppleMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldenAppleMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public GoldenAppleMat[] types()
    {
        return GoldenAppleMat.goldenAppleTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static GoldenAppleMat[] goldenAppleTypes()
    {
        return byID.values(new GoldenAppleMat[byID.size()]);
    }

    static
    {
        GoldenAppleMat.register(GOLDED_APPLE);
        GoldenAppleMat.register(GOLDED_ENCHANTED_APPLE);
    }
}

