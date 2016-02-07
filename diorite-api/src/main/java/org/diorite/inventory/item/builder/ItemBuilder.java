/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inventory.item.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.material.Material;

/**
 * Simple builder for item stack.
 */
public class ItemBuilder
{
    /**
     * Wrapped item stack used by builder.
     */
    protected ItemStack itemStack;

    /**
     * Construct new item builder for given material.
     *
     * @param material material of new item.
     */
    protected ItemBuilder(final Material material)
    {
        this.itemStack = new BaseItemStack(material);
    }

    /**
     * Construct new item builder for given item stack.
     *
     * @param itemStack source item stack to copy data to new builder.
     */
    protected ItemBuilder(final ItemStack itemStack)
    {
        this.itemStack = itemStack.clone();
    }

    /**
     * Set material of builder.
     *
     * @param material new material of item.
     *
     * @return builder for method chains.
     */
    public ItemBuilder material(final Material material)
    {
        this.itemStack.setMaterial(material);
        return this;
    }

    /**
     * Set material of item.
     *
     * @param src source item to copy material from it.
     *
     * @return builder for method chains.
     */
    public ItemBuilder material(final ItemStack src)
    {
        this.itemStack.setMaterial(src.getMaterial());
        return this;
    }

    /**
     * Set amount of material of item.
     *
     * @param amount new amount of item.
     *
     * @return builder for method chains.
     */
    public ItemBuilder amount(final int amount)
    {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set amount of material of item.
     *
     * @param src source item to copy amount from it.
     *
     * @return builder for method chains.
     */
    public ItemBuilder amount(final ItemStack src)
    {
        this.itemStack.setAmount(src.getAmount());
        return this;
    }

    /**
     * Set meta data of this item.
     *
     * @param builder builder of meta data.
     *
     * @return builder for method chains.
     */
    public ItemBuilder meta(final IMetaBuilder<?, ?> builder)
    {
        this.itemStack.setItemMeta(builder.build());
        return this;
    }

    /**
     * Set meta data of this item.
     *
     * @param meta meta data to copy.
     *
     * @return builder for method chains.
     */
    public ItemBuilder meta(final ItemMeta meta)
    {
        this.itemStack.setItemMeta(meta.clone());
        return this;
    }

    /**
     * Returns created item stack from this builder.
     *
     * @return created item stack from this builder.
     */
    public ItemStack build()
    {
        return this.itemStack.clone();
    }

    /**
     * Start builder of itemstack of given material.
     *
     * @param material material of item stack to build.
     *
     * @return created item builder.
     */
    public static ItemBuilder start(final Material material)
    {
        return new ItemBuilder(material);
    }

    /**
     * Start builder based on given item stack
     *
     * @param itemStack basic item stack, builder will copy data from it.
     *
     * @return created item builder.
     */
    public static ItemBuilder start(final ItemStack itemStack)
    {
        return new ItemBuilder(itemStack);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("itemStack", this.itemStack).toString();
    }
}
