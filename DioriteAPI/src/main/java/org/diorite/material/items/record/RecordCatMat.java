package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordCatMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final RecordCatMat RECORD_CAT = new RecordCatMat();

    private static final Map<String, RecordCatMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordCatMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RecordCatMat()
    {
        super("cat", 2257);
    }

    protected RecordCatMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordCatMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordCatMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordCatMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordCat sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordCat or null
     */
    public static RecordCatMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordCat sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordCat or null
     */
    public static RecordCatMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordCatMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordCatMat[] types()
    {
        return RecordCatMat.recordCatTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordCatMat[] recordCatTypes()
    {
        return byID.values(new RecordCatMat[byID.size()]);
    }

    static
    {
        RecordCatMat.register(RECORD_CAT);
    }
}

