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

package org.diorite.inventory.item;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.ItemFactory;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.material.Material;

/**
 * Interface for item stack representation.
 */
public class BaseItemStack implements ItemStack
{
    protected Material material;
    protected int      amount;
    protected ItemMeta itemMeta;

    public BaseItemStack(final Material material, final int amount)
    {
        Validate.notNull(material, "Material can't be null.");
        this.material = material.ensureValidInventoryItem();
        Validate.notNull(material, "Material can't be null.");
        this.amount = (this.material == null) ? 0 : amount;
    }

    public BaseItemStack(final Material material)
    {
        this(material, 1);
    }

    public BaseItemStack(final ItemStack item)
    {
        this(item.getMaterial(), item.getAmount());
        this.itemMeta = item.getItemMeta().clone();
    }

    @Override
    public Material getMaterial()
    {
        return this.material;
    }

    @Override
    public void setMaterial(final Material material)
    {
        this.material = material.ensureValidInventoryItem();
        if (this.material == null)
        {
            this.amount = 0;
            this.itemMeta = null;
        }
    }

    @Override
    public ItemMeta getItemMeta()
    {
        final ItemFactory fac = Diorite.getCore().getItemFactory();
        if (this.itemMeta == null)
        {
            fac.construct(this.material).apply(this);
        }
        if (! this.itemMeta.getClass().equals(fac.construct(this.material.getMetaType()).getClass()))
        {
            fac.construct(this.material, this.itemMeta.getNbtData()).apply(this);
        }
        return this.itemMeta;
    }

    @Override
    public boolean hasItemMeta()
    {
        return this.itemMeta != null;
    }

    @Override
    public void setItemMeta(final ItemMeta itemMeta)
    {
        if (itemMeta != null)
        {
            final ItemFactory fac = Diorite.getCore().getItemFactory();
            if (! itemMeta.getClass().equals(fac.construct(this.material.getMetaType()).getClass()))
            {
                fac.construct(this.material, itemMeta.getNbtData()).apply(this);
            }
        }
        this.itemMeta = itemMeta;
    }

    @Override
    public int getAmount()
    {
        return this.amount;
    }

    @Override
    public void setAmount(final int amount)
    {
        this.amount = amount;
    }

//    @Override
//    public void update()
//    {
//        // TODO
//    }

    @Override
    public boolean isAir()
    {
        return (this.material == null) || this.material.simpleEquals(Material.AIR);
    }

    @Override
    public boolean isValid()
    {
        return this.amount <= this.material.getMaxStack();
    }

    @Override
    public boolean isSimilar(final ItemStack b)
    {
        if (b == null)
        {
            return this.material.equals(Material.AIR);
        }
        if (! this.material.equals(b.getMaterial()))
        {
            return false;
        }
        if (this.itemMeta == null)
        {
            return b.getItemMeta().isEmpty();
        }
        return this.itemMeta.equals(b.getItemMeta());
    }

    @Override
    public BaseItemStack split(final int size)
    {
        if (size > this.amount)
        {
            throw new IllegalArgumentException();
        }

        if (this.amount == 1)
        {
            return null;
        }

        final BaseItemStack temp = new BaseItemStack(this);

        this.amount -= size;
        temp.setAmount(size);

        return temp;
    }

    @Override
    public BaseItemStack combine(final ItemStack other)
    {
        if (! this.isSimilar(other))
        {
            throw new IllegalArgumentException("Items must be similar to combine them.");
        }

        final int maxStack = this.material.getMaxStack();
        if ((this.amount + other.getAmount()) > maxStack)
        {
            final int pendingItems = (this.amount + other.getAmount()) - maxStack;
            this.amount = maxStack;

            final BaseItemStack temp = new BaseItemStack(this);
            temp.setAmount(pendingItems);
            return temp;
        }
        else
        {
            this.amount += other.getAmount();
            return null;
        }
    }

    @Override
    public ItemStack addFrom(final ItemStack other, final int amount)
    {
        if (amount > other.getAmount())
        {
            throw new IllegalArgumentException("amount to conbine can't be bigger than amount of items in stack.");
        }
        if (! this.isSimilar(other))
        {
            throw new IllegalArgumentException("Items must be similar to combine them.");
        }

        final int maxStack = this.material.getMaxStack();
        if ((this.amount + amount) > maxStack)
        {
            final int pendingItems = (this.amount + amount) - maxStack;
            this.amount = maxStack;

            final ItemStack temp = new BaseItemStack(this);
            temp.setAmount(pendingItems);
            return temp;
        }
        else
        {
            this.amount += amount;

            other.setAmount(other.getAmount() - amount);
            return null;
        }
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public BaseItemStack clone()
    {
        return new BaseItemStack(this);
    }

    @Override
    public int hashCode()
    {
        int result = (this.material != null) ? this.material.hashCode() : 0;
        result = (31 * result) + this.amount;
        result = (31 * result) + ((this.itemMeta != null) ? this.itemMeta.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BaseItemStack))
        {
            return false;
        }

        final BaseItemStack itemStack = (BaseItemStack) o;

        return (this.amount == itemStack.amount) && ! ((this.material != null) ? ! this.material.equals(itemStack.material) : (itemStack.material != null)) && ((this.itemMeta == null) || this.itemMeta.equals(itemStack.itemMeta));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("material", this.material).append("amount", this.amount).append("itemMeta", this.itemMeta).toString();
    }

}
