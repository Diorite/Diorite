package org.diorite.material.items.block.door;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.SimpleEnumMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class WoodenDoorItemMat extends DoorItemMat
{
    protected final WoodTypeMat woodType;

    protected WoodenDoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final WoodTypeMat woodType)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.woodType = woodType;
    }

    protected WoodenDoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final WoodTypeMat woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.woodType = woodType;
    }

    private static final SimpleEnumMap<WoodTypeMat, WoodenDoorItemMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    /**
     * Returns wood type of this door.
     *
     * @return wood type of this door.
     */
    public WoodTypeMat getWoodType()
    {
        return this.woodType;
    }

    /**
     * Returns doors made of selected wood type.
     *
     * @param woodType type of wood.
     *
     * @return doors made of selected wood type.
     */
    public WoodenDoorItemMat getWoodType(final WoodTypeMat woodType)
    {
        return types.get(woodType);
    }

    /**
     * Returns sub-type of {@link WoodenDoorItemMat}, based on {@link WoodTypeMat}.
     *
     * @param woodType {@link WoodTypeMat} of WoodenFence
     *
     * @return sub-type of {@link WoodenDoorItemMat}.
     */
    public static WoodenDoorItemMat getWoodenDoorItem(final WoodTypeMat woodType)
    {
        return types.get(woodType);
    }

    /**
     * Register new wood type to one of door items, like OAK to OAK_DOOR_ITEM.
     *
     * @param type type of wood.
     * @param mat  fence material.
     */
    public static void registerWoodType(final WoodTypeMat type, final WoodenDoorItemMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodTypeMat.OAK, OAK_DOOR_ITEM);
        registerWoodType(WoodTypeMat.SPRUCE, SPRUCE_DOOR_ITEM);
        registerWoodType(WoodTypeMat.BIRCH, BIRCH_DOOR_ITEM);
        registerWoodType(WoodTypeMat.JUNGLE, JUNGLE_DOOR_ITEM);
        registerWoodType(WoodTypeMat.ACACIA, ACACIA_DOOR_ITEM);
        registerWoodType(WoodTypeMat.DARK_OAK, DARK_OAK_DOOR_ITEM);
    }
}
