package org.diorite.material.items.block;

import java.util.Map;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Redstone Repeater Item' item material in minecraft. <br>
 * ID of material: 356 <br>
 * String ID of material: minecraft:repeater <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class RedstoneRepeaterItemMat extends ItemMaterialData implements PlaceableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneRepeaterItemMat REDSTONE_REPEATER_ITEM = new RedstoneRepeaterItemMat();

    private static final Map<String, RedstoneRepeaterItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<RedstoneRepeaterItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterItemMat()
    {
        super("REDSTONE_REPEATER_ITEM", 356, "minecraft:repeater", "REDSTONE_REPEATER_ITEM", (short) 0x00);
    }

    protected RedstoneRepeaterItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected RedstoneRepeaterItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public RedstoneRepeaterItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public RedstoneRepeaterItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of RedstoneRepeaterItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneRepeaterItem or null
     */
    public static RedstoneRepeaterItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of RedstoneRepeaterItem sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneRepeaterItem or null
     */
    public static RedstoneRepeaterItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneRepeaterItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneRepeaterItemMat[] types()
    {
        return RedstoneRepeaterItemMat.redstoneRepeaterItemTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static RedstoneRepeaterItemMat[] redstoneRepeaterItemTypes()
    {
        return byID.values(new RedstoneRepeaterItemMat[byID.size()]);
    }

    static
    {
        RedstoneRepeaterItemMat.register(REDSTONE_REPEATER_ITEM);
    }
}

