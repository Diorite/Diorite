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

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundAbilities;
import org.diorite.impl.connection.packets.play.serverbound.PacketPlayServerboundAbilities;
import org.diorite.entity.Human;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;
import org.diorite.utils.others.NamedUUID;

public interface IHuman extends ILivingEntity, Human
{
    float BASE_HEAD_HEIGHT      = 1.62F;
    float CROUCHING_HEAD_HEIGHT = BASE_HEAD_HEIGHT - 0.08F;
    float SLEEP_HEAD_HEIGHT     = 0.2F;
    float ITEM_DROP_MOD_Y       = 0.3F;
    @SuppressWarnings("MagicNumber")
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
    /**
     * Size of metadata. TODO validate
     */
    byte META_KEYS                  = 19;
    /**
     * byte entry with visible skin parts flags.
     */
    byte META_KEY_SKIN_FLAGS        = 10;
    /**
     * byte entry, 0x02 bool cape hidden
     */
    byte META_KEY_CAPE              = 16;
    /**
     * float entry, amount of absorption hearts (yellow/gold ones)
     */
    byte META_KEY_ABSORPTION_HEARTS = 17;
    /**
     * int entry, amount of player points
     */
    byte META_KEY_SCORE             = 18;

    void setNamedUUID(NamedUUID namedUUID);

    float getHeadHeight();

    void pickupItems();

    PacketPlayClientboundAbilities getAbilities();

    void setAbilities(PacketPlayClientboundAbilities abilities);

    void setAbilities(PacketPlayServerboundAbilities abilities);

    void closeInventory(int id);
}
