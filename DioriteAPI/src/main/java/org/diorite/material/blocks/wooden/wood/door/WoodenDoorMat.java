package org.diorite.material.blocks.wooden.wood.door;

import org.diorite.BlockFace;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.DoorMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenDoor-based blocks
 */
public abstract class WoodenDoorMat extends WoodMat implements DoorMat
{
    protected WoodenDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType, hardness, blastResistance);
    }

    protected WoodenDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, woodType, hardness, blastResistance);
    }

    private static final SimpleEnumMap<WoodTypeMat, WoodenDoorMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @Override
    public abstract WoodenDoorMat getType(final boolean isPowered, final boolean hingeOnRightSide);

    @Override
    public abstract WoodenDoorMat getType(final BlockFace face, final boolean isOpen);

    @Override
    public abstract WoodenDoorMat getBlockFacing(final BlockFace face) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getTopPart(final boolean top);

    @Override
    public abstract WoodenDoorMat getPowered(final boolean powered) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getOpen(final boolean open) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getHingeOnRightSide(final boolean onRightSide) throws RuntimeException;

    @Override
    public WoodenDoorMat getWoodType(final WoodTypeMat woodType)
    {
        return types.get(woodType);
    }

    /**
     * Returns one of WoodenDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param woodType         {@link WoodTypeMat} of WoodenDoor
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of WoodenDoor
     */
    public static WoodenDoorMat getWoodenDoor(final WoodTypeMat woodType, final boolean powered, final boolean hingeOnRightSide)
    {
        return types.get(woodType).getType(powered, hingeOnRightSide);
    }

    /**
     * Returns one of WoodenDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param woodType  {@link WoodTypeMat} of WoodenDoor
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of WoodenDoor
     */
    public static WoodenDoorMat getWoodenDoor(final WoodTypeMat woodType, final BlockFace blockFace, final boolean open)
    {
        return types.get(woodType).getType(blockFace, open);
    }

    /**
     * Register new wood type to one of doors, like OAK to OAK_DOOR.
     *
     * @param type type of wood.
     * @param mat  door material.
     */
    public static void registerWoodType(final WoodTypeMat type, final WoodenDoorMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodTypeMat.OAK, OAK_DOOR);
        registerWoodType(WoodTypeMat.SPRUCE, SPRUCE_DOOR);
        registerWoodType(WoodTypeMat.BIRCH, BIRCH_DOOR);
        registerWoodType(WoodTypeMat.JUNGLE, JUNGLE_DOOR);
        registerWoodType(WoodTypeMat.ACACIA, ACACIA_DOOR);
        registerWoodType(WoodTypeMat.DARK_OAK, DARK_OAK_DOOR);
    }
}
