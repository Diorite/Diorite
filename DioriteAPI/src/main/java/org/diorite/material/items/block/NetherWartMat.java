package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class NetherWartMat extends ItemMaterialData implements PlaceableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final NetherWartMat NETHER_WART = new NetherWartMat();

    private static final Map<String, NetherWartMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<NetherWartMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected NetherWartMat()
    {
        super("NETHER_WART", 372, "minecraft:nether_wart", "NETHER_WART", (short) 0x00);
    }

    protected NetherWartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected NetherWartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public NetherWartMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public NetherWartMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of NetherWart sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherWart or null
     */
    public static NetherWartMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of NetherWart sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherWart or null
     */
    public static NetherWartMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherWartMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherWartMat[] types()
    {
        return NetherWartMat.netherWartTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherWartMat[] netherWartTypes()
    {
        return byID.values(new NetherWartMat[byID.size()]);
    }

    static
    {
        NetherWartMat.register(NETHER_WART);
    }
}

