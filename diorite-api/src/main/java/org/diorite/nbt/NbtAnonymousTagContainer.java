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

import java.util.List;

/**
 * Represent {@link NbtTagContainer} where all elements are anonymous. (they don't have name)
 */
public interface NbtAnonymousTagContainer extends NbtTagContainer, List<NbtTag>
{
    /**
     * Add new nbt tag to this container.
     *
     * @param tag nbt tag to be added.
     *
     * @return true if tag was added.
     */
    boolean addTag(NbtTag tag);

    /**
     * Returns list of all nbt tags in this container.
     *
     * @return list of all nbt tags in this container.
     */
    List<NbtTag> getTags();

    /**
     * Returns list of all nbt tags in this container casted to given type.
     *
     * @param tagClass type of nbt tags.
     * @param <T>      type of nbt tags.
     *
     * @return list of all nbt tags in this container casted to given type.
     */
    <T extends NbtTag> List<T> getTags(Class<T> tagClass);

    /**
     * Set nbt tag on given index to given tag.
     *
     * @param i   index of element.
     * @param tag nbt tag to set.
     */
    void setTag(int i, NbtTag tag);
}