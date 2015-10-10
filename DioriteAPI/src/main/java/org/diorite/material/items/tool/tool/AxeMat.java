package org.diorite.material.items.tool.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.material.items.tool.ToolMat;

/**
 * Abstract class for all axe-based tools
 */
@SuppressWarnings("JavaDoc")
public abstract class AxeMat extends ToolMat
{
    protected AxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.AXE);
    }

    protected AxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.AXE);
    }

    protected AxeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected AxeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract AxeMat[] types();

    @Override
    public abstract AxeMat getType(final String type);

    @Override
    public abstract AxeMat getType(final int type);

    @Override
    public abstract AxeMat increaseDurability();

    @Override
    public abstract AxeMat decreaseDurability();

    @Override
    public abstract AxeMat setDurability(final int durability);
}
