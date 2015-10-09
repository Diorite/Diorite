package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class Record11Mat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final Record11Mat RECORD_11 = new Record11Mat();

    private static final Map<String, Record11Mat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<Record11Mat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected Record11Mat()
    {
        super("11", 2266);
    }

    protected Record11Mat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected Record11Mat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public Record11Mat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public Record11Mat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Record11 sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Record11 or null
     */
    public static Record11Mat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Record11 sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Record11 or null
     */
    public static Record11Mat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Record11Mat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public Record11Mat[] types()
    {
        return Record11Mat.record11Types();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static Record11Mat[] record11Types()
    {
        return byID.values(new Record11Mat[byID.size()]);
    }

    static
    {
        Record11Mat.register(RECORD_11);
    }
}

