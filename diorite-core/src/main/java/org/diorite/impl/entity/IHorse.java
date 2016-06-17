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

import org.diorite.entity.Horse;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IHorse extends IAnimalEntity, Horse
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                 = 18;
    /**
     * Status bit set. <br>
     * none, IsTamed, HasSaddle, HasChest, IsBred, IsEating, IsRearing, MouthOpen
     */
    byte META_KEY_HORSE_STATUS     = 13;
    /**
     * int, enum, horse type, Normal, Donkey, Mule, Zombie, Sekeleton
     */
    byte META_KEY_HORSE_TYPE       = 14;
    /**
     * int, 2x enum, horse variant and color. <br>
     * 0x00FF - color: White, Creamy, Chestnut, Brown, Black, Gray, Dark Brown <br>
     * 0xFF00 - variant: None, White, Whitefield, White Dots, Black Dots.
     */
    byte META_KEY_HORSE_VARIANT    = 15;
    /**
     * UUID
     */
    byte META_KEY_HORSE_OWNER_UUID = 16;
    /**
     * int, enum? Armor type.
     */
    byte META_KEY_HORSE_ARMOR      = 17;


    /**
     * Contains status flags used in matadata.
     */
    interface HorseStatusFlag
    {
        byte TAMED      = 1;
        byte SADDLE     = 2;
        byte CHEST      = 3;
        byte BRED       = 4;
        byte EATING     = 5;
        byte REARING    = 6;
        byte MOUTH_OPEN = 7;
    }
}
