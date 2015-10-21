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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract representation of NbtTag.
 */
public abstract class NbtAbstractTag implements NbtTag
{
    /**
     * Name of this nbt tag.
     */
    protected String name;
    /**
     * Parent of this nbt tag.
     */
    protected NbtTagContainer parent = null;

    /**
     * Construct new nbt tag without name.
     */
    protected NbtAbstractTag()
    {
    }

    /**
     * Construct new nbt tag with given name.
     *
     * @param name name of this nbt tag.
     */
    protected NbtAbstractTag(final String name)
    {
        this.setName(name);
    }

    /**
     * Clone constructor.
     *
     * @param nbtAbstractTag tag to clone.
     */
    protected NbtAbstractTag(final NbtAbstractTag nbtAbstractTag)
    {
        this.name = nbtAbstractTag.name;
//        this.parent = nbtAbstractTag.parent;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(String name)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        if (name != null)
        {
            name = name.intern();
        }
        this.name = name;
        if (this.parent != null)
        {
            if (this.parent instanceof NbtAnonymousTagContainer)
            {
                ((NbtAnonymousTagContainer) this.parent).addTag(this);
            }
            else
            {
                ((NbtNamedTagContainer) this.parent).addTag(this);
            }
        }
    }

    @Override
    public NbtTagContainer getParent()
    {
        return this.parent;
    }

    @Override
    public void setParent(final NbtTagContainer parent)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        this.parent = parent;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        if (! anonymous)
        {
            final byte[] name = this.name.getBytes(STRING_CHARSET);
            outputStream.writeShort(name.length);
            outputStream.write(name);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        if (! anonymous)
        {
            final int nameSize = inputStream.readShort();
            final byte[] nameBytes = new byte[nameSize];
            inputStream.readFully(nameBytes);
            this.setName(new String(nameBytes, STRING_CHARSET));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("parent", this.parent).toString();
    }
}