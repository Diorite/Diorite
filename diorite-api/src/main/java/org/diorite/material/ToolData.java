/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    protected final float        attackSpeed;

    /**
     * Construct new tool properties.
     *
     * @param toolMaterial material of tool.
     * @param toolType     type of tool.
     * @param damage       damage of tool.
     * @param attackSpeed  attack speed of tool.
     */
    public ToolData(final ToolMaterial toolMaterial, final ToolType toolType, final double damage, final double attackSpeed)
    {
        Validate.notNull(toolMaterial, "Tool material can't be null.");
        Validate.notNull(toolType, "Tool type can't be null.");
        this.toolMaterial = toolMaterial;
        this.toolType = toolType;
        this.damage = (float) damage;
        this.attackSpeed = (float) attackSpeed;
    }

    /**
     * Returns attack damage of this tool.
     *
     * @return attack damage of this tool.
     */
    public double getDamage()
    {
        return this.damage;
    }

    /**
     * Returns attack speed of this tool.
     *
     * @return attack speed of this tool.
     */
    public double getAttackSpeed()
    {
        return this.attackSpeed;
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

        return (Float.compare(toolData.damage, this.damage) == 0) && (Float.compare(toolData.attackSpeed, this.attackSpeed) == 0) && this.toolMaterial.equals(toolData.toolMaterial) && this.toolType.equals(toolData.toolType);
    }

    @Override
    public int hashCode()
    {
        int result = this.toolMaterial.hashCode();
        result = (31 * result) + this.toolType.hashCode();
        result = (31 * result) + ((this.damage != + 0.0f) ? Float.floatToIntBits(this.damage) : 0);
        result = (31 * result) + ((this.attackSpeed != + 0.0f) ? Float.floatToIntBits(this.attackSpeed) : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("toolMaterial", this.toolMaterial).append("toolType", this.toolType).append("damage", this.damage).toString();
    }
}
