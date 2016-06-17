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

package org.diorite.impl.entity;

import org.diorite.entity.Wither;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWither extends IMonsterEntity, Wither
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 3.5F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                          = 16;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_FIRST_HEAD_TARGET  = 12;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_SECOND_HEAD_TARGET = 13;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_THIRD_HEAD_TARGET  = 14;
    /**
     * int, entity id
     */
    byte META_KEY_WITHER_INVULNERABLE_TIME  = 15;
}
