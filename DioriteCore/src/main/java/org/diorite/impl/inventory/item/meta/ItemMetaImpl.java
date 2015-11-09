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

package org.diorite.impl.inventory.item.meta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.nbt.NbtTagCompound;

public abstract class ItemMetaImpl implements ItemMeta
{
    protected static final String SEP = ".";

    protected NbtTagCompound tag;
    protected boolean        dirty;

    public ItemMetaImpl(final NbtTagCompound tag)
    {
        this.tag = tag;
    }

    public NbtTagCompound getRawData()
    {
        return this.tag;
    }

    protected boolean removeIfNeeded(final String name, final Object value)
    {
        if (value == null)
        {
            this.removeTag(name);
            return true;
        }
        return false;
    }

    private void removeTag(final String name)
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(name);
        this.checkTag(false);
        this.setDirty();
    }

    protected boolean removeIfNeeded(final String name, final boolean isNull)
    {
        if (isNull)
        {
            this.removeTag(name);
            return true;
        }
        return false;
    }

    protected void setStringTag(final String name, final String value)
    {
        this.checkTag(true);
        this.tag.setString(name, value);
        this.setDirty();
    }

    @Override
    public boolean isEmpty()
    {
        return (this.tag == null) || this.tag.isEmpty();
    }

    @Override
    public NbtTagCompound getNbtData()
    {
        if (this.tag == null)
        {
            return null;
        }
        return this.tag.clone();
    }

    @Override
    public void setNbtData(final NbtTagCompound tag)
    {
        this.tag = tag.clone();
        this.setDirty();
    }

    @Override
    public abstract ItemMeta clone();

    @Override
    public void checkTag(final boolean get)
    {
        if (get)
        {
            if (this.tag == null)
            {
                this.tag = new NbtTagCompound("tag");
                this.setDirty();
            }
        }
        else if ((this.tag != null) && this.tag.isEmpty())
        {
            this.tag = null;
            this.setDirty();
        }
    }

    @Override
    public boolean isDirty()
    {
        return this.dirty;
    }

    @Override
    public boolean setDirty(final boolean dirty)
    {
        final boolean old = this.dirty;
        this.dirty = dirty;
        return old;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ItemMeta))
        {
            return false;
        }

        final ItemMeta itemMeta = (ItemMeta) o;
        this.checkTag(false);
        itemMeta.checkTag(false);
        if (this.isEmpty() && itemMeta.isEmpty())
        {
            return true;
        }
        if (itemMeta instanceof ItemMetaImpl)
        {
            final ItemMetaImpl itemMetaImpl = (ItemMetaImpl) itemMeta;
            return ! ((this.tag != null) ? ! this.tag.equals(itemMetaImpl.tag) : (itemMetaImpl.tag != null));
        }
        return ! ((this.tag != null) ? ! this.tag.equals(itemMeta.getNbtData()) : (itemMeta.getNbtData() != null));
    }

    @Override
    public int hashCode()
    {
        return ((this.tag != null) && ! this.tag.isEmpty()) ? this.tag.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tag", this.tag).toString();
    }
}
