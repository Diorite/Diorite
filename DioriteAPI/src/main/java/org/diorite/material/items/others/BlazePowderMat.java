package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BlazePowderMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BlazePowderMat BLAZE_POWDER = new BlazePowderMat();

    private static final Map<String, BlazePowderMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BlazePowderMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected BlazePowderMat()
    {
        super("BLAZE_POWDER", 377, "minecraft:blaze_powder", "BLAZE_POWDER", (short) 0x00);
    }

    protected BlazePowderMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BlazePowderMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BlazePowderMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BlazePowderMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of BlazePowder sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BlazePowder or null
     */
    public static BlazePowderMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of BlazePowder sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BlazePowder or null
     */
    public static BlazePowderMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BlazePowderMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BlazePowderMat[] types()
    {
        return BlazePowderMat.blazePowderTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static BlazePowderMat[] blazePowderTypes()
    {
        return byID.values(new BlazePowderMat[byID.size()]);
    }

    static
    {
        BlazePowderMat.register(BLAZE_POWDER);
    }
}

