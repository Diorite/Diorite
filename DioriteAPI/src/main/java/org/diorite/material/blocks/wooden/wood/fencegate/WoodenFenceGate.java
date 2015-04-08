package org.diorite.material.blocks.wooden.wood.fencegate;

import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenFenceGate extends Wood
{
    public WoodenFenceGate(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType);
    }

    public WoodenFenceGate(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, (byte) 0, woodType);
    }

    public WoodenFenceGate(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    public WoodenFenceGate(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public WoodenFenceGate getWoodType(final WoodType woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_FENCE_GATE;
            case SPRUCE:
                return SPRUCE_FENCE_GATE;
            case BIRCH:
                return BIRCH_FENCE_GATE;
            case JUNGLE:
                return JUNGLE_FENCE_GATE;
            case ACACIA:
                return ACACIA_FENCE_GATE;
            case DARK_OAK:
                return DARK_OAK_FENCE_GATE;
            default:
                return null;
        }
    }
}
