package org.diorite.material.items.tool.basic;

import org.diorite.material.BasicToolData;
import org.diorite.material.items.tool.BasicToolMat;

public abstract class AbstractBowMat extends BasicToolMat
{
    protected AbstractBowMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, typeName, type, toolData);
    }

    protected AbstractBowMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolData);
    }

    @Override
    public abstract AbstractBowMat getType(final String type);

    @Override
    public abstract AbstractBowMat getType(final int type);

    @Override
    public abstract AbstractBowMat increaseDurability();

    @Override
    public abstract AbstractBowMat decreaseDurability();

    @Override
    public abstract AbstractBowMat setDurability(final int durability);

    @Override
    public abstract AbstractBowMat[] types();
}

