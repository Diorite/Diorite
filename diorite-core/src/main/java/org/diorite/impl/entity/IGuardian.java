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

import org.diorite.entity.Guardian;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGuardian extends IMonsterEntity, Guardian
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.85F, 0.85F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                = 14;
    /**
     * byte, bit flags. <br>
     * Eldery, RetractingSpikes
     */
    byte META_KEY_GUARDIAN_STATUS = 12;
    /**
     * int, entity id
     */
    byte META_KEY_GUARDIAN_TARGET = 13;

    /**
     * Contains status flags used in matadata.
     */
    interface GuardianStatusFlag
    {
        byte ELDERY            = 0;
        byte RETRACTING_SPIKES = 1;
    }
}
