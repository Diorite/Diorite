package org.diorite.material.blocks.wooden.wood.stairs;

import org.diorite.material.blocks.Stairs;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenStairs extends Wood implements Stairs
{
    public WoodenStairs(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType);
    }

    public WoodenStairs(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, (byte) 0, woodType);
    }

    public WoodenStairs(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    public WoodenStairs(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public WoodenStairs getWoodType(final WoodType woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_STAIRS;
            case SPRUCE:
                return SPRUCE_STAIRS;
            case BIRCH:
                return BIRCH_STAIRS;
            case JUNGLE:
                return JUNGLE_STAIRS;
            case ACACIA:
                return ACACIA_STAIRS;
            case DARK_OAK:
                return DARK_OAK_STAIRS;
            default:
                return null;
        }
    }
}
