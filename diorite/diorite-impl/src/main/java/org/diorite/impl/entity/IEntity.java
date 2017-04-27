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

package org.diorite.impl.entity;

import org.diorite.entity.Entity;

public interface IEntity extends Entity
{
    /* DEFAULT VALUES */
    byte    ENTITY_BASIC_FLAGS           = 0;
    int     ENTITY_AIR_LEVEL             = 300;
    String  ENTITY_NAME                  = "";
    boolean ENTITY_NAME_TAG_VISIBILITY   = false;
    boolean ENTITY_SOUND                 = true;
    boolean ENTITY_GRAVITY               = true;

    /* METADATA */
    /**
     * Size of metadata.
     */
    byte METADATA_SIZE = 6;

    /**
     * Basic flags.
     */
    byte METADATA_ENTITY_BASIC_FLAGS = 0;

    /**
     * Air level.
     */
    byte METADATA_ENTITY_AIR_LEVEL = 1;

    /**
     * Name or name tag.
     */
    byte METADATA_ENTITY_NAME = 2;

    /**
     * Name tag visibility.
     */
    byte METADATA_ENTITY_NAME_TAG_VISIBILITY = 3;

    /**
     * Sound effects of entity.
     */
    byte METADATA_ENTITY_SOUND = 4;

    /**
     * Gravity.
     */
    byte METADATA_ENTITY_GRAVITY = 5;
}
