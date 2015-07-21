package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RabbitMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RabbitMat RABBIT = new RabbitMat();

    private static final Map<String, RabbitMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RabbitMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RabbitMat()
    {
        super("RABBIT", 411, "minecraft:rabbit", "RABBIT", (short) 0x00, 3, 1.8F);
    }

    protected RabbitMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected RabbitMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public RabbitMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RabbitMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Rabbit sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Rabbit or null
     */
    public static RabbitMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Rabbit sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Rabbit or null
     */
    public static RabbitMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RabbitMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RabbitMat[] types()
    {
        return RabbitMat.rabbitTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RabbitMat[] rabbitTypes()
    {
        return byID.values(new RabbitMat[byID.size()]);
    }

    static
    {
        RabbitMat.register(RABBIT);
    }
}

