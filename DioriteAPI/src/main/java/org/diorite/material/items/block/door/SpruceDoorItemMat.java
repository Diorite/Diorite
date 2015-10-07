package org.diorite.material.items.block.door;

import java.util.Map;

import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class SpruceDoorItemMat extends WoodenDoorItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SpruceDoorItemMat SPRUCE_DOOR_ITEM = new SpruceDoorItemMat();

    private static final Map<String, SpruceDoorItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<SpruceDoorItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected SpruceDoorItemMat()
    {
        super("SPRUCE_DOOR_ITEM", 427, "minecraft:spruce_door", "SPRUCE_DOOR_ITEM", (short) 0x00, WoodType.SPRUCE);
    }

    protected SpruceDoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    protected SpruceDoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public SpruceDoorItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public SpruceDoorItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of SpruceDoorItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceDoorItem or null
     */
    public static SpruceDoorItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of SpruceDoorItem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpruceDoorItem or null
     */
    public static SpruceDoorItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceDoorItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpruceDoorItemMat[] types()
    {
        return SpruceDoorItemMat.spruceDoorItemTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SpruceDoorItemMat[] spruceDoorItemTypes()
    {
        return byID.values(new SpruceDoorItemMat[byID.size()]);
    }

    static
    {
        SpruceDoorItemMat.register(SPRUCE_DOOR_ITEM);
    }
}

