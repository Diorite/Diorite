package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class WrittenBookMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WrittenBookMat WRITTEN_BOOK = new WrittenBookMat();

    private static final Map<String, WrittenBookMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<WrittenBookMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected WrittenBookMat()
    {
        super("WRITTEN_BOOK", 387, "minecraft:written_book", 16, "WRITTEN_BOOK", (short) 0x00);
    }

    protected WrittenBookMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, WRITTEN_BOOK.getMaxStack(), typeName, type);
    }

    protected WrittenBookMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public WrittenBookMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public WrittenBookMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of WrittenBook sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WrittenBook or null
     */
    public static WrittenBookMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of WrittenBook sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WrittenBook or null
     */
    public static WrittenBookMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WrittenBookMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WrittenBookMat[] types()
    {
        return WrittenBookMat.writtenBookTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static WrittenBookMat[] writtenBookTypes()
    {
        return byID.values(new WrittenBookMat[byID.size()]);
    }

    static
    {
        WrittenBookMat.register(WRITTEN_BOOK);
    }
}

