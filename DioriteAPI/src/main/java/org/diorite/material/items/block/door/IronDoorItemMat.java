package org.diorite.material.items.block.door;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class IronDoorItemMat extends DoorItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final IronDoorItemMat IRON_DOOR_ITEM = new IronDoorItemMat();

    private static final Map<String, IronDoorItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<IronDoorItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected IronDoorItemMat()
    {
        super("IRON_DOOR_ITEM", 330, "minecraft:iron_door", "IRON_DOOR_ITEM", (short) 0x00);
    }

    protected IronDoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected IronDoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public IronDoorItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public IronDoorItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of IronDoorItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronDoorItem or null
     */
    public static IronDoorItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of IronDoorItem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronDoorItem or null
     */
    public static IronDoorItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronDoorItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronDoorItemMat[] types()
    {
        return IronDoorItemMat.ironDoorItemTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronDoorItemMat[] ironDoorItemTypes()
    {
        return byID.values(new IronDoorItemMat[byID.size()]);
    }

    static
    {
        IronDoorItemMat.register(IRON_DOOR_ITEM);
    }
}

