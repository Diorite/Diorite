package org.diorite.material.items.tool.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.material.items.tool.ToolMat;

public abstract class ShovelMat extends ToolMat
{
    protected ShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.SHOVEL);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.SHOVEL);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract ShovelMat[] types();

    @Override
    public abstract ShovelMat getType(final String type);

    @Override
    public abstract ShovelMat getType(final int type);

    @Override
    public abstract ShovelMat increaseDurability();

    @Override
    public abstract ShovelMat decreaseDurability();

    @Override
    public abstract ShovelMat setDurability(final int durability);
}
