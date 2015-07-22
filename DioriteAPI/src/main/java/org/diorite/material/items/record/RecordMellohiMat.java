package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordMellohiMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RecordMellohiMat RECORD_MELLOHI = new RecordMellohiMat();

    private static final Map<String, RecordMellohiMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordMellohiMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected RecordMellohiMat()
    {
        super("mellohi", 2262);
    }

    protected RecordMellohiMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordMellohiMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordMellohiMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordMellohiMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordMellohi sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordMellohi or null
     */
    public static RecordMellohiMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordMellohi sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordMellohi or null
     */
    public static RecordMellohiMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordMellohiMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordMellohiMat[] types()
    {
        return RecordMellohiMat.recordMellohiTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordMellohiMat[] recordMellohiTypes()
    {
        return byID.values(new RecordMellohiMat[byID.size()]);
    }

    static
    {
        RecordMellohiMat.register(RECORD_MELLOHI);
    }
}

