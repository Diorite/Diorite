package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class Record13Mat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final Record13Mat RECORD_13 = new Record13Mat();

    private static final Map<String, Record13Mat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<Record13Mat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected Record13Mat()
    {
        super("13", 2256);
    }

    protected Record13Mat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected Record13Mat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public Record13Mat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public Record13Mat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Record13 sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Record13 or null
     */
    public static Record13Mat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Record13 sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Record13 or null
     */
    public static Record13Mat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Record13Mat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public Record13Mat[] types()
    {
        return Record13Mat.record13Types();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static Record13Mat[] record13Types()
    {
        return byID.values(new Record13Mat[byID.size()]);
    }

    static
    {
        Record13Mat.register(RECORD_13);
    }
}

