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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.MapMeta;
import org.diorite.map.MapIcon;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;

public class MapMetaImpl extends SimpleItemMetaImpl implements MapMeta
{
    protected static final String SCALING     = "map_is_scaling";
    protected static final String DECORATIONS = "Decorations";

    public MapMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public MapMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean isScaling()
    {
        return (this.tag != null) && this.tag.getBoolean(SCALING, false);
    }

    @Override
    public void setScaling(final boolean value)
    {
        if (this.removeIfNeeded(SCALING, ! value))
        {
            return;
        }
        this.checkTag(true);
        this.tag.setBoolean(SCALING, true);
        this.setDirty();
    }

    @Override
    public Collection<MapIcon> getIcons()
    {
        if (this.tag == null)
        {
            return null;
        }
        final List<NbtTagCompound> list = this.tag.getList(DECORATIONS, NbtTagCompound.class);
        if (list == null)
        {
            return null;
        }
        return list.stream().map(MapIcon::new).collect(Collectors.toList());
    }

    @Override
    public void addIcon(final MapIcon icon)
    {
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(DECORATIONS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(DECORATIONS);
            this.tag.addTag(list);
        }
        list.add(icon.serializeToNBT());
        this.setDirty();
    }

    @Override
    public void removeIcon(final MapIcon icon)
    {
        if (this.tag == null)
        {
            return;
        }
        final NbtTagList list = this.tag.getTag(DECORATIONS, NbtTagList.class);
        if (list == null)
        {
            return;
        }
        list.remove(icon);
        if (list.isEmpty())
        {
            this.tag.remove(DECORATIONS);
            this.checkTag(false);
        }
        this.setDirty();
    }

    @Override
    public void removeIcon(final String icon)
    {
        if (this.tag == null)
        {
            return;
        }
        final NbtTagList list = this.tag.getTag(DECORATIONS, NbtTagList.class);
        if (list == null)
        {
            return;
        }
        list.removeIf(t -> ((NbtTagCompound) t).getString("id").equals(icon));
        if (list.isEmpty())
        {
            this.tag.remove(DECORATIONS);
            this.checkTag(false);
        }
        this.setDirty();
    }

    @Override
    public void removeIcons()
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.remove(DECORATIONS);
        this.checkTag(false);
        this.setDirty();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public MapMetaImpl clone()
    {
        return new MapMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
