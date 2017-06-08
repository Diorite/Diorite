/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Represent list of nbt tags, nbt list-like container.
 */
public final class NbtTagList extends NbtAbstractTag implements NbtAnonymousTagContainer
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    private List<NbtTag> tagList;

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
     * @param name
     *         name to be used.
     */
    public NbtTagList(@Nullable String name)
    {
        super(name);
        this.tagList = new ArrayList<>(8);
    }

    /**
     * Construct new NbtTagList with given name and empty list as value.
     *
     * @param name
     *         name to be used.
     * @param size
     *         initial capacity of list.
     */
    public NbtTagList(@Nullable String name, int size)
    {
        super(name);
        this.tagList = new ArrayList<>(size);
    }

    /**
     * Construct new NbtTagList with given name and list.
     *
     * @param name
     *         name to be used.
     * @param tagList
     *         list of tags to be used.
     */
    public NbtTagList(@Nullable String name, Collection<? extends NbtTag> tagList)
    {
        super(name);
        this.tagList = new ArrayList<>(tagList);
    }

    /**
     * Construct new NbtTagList with given name and list.
     *
     * @param name
     *         name to be used.
     * @param type
     *         type of values in this nbt tag list.
     * @param values
     *         values of this nbt tag list.
     * @param <T>
     *         type of values.
     */
    public <T> NbtTagList(@Nullable String name, NbtTagType type, Collection<? extends T> values)
    {
        super(name);
        this.tagList = new ArrayList<>(values.size());
        for (T value : values)
        {
            this.tagList.add(type.newInstance(value));
        }
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagList
     *         tag to be cloned.
     */
    private NbtTagList(NbtTagList nbtTagList)
    {
        super(nbtTagList);
        this.tagList = new ArrayList<>(nbtTagList.tagList.size());
        nbtTagList.tagList.forEach(t -> this.addTag(t.clone()));
    }

    @Override
    public List<Object> getNBTValue()
    {
        List<Object> list = new ArrayList<>(this.tagList.size());
        list.addAll(this.tagList.stream().map(NbtTag::getNBTValue).collect(Collectors.toList()));
        return list;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.LIST;
    }

    @Override
    public boolean addTag(@Nullable NbtTag tag)
    {
        boolean add = this.tagList.add(tag);
        if (add && (tag != null))
        {
            tag.setParent(this);
        }
        return add;
    }

    @Override
    public List<NbtTag> getTags()
    {
        return new ArrayList<>(this.tagList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> List<T> getTags(Class<T> tagClass)
    {
        List<T> list = new ArrayList<>(this.tagList.size());
        for (NbtTag tag : this.tagList)
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
    public void setTag(int i, NbtTag tag)
    {
        this.tagList.set(i, tag);
    }

    @Override
    public void removeTag(NbtTag tag)
    {
        this.tagList.remove(tag);
    }

    @Override
    public int size()
    {
        return this.tagList.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.tagList.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o)
    {
        return this.tagList.contains(o);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagList clone()
    {
        return new NbtTagList(this);
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeByte((! this.tagList.isEmpty() ? this.tagList.get(0).getTagID() : NbtTagType.END.getTypeID()));
        outputStream.writeInt(this.tagList.size());
        for (NbtTag tag : this.tagList)
        {
            tag.write(outputStream, true);
        }
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tagList = new ArrayList<>(8);
        byte type = inputStream.readByte();
        NbtTagType tagType = NbtTagType.valueOf(type);
        if (tagType == null)
        {
            throw new IllegalStateException("Unknown tag type: " + type);
        }
        int size = inputStream.readInt();
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
    public ListIterator<NbtTag> listIterator(int index)
    {
        return this.tagList.listIterator(index);
    }

    @Override
    public NbtTagList subList(int fromIndex, int toIndex)
    {
        return new NbtTagList(this.name, this.tagList.subList(fromIndex, toIndex));
    }

    @Override
    public ListIterator<NbtTag> listIterator()
    {
        return this.tagList.listIterator();
    }

    @Override
    public Iterator<NbtTag> iterator()
    {
        return this.tagList.iterator();
    }

    @Override
    public NbtTag[] toArray()
    {
        return this.tagList.toArray(new NbtTag[this.size()]);
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T> T[] toArray(T[] a)
    {
        return this.tagList.toArray(a);
    }

    @Override
    public boolean add(@Nullable NbtTag nbtTag)
    {
        return this.addTag(nbtTag);
    }

    @Override
    public boolean remove(@Nullable Object o)
    {
        boolean r = this.tagList.remove(o);
        if (o != null)
        {
            NbtTag tag = (NbtTag) o;
            tag.setParent(null);
        }
        return r;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.tagList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends NbtTag> c)
    {
        return this.tagList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends NbtTag> c)
    {
        return this.tagList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return this.tagList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return this.tagList.retainAll(c);
    }

    @Override
    public void clear()
    {
        this.tagList.clear();
    }

    @Override
    @Nullable
    public NbtTag get(int index)
    {
        return this.tagList.get(index);
    }

    @Override
    @Nullable
    public NbtTag set(int index, @Nullable NbtTag element)
    {
        return this.tagList.set(index, element);
    }

    @Override
    public void add(int index, @Nullable NbtTag element)
    {
        this.tagList.add(index, element);
    }

    @Override
    public NbtTag remove(int index)
    {
        return this.tagList.remove(index);
    }

    @Override
    public int indexOf(@Nullable Object o)
    {
        return this.tagList.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o)
    {
        return this.tagList.lastIndexOf(o);
    }

    @Override
    public boolean equals(@Nullable Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagList))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagList nbtTags = (NbtTagList) o;
        return this.tagList.equals(nbtTags.tagList);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.tagList.hashCode();
        return result;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.tagList.size() * 64);
        sb.append('[');

        for (int index = 0; index < this.tagList.size(); ++ index)
        {
            if (index != 0)
            {
                sb.append(',');
            }
            sb.append(index).append(':').append(this.tagList.get(index));
        }

        return sb.append(']').toString();
    }
}