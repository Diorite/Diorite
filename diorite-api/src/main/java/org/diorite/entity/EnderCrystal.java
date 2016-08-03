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

package org.diorite.entity;

import org.diorite.block.BlockLocation;

/**
 * Represent ender crystal entity.
 */
public interface EnderCrystal extends Entity, ObjectEntity
{
    /**
     * Returns true if this ender crystal include bottom pedestal.
     *
     * @return true if this ender crystal include bottom pedestal.
     */
    boolean isShowBottom();

    /**
     * Sets if this ender crystal should include bottom pedestal.
     *
     * @param showBottom if this ender crystal should include bottom pedestal.
     */
    void setShowBottom(boolean showBottom);

    /**
     * Returns beam target location, may be null.
     *
     * @return beam target location, may be null.
     */
    BlockLocation getBeamTarget();

    /**
     * Sets beam target location, use null value to disable beam.
     *
     * @param location new beam target location.
     */
    void setBeamTarget(final BlockLocation location);
}
