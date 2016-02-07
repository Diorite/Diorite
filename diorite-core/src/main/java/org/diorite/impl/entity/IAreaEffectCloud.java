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

import org.diorite.entity.AreaEffectCloud;

public interface IAreaEffectCloud extends IEntity, AreaEffectCloud, EntityObject
{
    float HEIGHT = 0.5F;

    /**
     * Size of metadata.
     */
    byte META_KEYS                              = 9;
    /**
     * Float
     */
    byte META_KEY_AREA_EFFECT_CLOUD_RADIUS      = 5;
    /**
     * Int  (only for mob spell particle)
     */
    byte META_KEY_AREA_EFFECT_CLOUD_COLOR       = 6;
    /**
     * Boolean, make effect visible only as point.
     */
    byte META_KEY_AREA_EFFECT_CLOUD_IS_POINT    = 7; // TODO
    /**
     * Int
     */
    byte META_KEY_AREA_EFFECT_CLOUD_PARTICLE_ID = 8;
}
