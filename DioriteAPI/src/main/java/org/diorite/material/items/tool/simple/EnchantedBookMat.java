package org.diorite.material.items.tool.simple;

import java.util.Map;

import org.diorite.material.EnchantableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class EnchantedBookMat extends ItemMaterialData implements EnchantableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final EnchantedBookMat ENCHANTED_BOOK = new EnchantedBookMat();

    private static final Map<String, EnchantedBookMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<EnchantedBookMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected EnchantedBookMat()
    {
        super("ENCHANTED_BOOK", 403, "minecraft:enchanted_book", 1, "ENCHANTED_BOOK", (short) 0x00);
    }

    protected EnchantedBookMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, ENCHANTED_BOOK.getMaxStack(), typeName, type);
    }

    protected EnchantedBookMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public EnchantedBookMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public EnchantedBookMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @Override
    public int getEnchantability()
    {
        return 1;
    }

    /**
     * Returns one of EnchantedBook sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EnchantedBook or null
     */
    public static EnchantedBookMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of EnchantedBook sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EnchantedBook or null
     */
    public static EnchantedBookMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EnchantedBookMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EnchantedBookMat[] types()
    {
        return EnchantedBookMat.enchantedBookTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static EnchantedBookMat[] enchantedBookTypes()
    {
        return byID.values(new EnchantedBookMat[byID.size()]);
    }

    static
    {
        EnchantedBookMat.register(ENCHANTED_BOOK);
    }
}

