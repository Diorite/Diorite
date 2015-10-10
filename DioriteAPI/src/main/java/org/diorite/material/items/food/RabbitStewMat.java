package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Rabbit Stew' item material in minecraft. <br>
 * ID of material: 413 <br>
 * String ID of material: minecraft:rabbit_stew <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class RabbitStewMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RabbitStewMat RABBIT_STEW = new RabbitStewMat();

    private static final Map<String, RabbitStewMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RabbitStewMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RabbitStewMat()
    {
        super("RABBIT_STEW", 413, "minecraft:rabbit_stew", "RABBIT_STEW", (short) 0x00, 10, 12);
    }

    protected RabbitStewMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected RabbitStewMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public RabbitStewMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RabbitStewMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RabbitStew sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RabbitStew or null
     */
    public static RabbitStewMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RabbitStew sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RabbitStew or null
     */
    public static RabbitStewMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RabbitStewMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RabbitStewMat[] types()
    {
        return RabbitStewMat.rabbitStewTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static RabbitStewMat[] rabbitStewTypes()
    {
        return byID.values(new RabbitStewMat[byID.size()]);
    }

    static
    {
        RabbitStewMat.register(RABBIT_STEW);
    }
}

