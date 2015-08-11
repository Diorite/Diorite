package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordChripMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RecordChripMat RECORD_CHRIP = new RecordChripMat();

    private static final Map<String, RecordChripMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordChripMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected RecordChripMat()
    {
        super("chrip", 2259);
    }

    protected RecordChripMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordChripMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordChripMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordChripMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordChrip sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordChrip or null
     */
    public static RecordChripMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordChrip sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordChrip or null
     */
    public static RecordChripMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordChripMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordChripMat[] types()
    {
        return RecordChripMat.recordChripTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordChripMat[] recordChripTypes()
    {
        return byID.values(new RecordChripMat[byID.size()]);
    }

    static
    {
        RecordChripMat.register(RECORD_CHRIP);
    }
}

