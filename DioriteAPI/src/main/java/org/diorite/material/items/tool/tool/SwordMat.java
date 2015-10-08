package org.diorite.material.items.tool.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.material.items.tool.ToolMat;

public abstract class SwordMat extends ToolMat
{
    protected SwordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.SWORD);
    }

    protected SwordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.SWORD);
    }

    protected SwordMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected SwordMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract SwordMat[] types();

    @Override
    public abstract SwordMat getType(final String type);

    @Override
    public abstract SwordMat getType(final int type);

    @Override
    public abstract SwordMat increaseDurability();

    @Override
    public abstract SwordMat decreaseDurability();

    @Override
    public abstract SwordMat setDurability(final int durability);
}
