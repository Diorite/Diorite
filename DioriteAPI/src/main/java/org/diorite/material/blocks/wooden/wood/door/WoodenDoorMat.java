package org.diorite.material.blocks.wooden.wood.door;

import org.diorite.material.blocks.DoorMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;

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

    @Override
    public WoodenDoorMat getWoodType(final WoodTypeMat woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_DOOR;
            case SPRUCE:
                return SPRUCE_DOOR;
            case BIRCH:
                return BIRCH_DOOR;
            case JUNGLE:
                return JUNGLE_DOOR;
            case ACACIA:
                return ACACIA_DOOR;
            case DARK_OAK:
                return DARK_OAK_DOOR;
            default:
                return null;
        }
    }
}
