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

package org.diorite.utils.others;

/**
 * Represent object that can be "dirty" - so it need some kind of update.
 */
public interface Dirtable
{
    /**
     * Check if item is dirty.
     *
     * @return true if item is dirty.
     */
    boolean isDirty();

    /**
     * Change dirty state, and return previous one.
     *
     * @param dirty new dirty state
     *
     * @return old dirty state.
     */
    boolean setDirty(final boolean dirty);

    /**
     * Change dirty state to true, and return previous one.
     *
     * @return old dirty state.
     */
    default boolean setDirty()
    {
        return this.setDirty(true);
    }

    /**
     * Change dirty state to false, and return previous one.
     *
     * @return old dirty state.
     */
    default boolean setClean()
    {
        return this.setDirty(false);
    }
}
