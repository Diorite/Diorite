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

package org.diorite.nbt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent list of nbt tags, nbt list-like container.
 */
public class NbtTagList extends NbtAbstractTag implements NbtAnonymousTagContainer
{
    /**
     * Value of nbt tag.
     */
    protected List<NbtTag> tagList;

    /**
     * Construct new NbtTagList without name and empty list as value.
     */
    public NbtTagList()
    {
        this.tagList = new ArrayList<>(8);
    }

    /**
     * Construct new NbtTagList with given name and empty list as value.
     *
     * @param name name to be used.
     */
    public NbtTagList(final String name)
    {
        super(name);
        this.tagList = new ArrayList<>(8);
    }

    /**
     * Construct new NbtTagList with given name and empty list as value.
     *
     * @param name name to be used.
     * @param size initial capacity of list.
     */
    public NbtTagList(final String name, final int size)
    {
        super(name);
        this.tagList = new ArrayList<>(size);
    }

    /**
     * Construct new NbtTagList with given name and list.
     *
     * @param name    name to be used.
     * @param tagList list of tags to be used.
     */
    public NbtTagList(final String name, final Collection<? extends NbtTag> tagList)
    {
        super(name);
        this.tagList = new ArrayList<>(tagList);
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagList tag to be cloned.
     */
    protected NbtTagList(final NbtTagList nbtTagList)
    {
        super(nbtTagList);
        this.tagList = new ArrayList<>(nbtTagList.tagList.size());
        nbtTagList.tagList.forEach(t -> this.addTag(t.clone()));
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.LIST;
    }

    @Override
    public void addTag(final NbtTag tag)
    {
        this.tagList.add(tag);
        tag.setParent(this);
    }

    @Override
    public List<NbtTag> getTags()
    {
        return new ArrayList<>(this.tagList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> List<T> getTags(final Class<T> tagClass)
    {
        final List<T> list = new ArrayList<>(this.tagList.size());
        for (final NbtTag tag : this.tagList)
        {
            if (! tagClass.isInstance(tag))
            {
                continue;
            }
            list.add((T) tag);
        }
        return list;
    }

    @Override
    public void setTag(final int i, final NbtTag tag)
    {
        this.tagList.set(i, tag);
    }

    @Override
    public void removeTag(final NbtTag tag)
    {
        this.tagList.remove(tag);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagList clone()
    {
        return new NbtTagList(this);
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeByte((! this.tagList.isEmpty() ? this.tagList.get(0).getTagID() : NbtTagType.END.getTypeID()));
        outputStream.writeInt(this.tagList.size());
        for (final NbtTag tag : this.tagList)
        {
            tag.write(outputStream, true);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tagList = new ArrayList<>(8);
        final byte type = inputStream.readByte();
        final NbtTagType tagType = NbtTagType.valueOf(type);
        final int size = inputStream.readInt();
        if (tagType == NbtTagType.END)
        {
            return;
        }

        limiter.incrementElementsCount(size);

        for (int i = 0; i < size; i++)
        {
            this.addTag(inputStream.readTag(tagType, true, limiter));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tagList", this.tagList).toString();
    }
}