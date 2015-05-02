package org.diorite.material.blocks.liquid;

public enum LiquidTypeMat
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
