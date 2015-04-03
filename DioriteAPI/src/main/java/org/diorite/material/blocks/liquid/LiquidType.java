package org.diorite.material.blocks.liquid;

public enum LiquidType
{
    NORMAL,
    STILL;

    public boolean isStill()
    {
        return this == STILL;
    }

    public boolean isNormal()
    {
        return this == NORMAL;
    }
}
