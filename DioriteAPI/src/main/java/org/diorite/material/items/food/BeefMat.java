package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.material.items.EdibleItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BeefMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final BeefMat BEEF = new BeefMat();

    private static final Map<String, BeefMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BeefMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected BeefMat()
    {
        super("BEEF", 363, "minecraft:beef", "BEEF", (short) 0x00, 3, 1.8F);
    }

    protected BeefMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected BeefMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public BeefMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BeefMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Beef sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Beef or null
     */
    public static BeefMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Beef sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Beef or null
     */
    public static BeefMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BeefMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BeefMat[] types()
    {
        return BeefMat.beefTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BeefMat[] beefTypes()
    {
        return byID.values(new BeefMat[byID.size()]);
    }

    static
    {
        BeefMat.register(BEEF);
    }
}

