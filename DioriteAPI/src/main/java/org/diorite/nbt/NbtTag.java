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
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Represent nbt tag, nbt is used by minecraft to save most of game data.
 */
public interface NbtTag extends Serializable
{
    /**
     * Charset used by nbt tags.
     */
    Charset STRING_CHARSET = Charset.forName("UTF-8");

    /**
     * Returns name of this nbt tag. May return null.
     *
     * @return name of this nbt tag.
     */
    String getName();

    /**
     * Set name of this nbt tag.
     *
     * @param name new name for this tag.
     */
    void setName(String name);

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    Object getNBTValue();

    /**
     * Returns parent nbt tag container for this tag.
     *
     * @return parent nbt tag container for this tag.
     */
    NbtTagContainer getParent();

    /**
     * Set parent nbt tag container for this tag.
     *
     * @param parent parent of this nbt tag.
     */
    void setParent(NbtTagContainer parent);

    /**
     * Returns id of type of this tag.
     *
     * @return id of type of this tag.
     */
    default byte getTagID()
    {
        return this.getTagType().getTypeID();
    }

    /**
     * Returns type of this tag.
     *
     * @return type of this tag.
     */
    NbtTagType getTagType();

    /**
     * Write this tag to given {@link NbtOutputStream}
     *
     * @param outputStream NbtOutputStream to use.
     * @param anonymous    if tag should be saved with name.
     *
     * @throws IOException if write operation to NbtOutputStream failed.
     */
    void write(NbtOutputStream outputStream, boolean anonymous) throws IOException;

    /**
     * Read data of this tag from given {@link NbtInputStream}
     *
     * @param inputStream NbtInputStream to use.
     * @param anonymous   if stream contains name of this tag.
     * @param limiter     instance of {@link NbtLimiter} to use.
     *
     * @throws IOException if read operation from NbtOutputStream failed.
     */
    default void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        if (! anonymous)
        {
            final int nameSize = inputStream.readShort();
            final byte[] nameBytes = new byte[nameSize];
            inputStream.readFully(nameBytes);
            this.setName(new String(nameBytes, STRING_CHARSET));
        }
    }

    /**
     * Clone (deep) this NbtTag.
     *
     * @return clone of this nbt tag.
     */
    NbtTag clone();
}