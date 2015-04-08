package org.diorite.material.blocks.wooden.wood.door;

import org.diorite.material.blocks.Door;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenDoor extends Wood implements Door
{
    public WoodenDoor(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType);
    }

    public WoodenDoor(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, (byte) 0, woodType);
    }

    public WoodenDoor(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    public WoodenDoor(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public WoodenDoor getWoodType(final WoodType woodType)
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
