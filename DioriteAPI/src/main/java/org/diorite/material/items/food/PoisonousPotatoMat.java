package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class PoisonousPotatoMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final PoisonousPotatoMat POISONOUS_POTATO = new PoisonousPotatoMat();

    private static final Map<String, PoisonousPotatoMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<PoisonousPotatoMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected PoisonousPotatoMat()
    {
        super("POISONOUS_POTATO", 394, "minecraft:poisounous_potato", "POISONOUS_POTATO", (short) 0x00, 2, 1.2F);
    }

    protected PoisonousPotatoMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected PoisonousPotatoMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public PoisonousPotatoMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public PoisonousPotatoMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of PoisonousPotato sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PoisonousPotato or null
     */
    public static PoisonousPotatoMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of PoisonousPotato sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PoisonousPotato or null
     */
    public static PoisonousPotatoMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PoisonousPotatoMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PoisonousPotatoMat[] types()
    {
        return PoisonousPotatoMat.poisonousPotatoTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static PoisonousPotatoMat[] poisonousPotatoTypes()
    {
        return byID.values(new PoisonousPotatoMat[byID.size()]);
    }

    static
    {
        PoisonousPotatoMat.register(POISONOUS_POTATO);
    }
}

