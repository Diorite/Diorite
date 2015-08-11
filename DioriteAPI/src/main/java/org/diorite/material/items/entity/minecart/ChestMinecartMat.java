package org.diorite.material.items.entity.minecart;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class ChestMinecartMat extends AbstractMinecartMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final ChestMinecartMat CHEST_MINECART = new ChestMinecartMat();

    private static final Map<String, ChestMinecartMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<ChestMinecartMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected ChestMinecartMat()
    {
        super("CHEST_MINECART", 342, "minecraft:chest_minecart", "CHEST_MINECART", (short) 0x00);
    }

    protected ChestMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ChestMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public ChestMinecartMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public ChestMinecartMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of ChestMinecart sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of ChestMinecart or null
     */
    public static ChestMinecartMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of ChestMinecart sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of ChestMinecart or null
     */
    public static ChestMinecartMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ChestMinecartMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ChestMinecartMat[] types()
    {
        return ChestMinecartMat.chestMinecartTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ChestMinecartMat[] chestMinecartTypes()
    {
        return byID.values(new ChestMinecartMat[byID.size()]);
    }

    static
    {
        ChestMinecartMat.register(CHEST_MINECART);
    }
}

