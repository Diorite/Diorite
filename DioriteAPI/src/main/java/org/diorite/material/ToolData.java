package org.diorite.material;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents tool properties.
 */
public class ToolData
{
    protected final ToolMaterial toolMaterial;
    protected final ToolType     toolType;
    protected final float        damage;

    /**
     * Construct new tool properties.
     *
     * @param toolMaterial material of tool.
     * @param toolType     type of tool.
     * @param damage       damage of tool.
     */
    public ToolData(final ToolMaterial toolMaterial, final ToolType toolType, final float damage)
    {
        Validate.notNull(toolMaterial, "Tool material can't be null.");
        Validate.notNull(toolType, "Tool type can't be null.");
        this.toolMaterial = toolMaterial;
        this.toolType = toolType;
        this.damage = damage;
    }

    /**
     * Returns attack damage of this tool.
     *
     * @return attack damage of this tool.
     */
    public float getDamage()
    {
        return this.damage;
    }

    /**
     * Returns {@link ToolMaterial} defined by this properties.
     *
     * @return {@link ToolMaterial} defined by this properties.
     */
    public ToolMaterial getToolMaterial()
    {
        return this.toolMaterial;
    }

    /**
     * Returns {@link ToolType} defined by this properties.
     *
     * @return {@link ToolType} defined by this properties.
     */
    public ToolType getToolType()
    {
        return this.toolType;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ToolData))
        {
            return false;
        }

        final ToolData toolData = (ToolData) o;

        return (Float.compare(toolData.damage, this.damage) == 0) && this.toolMaterial.equals(toolData.toolMaterial) && toolType.equals(toolData.toolType);

    }

    @Override
    public int hashCode()
    {
        int result = this.toolMaterial.hashCode();
        result = 31 * result + this.toolType.hashCode();
        result = 31 * result + (this.damage != + 0.0f ? Float.floatToIntBits(this.damage) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("toolMaterial", this.toolMaterial).append("toolType", this.toolType).append("damage", this.damage).toString();
    }
}
