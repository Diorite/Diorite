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

package org.diorite.inventory.item.meta;

import java.util.UUID;

import org.diorite.auth.GameProfile;

/**
 * Represent meta data used by skulls.
 */
public interface SkullMeta extends ItemMeta
{
    /**
     * Returns the owner of the skull.
     *
     * @return the owner of the skull.
     */
    GameProfile getOwner();

    /**
     * Checks to see if the skull has an owner.
     *
     * @return true if the skull has an owner
     */
    boolean hasOwner();

    /**
     * Sets the owner of the skull. <br>
     * Method will try to get cached skin for this player or download new skin from Mojang if needed,
     * downloading skin will not block current thread.
     *
     * @param nickname the new owner of the skull.
     */
    void setOwner(String nickname);

    /**
     * Sets the owner of the skull. <br>
     * Method will try to get cached skin for this player or download new skin from Mojang if needed,
     * downloading skin will not block current thread.
     *
     * @param uuid the new owner of the skull.
     */
    void setOwner(UUID uuid);

    /**
     * Sets the owner of the skull. <br>
     * Method will trust data in {@link GameProfile} even if it is fake or outdated.
     *
     * @param gameProfile the new owner of the skull.
     */
    void setOwner(GameProfile gameProfile);

    @Override
    SkullMeta clone();
}