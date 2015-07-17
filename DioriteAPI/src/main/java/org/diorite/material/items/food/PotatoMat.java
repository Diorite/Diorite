package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.material.items.EdibleItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class PotatoMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final PotatoMat POTATO = new PotatoMat();

    private static final Map<String, PotatoMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PotatoMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected PotatoMat()
    {
        super("POTATO", 392, "minecraft:potato", "POTATO", (short) 0x00, 1, 0.6F);
    }

    protected PotatoMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected PotatoMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public PotatoMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PotatoMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Potato sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Potato or null
     */
    public static PotatoMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Potato sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Potato or null
     */
    public static PotatoMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PotatoMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PotatoMat[] types()
    {
        return PotatoMat.potatoTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PotatoMat[] potatoTypes()
    {
        return byID.values(new PotatoMat[byID.size()]);
    }

    static
    {
        PotatoMat.register(POTATO);
    }
}

