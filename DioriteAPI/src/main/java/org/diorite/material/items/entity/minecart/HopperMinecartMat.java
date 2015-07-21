package org.diorite.material.items.entity.minecart;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class HopperMinecartMat extends AbstractMinecartMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final HopperMinecartMat HOPPER_MINECART = new HopperMinecartMat();

    private static final Map<String, HopperMinecartMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<HopperMinecartMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected HopperMinecartMat()
    {
        super("HOPPER_MINECART", 408, "minecraft:hopper_minecart", "HOPPER_MINECART", (short) 0x00);
    }

    protected HopperMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected HopperMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public HopperMinecartMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public HopperMinecartMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of HopperMinecart sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of HopperMinecart or null
     */
    public static HopperMinecartMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of HopperMinecart sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of HopperMinecart or null
     */
    public static HopperMinecartMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HopperMinecartMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public HopperMinecartMat[] types()
    {
        return HopperMinecartMat.hopperMinecartTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static HopperMinecartMat[] hopperMinecartTypes()
    {
        return byID.values(new HopperMinecartMat[byID.size()]);
    }

    static
    {
        HopperMinecartMat.register(HOPPER_MINECART);
    }
}

