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

package org.diorite.material.items;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.meta.ToolMeta;
import org.diorite.material.BreakableItemMat;
import org.diorite.material.EnchantableMat;
import org.diorite.material.ItemMaterialData;
import org.diorite.material.ToolData;
import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;

/**
 * Represents a tool item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Tool durability types should be cached
 */
public abstract class ToolMat extends ItemMaterialData implements BreakableItemMat, EnchantableMat
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

    {
        this.metaType = ToolMeta.class;
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

    /**
     * Returns properties of tool defined by this item.
     *
     * @return properties of this tool.
     */
    public ToolData getProperties()
    {
        return this.toolMaterial.getProperties(this.toolType);
    }

    @Override
    public int getDurability()
    {
        return this.getType();
    }

    @Override
    public int getBaseDurability()
    {
        return this.toolMaterial.getBaseDurability();
    }

    @Override
    public int getEnchantability()
    {
        return this.toolMaterial.getEnchantability();
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("properties", this.getProperties()).toString();
    }
}
