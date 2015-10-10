package org.diorite.material.items.entity.minecart;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Tnt Minecart' item material in minecraft. <br>
 * ID of material: 407 <br>
 * String ID of material: minecraft:tnt_minecart <br>
 * Max item stack size: 1
 */
@SuppressWarnings("JavaDoc")
public class TntMinecartMat extends AbstractMinecartMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final TntMinecartMat TNT_MINECART = new TntMinecartMat();

    private static final Map<String, TntMinecartMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<TntMinecartMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected TntMinecartMat()
    {
        super("TNT_MINECART", 407, "minecraft:tnt_minecart", "TNT_MINECART", (short) 0x00);
    }

    protected TntMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected TntMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public TntMinecartMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public TntMinecartMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of TntMinecart sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TntMinecart or null
     */
    public static TntMinecartMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of TntMinecart sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TntMinecart or null
     */
    public static TntMinecartMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TntMinecartMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public TntMinecartMat[] types()
    {
        return TntMinecartMat.tntMinecartTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static TntMinecartMat[] tntMinecartTypes()
    {
        return byID.values(new TntMinecartMat[byID.size()]);
    }

    static
    {
        TntMinecartMat.register(TNT_MINECART);
    }
}

