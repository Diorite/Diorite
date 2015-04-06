package org.diorite.material.blocks.wooden.wood.fence;

import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenFence extends Wood
{
    public WoodenFence(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType);
    }

    public WoodenFence(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, (byte) 0, woodType);
    }

    public WoodenFence(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    public WoodenFence(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public WoodenFence getWoodType(final WoodType woodType)
    {
        switch (woodType)
        {
            case OAK:
                return null;
            case SPRUCE:
                return null;
            case BIRCH:
                return null;
            case JUNGLE:
                return null;
            case ACACIA:
                return null;
            case DARK_OAK:
                return null;
            default:
                return null;
        }
    }
}
