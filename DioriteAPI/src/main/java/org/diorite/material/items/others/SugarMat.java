package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SugarMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SugarMat SUGAR = new SugarMat();

    private static final Map<String, SugarMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SugarMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected SugarMat()
    {
        super("SUGAR", 353, "minecraft:sugar", "SUGAR", (short) 0x00);
    }

    protected SugarMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected SugarMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public SugarMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SugarMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Sugar sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sugar or null
     */
    public static SugarMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Sugar sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sugar or null
     */
    public static SugarMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SugarMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SugarMat[] types()
    {
        return SugarMat.sugarTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static SugarMat[] sugarTypes()
    {
        return byID.values(new SugarMat[byID.size()]);
    }

    static
    {
        SugarMat.register(SUGAR);
    }
}

