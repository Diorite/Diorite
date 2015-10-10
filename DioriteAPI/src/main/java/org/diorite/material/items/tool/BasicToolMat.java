package org.diorite.material.items.tool;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BasicToolData;
import org.diorite.material.EnchantableMat;
import org.diorite.material.ItemMaterialData;

/**
 * Represents a tool item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Tool durability types should be cached
 */
@SuppressWarnings("JavaDoc")
public abstract class BasicToolMat extends ItemMaterialData implements BreakableItemMat, EnchantableMat
{
    /**
     * Instance of {@link BasicToolData} that contains all basic parameters of this tool.
     */
    protected final BasicToolData toolData;

    protected BasicToolMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.toolData = toolData;
    }

    protected BasicToolMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final BasicToolData toolData)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.toolData = toolData;
    }

    /**
     * Returns instance of {@link BasicToolData} that contains all basic parameters of this tool.
     *
     * @return instance of {@link BasicToolData} that contains all basic parameters of this tool.
     */
    public BasicToolData getToolData()
    {
        return this.toolData;
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
    public int getBaseDurability()
    {
        return this.toolData.getBaseDurability();
    }

    @Override
    public int getEnchantability()
    {
        return this.toolData.getEnchantability();
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("toolData", this.toolData).toString();
    }
}
