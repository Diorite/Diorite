package org.diorite.material.items.record;

import java.util.Map;

import org.diorite.Sound;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RecordFarMat extends RecordMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final RecordFarMat RECORD_FAR = new RecordFarMat();

    private static final Map<String, RecordFarMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RecordFarMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RecordFarMat()
    {
        super("far", 2260);
    }

    protected RecordFarMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, typeName, type, sound);
    }

    protected RecordFarMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final Sound sound)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, sound);
    }

    @Override
    public RecordFarMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RecordFarMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RecordFar sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RecordFar or null
     */
    public static RecordFarMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RecordFar sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RecordFar or null
     */
    public static RecordFarMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RecordFarMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RecordFarMat[] types()
    {
        return RecordFarMat.recordFarTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RecordFarMat[] recordFarTypes()
    {
        return byID.values(new RecordFarMat[byID.size()]);
    }

    static
    {
        RecordFarMat.register(RECORD_FAR);
    }
}

