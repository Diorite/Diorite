package org.diorite.material.items.others;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Book' item material in minecraft. <br>
 * ID of material: 340 <br>
 * String ID of material: minecraft:book <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class BookMat extends ItemMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BookMat BOOK = new BookMat();

    private static final Map<String, BookMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BookMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected BookMat()
    {
        super("BOOK", 340, "minecraft:book", "BOOK", (short) 0x00);
    }

    protected BookMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected BookMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public BookMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BookMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of Book sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Book or null
     */
    public static BookMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Book sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Book or null
     */
    public static BookMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BookMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BookMat[] types()
    {
        return BookMat.bookTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static BookMat[] bookTypes()
    {
        return byID.values(new BookMat[byID.size()]);
    }

    static
    {
        BookMat.register(BOOK);
    }
}

