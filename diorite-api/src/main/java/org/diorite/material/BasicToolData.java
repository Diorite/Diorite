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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents tool properties.
 */
public class BasicToolData
{
    /**
     * Durability of this tool.
     */
    protected final int baseDurability;
    /**
     * Enchantability level of this tool, used when enchanting.
     */
    protected final int enchantability;

    /**
     * Construct new tool properties.
     *
     * @param baseDurability base durability of tool.
     * @param enchantability enchantability of tool.
     */
    public BasicToolData(final int baseDurability, final int enchantability)
    {
        this.baseDurability = baseDurability;
        this.enchantability = enchantability;
    }

    /**
     * Construct new tool properties with enchantability of 1.
     *
     * @param baseDurability base durability of tool.
     */
    public BasicToolData(final int baseDurability)
    {
        this.baseDurability = baseDurability;
        this.enchantability = 1;
    }

    /**
     * Construct new tool properties as copy of other one.
     *
     * @param old properties to copy.
     */
    public BasicToolData(final BasicToolData old)
    {
        this.baseDurability = old.getBaseDurability();
        this.enchantability = old.getEnchantability();
    }

    /**
     * Returns base durability of this tool.
     *
     * @return base durability of this tool.
     */
    public int getBaseDurability()
    {
        return this.baseDurability;
    }

    /**
     * Returns enchantability level of tool, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of tool.
     */
    public int getEnchantability()
    {
        return this.enchantability;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BasicToolData))
        {
            return false;
        }

        final BasicToolData that = (BasicToolData) o;

        return (this.baseDurability == that.baseDurability) && (this.enchantability == that.enchantability);

    }

    @Override
    public int hashCode()
    {
        int result = this.baseDurability;
        result = (31 * result) + this.enchantability;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("baseDurability", this.baseDurability).append("enchantability", this.enchantability).toString();
    }
}
