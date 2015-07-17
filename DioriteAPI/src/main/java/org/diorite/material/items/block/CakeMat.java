package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class CakeMat extends ItemMaterialData implements PlaceableItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final CakeMat CAKE = new CakeMat();

    private static final Map<String, CakeMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CakeMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected CakeMat()
    {
        super("CAKE", 354, "minecraft:cake", "CAKE", (short) 0x00);
    }

    protected CakeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected CakeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public CakeMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CakeMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Cake sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cake or null
     */
    public static CakeMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Cake sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cake or null
     */
    public static CakeMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CakeMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CakeMat[] types()
    {
        return CakeMat.cakeTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CakeMat[] cakeTypes()
    {
        return byID.values(new CakeMat[byID.size()]);
    }

    static
    {
        CakeMat.register(CAKE);
    }
}

