package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class RedstoneComparatorItemMat extends ItemMaterialData implements PlaceableItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte USED_DATA_VALUES = 1;

    public static final RedstoneComparatorItemMat REDSTONE_COMPARATOR_ITEM = new RedstoneComparatorItemMat();

    private static final Map<String, RedstoneComparatorItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RedstoneComparatorItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected RedstoneComparatorItemMat()
    {
        super("REDSTONE_COMPARATOR_ITEM", 404, "minecraft:comparator", "REDSTONE_COMPARATOR_ITEM", (short) 0x00);
    }

    protected RedstoneComparatorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected RedstoneComparatorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public RedstoneComparatorItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RedstoneComparatorItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RedstoneComparatorItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneComparatorItem or null
     */
    public static RedstoneComparatorItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RedstoneComparatorItem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneComparatorItem or null
     */
    public static RedstoneComparatorItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneComparatorItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneComparatorItemMat[] types()
    {
        return RedstoneComparatorItemMat.redstoneComparatorItemTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneComparatorItemMat[] redstoneComparatorItemTypes()
    {
        return byID.values(new RedstoneComparatorItemMat[byID.size()]);
    }

    static
    {
        RedstoneComparatorItemMat.register(REDSTONE_COMPARATOR_ITEM);
    }
}

