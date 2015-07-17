package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordWaitMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final RecordWaitMat RECORD_WAIT = new RecordWaitMat();

    private static final Map<String, RecordWaitMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordWaitMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RecordWaitMat()
    {
        super("wait", 2267);
    }

    protected RecordWaitMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordWaitMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordWaitMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordWaitMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordWait sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordWait or null
     */
    public static RecordWaitMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordWait sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordWait or null
     */
    public static RecordWaitMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordWaitMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordWaitMat[] types()
    {
        return RecordWaitMat.recordWaitTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordWaitMat[] recordWaitTypes()
    {
        return byID.values(new RecordWaitMat[byID.size()]);
    }

    static
    {
        RecordWaitMat.register(RECORD_WAIT);
    }
}

