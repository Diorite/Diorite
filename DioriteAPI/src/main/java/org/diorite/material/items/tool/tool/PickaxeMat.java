package org.diorite.material.items.tool.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.material.items.tool.ToolMat;

public abstract class PickaxeMat extends ToolMat
{
    protected PickaxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.PICKAXE);
    }

    protected PickaxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.PICKAXE);
    }

    protected PickaxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected PickaxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract PickaxeMat[] types();

    @Override
    public abstract PickaxeMat getType(final String type);

    @Override
    public abstract PickaxeMat getType(final int type);

    @Override
    public abstract PickaxeMat increaseDurability();

    @Override
    public abstract PickaxeMat decreaseDurability();

    @Override
    public abstract PickaxeMat setDurability(final int durability);
}
