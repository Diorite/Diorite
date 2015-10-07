package org.diorite.material.items.tool;

import org.diorite.material.ItemMaterialData;

/**
 * Represents a tool item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Tool durability types should be cached
 */
public abstract class BasicToolMat extends ItemMaterialData implements BreakableItemMat
{
    protected BasicToolMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
    }

    protected BasicToolMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public boolean isTool()
    {
        return true;
    }

    @Override
    public int getDurability()
    {
        return this.getType();
    }

    @Override
    public abstract BasicToolMat getType(final String type);

    @Override
    public abstract BasicToolMat getType(final int type);

    @Override
    public abstract BasicToolMat increaseDurability();

    @Override
    public abstract BasicToolMat decreaseDurability();

    @Override
    public abstract BasicToolMat setDurability(final int durability);

    @Override
    public abstract BasicToolMat[] types();
}
