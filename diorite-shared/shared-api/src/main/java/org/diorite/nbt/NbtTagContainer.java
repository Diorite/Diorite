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

import javax.annotation.Nonnull;

/**
 * Represent container of nbt tags.
 */
public interface NbtTagContainer extends NbtTag
{
    /**
     * Remove given nbt tag from this container.
     *
     * @param tag
     *         nbt tag to be removed.
     */
    void removeTag(@Nonnull NbtTag tag);

    /**
     * Returns true if container is empty.
     *
     * @return true if container is empty.
     */
    boolean isEmpty();

    @Override
    NbtTagContainer clone();

    /**
     * Add new NbtTag to this container, {@link NbtTag#getName()} will be used to get name for this value.
     *
     * @param tag
     *         nbt tag to be added.
     */
    boolean addTag(@Nonnull NbtTag tag);
}