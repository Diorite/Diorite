package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordBlocksMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RecordBlocksMat RECORD_BLOCKS = new RecordBlocksMat();

    private static final Map<String, RecordBlocksMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordBlocksMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected RecordBlocksMat()
    {
        super("blocks", 2258);
    }

    protected RecordBlocksMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordBlocksMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordBlocksMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordBlocksMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordBlocks sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordBlocks or null
     */
    public static RecordBlocksMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordBlocks sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordBlocks or null
     */
    public static RecordBlocksMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordBlocksMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordBlocksMat[] types()
    {
        return RecordBlocksMat.recordBlocksTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static RecordBlocksMat[] recordBlocksTypes()
    {
        return byID.values(new RecordBlocksMat[byID.size()]);
    }

    static
    {
        RecordBlocksMat.register(RECORD_BLOCKS);
    }
}

