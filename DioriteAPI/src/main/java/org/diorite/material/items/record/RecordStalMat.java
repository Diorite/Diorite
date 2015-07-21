package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordStalMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RecordStalMat RECORD_STAL = new RecordStalMat();

    private static final Map<String, RecordStalMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordStalMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RecordStalMat()
    {
        super("stal", 2263);
    }

    protected RecordStalMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordStalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordStalMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordStalMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordStal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordStal or null
     */
    public static RecordStalMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordStal sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordStal or null
     */
    public static RecordStalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordStalMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordStalMat[] types()
    {
        return RecordStalMat.recordStalTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordStalMat[] recordStalTypes()
    {
        return byID.values(new RecordStalMat[byID.size()]);
    }

    static
    {
        RecordStalMat.register(RECORD_STAL);
    }
}

