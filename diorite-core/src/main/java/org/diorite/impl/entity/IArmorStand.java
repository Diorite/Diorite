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

import org.diorite.entity.ArmorStand;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IArmorStand extends ILivingEntity, ArmorStand, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 2.0F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                               = 17;
    /**
     * Byte bit mask. <br>
     * Small armorstand, HasGravity, HasArms, RemoveBasePlate, Marker (no bounding box)
     */
    byte META_KEY_ARMOR_STAND_STATUS             = 10;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_HEAD_ROTATION      = 11;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_BODY_ROTATION      = 12;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_LEFT_ARM_ROTATION  = 13;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_RIGHT_ARM_ROTATION = 14;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_LEFT_LEG_ROTATION  = 15;
    /**
     * Vector3F, Euler angle
     */
    byte META_KEY_ARMOR_STAND_RIGHT_LEG_ROTATION = 16;

    /**
     * Contains status flags used in matadata.
     */
    interface ArmorStandStatusFlag
    {
        byte SMALL             = 0;
        byte HAS_GRAVITY       = 1;
        byte HAS_ARMS          = 2;
        byte REMOVE_BASE_PLATE = 3;
        byte MARKER            = 4;
    }
}
