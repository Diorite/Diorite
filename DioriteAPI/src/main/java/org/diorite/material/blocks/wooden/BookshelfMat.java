package org.diorite.material.blocks.wooden;

import java.util.Map;

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Bookshelf" and all its subtypes.
 */
public class BookshelfMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BookshelfMat BOOKSHELF = new BookshelfMat();

    private static final Map<String, BookshelfMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BookshelfMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BookshelfMat()
    {
        super("BOOKSHELF", 47, "minecraft:bookshelf", "BOOKSHELF", (byte) 0x00, 1.5f, 7.5f);
    }

    protected BookshelfMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public BookshelfMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BookshelfMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Bookshelf sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Bookshelf or null
     */
    public static BookshelfMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Bookshelf sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Bookshelf or null
     */
    public static BookshelfMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BookshelfMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BookshelfMat[] types()
    {
        return BookshelfMat.bookshelfTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BookshelfMat[] bookshelfTypes()
    {
        return byID.values(new BookshelfMat[byID.size()]);
    }

    static
    {
        BookshelfMat.register(BOOKSHELF);
    }
}
