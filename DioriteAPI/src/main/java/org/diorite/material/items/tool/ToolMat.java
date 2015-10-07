package org.diorite.material.items.tool;

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;

/**
 * Represents a tool item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Tool durability types should be cached
 */
public abstract class ToolMat extends BasicToolMat
{
    protected final ToolMaterial toolMaterial;
    protected final ToolType     toolType;

    protected ToolMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.toolMaterial = toolMaterial;
        this.toolType = toolType;
    }

    protected ToolMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.toolMaterial = toolMaterial;
        this.toolType = toolType;
    }

    /**
     * Retruns {@link ToolMaterial} defined by this item.
     *
     * @return {@link ToolMaterial} defined by this item.
     */
    public ToolMaterial getToolMaterial()
    {
        return this.toolMaterial;
    }

    /**
     * Retruns {@link ToolType} defined by this item.
     *
     * @return {@link ToolType} defined by this item.
     */
    public ToolType getToolType()
    {
        return this.toolType;
    }

    @Override
    public abstract ToolMat getType(final String type);

    @Override
    public abstract ToolMat getType(final int type);

    @Override
    public abstract ToolMat increaseDurability();

    @Override
    public abstract ToolMat decreaseDurability();

    @Override
    public abstract ToolMat setDurability(final int durability);

    @Override
    public abstract ToolMat[] types();
}
