package org.diorite.material.items.block.door;

import java.util.Map;

import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Dark Oak Door Item' item material in minecraft. <br>
 * ID of material: 431 <br>
 * String ID of material: minecraft:dark_oak_door <br>
 * Max item stack size: 64
 */
@SuppressWarnings("JavaDoc")
public class DarkOakDoorItemMat extends WoodenDoorItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final DarkOakDoorItemMat DARK_OAK_DOOR_ITEM = new DarkOakDoorItemMat();

    private static final Map<String, DarkOakDoorItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<DarkOakDoorItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DarkOakDoorItemMat()
    {
        super("DARK_OAK_DOOR_ITEM", 431, "minecraft:dark_oak_door", "DARK_OAK_DOOR_ITEM", (short) 0x00, WoodType.DARK_OAK);
    }

    protected DarkOakDoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    protected DarkOakDoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public DarkOakDoorItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public DarkOakDoorItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of DarkOakDoorItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakDoorItem or null
     */
    public static DarkOakDoorItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of DarkOakDoorItem sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DarkOakDoorItem or null
     */
    public static DarkOakDoorItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakDoorItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DarkOakDoorItemMat[] types()
    {
        return DarkOakDoorItemMat.darkOakDoorItemTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static DarkOakDoorItemMat[] darkOakDoorItemTypes()
    {
        return byID.values(new DarkOakDoorItemMat[byID.size()]);
    }

    static
    {
        DarkOakDoorItemMat.register(DARK_OAK_DOOR_ITEM);
    }
}

