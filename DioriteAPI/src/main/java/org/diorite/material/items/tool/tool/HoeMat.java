package org.diorite.material.items.tool.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;
import org.diorite.material.items.tool.ToolMat;

/**
 * Abstract class for all hoe-based tools.
 */
@SuppressWarnings("JavaDoc")
public abstract class HoeMat extends ToolMat
{
    protected HoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.HOE);
    }

    protected HoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.HOE);
    }

    protected HoeMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected HoeMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract HoeMat[] types();

    @Override
    public abstract HoeMat getType(final String type);

    @Override
    public abstract HoeMat getType(final int type);

    @Override
    public abstract HoeMat increaseDurability();

    @Override
    public abstract HoeMat decreaseDurability();

    @Override
    public abstract HoeMat setDurability(final int durability);
}
