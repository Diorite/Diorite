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

package org.diorite.auth;

import java.util.UUID;

import org.diorite.nbt.NbtSerializable;

/**
 * Represent player profile, profile contains all player data like uuid, nickname, skin.
 */
public interface GameProfile extends NbtSerializable
{
    /**
     * Returns uuid of profile. <br>
     * May return null.
     *
     * @return uuid of profile.
     */
    UUID getId();

    /**
     * Set uuid of profile.
     *
     * @param id new uuid.
     */
    void setId(UUID id);

    /**
     * Returns name of profile. <br>
     * May return null.
     *
     * @return name of profile.
     */
    String getName();

    /**
     * Set name of profile.
     *
     * @param name new name.
     */
    void setName(String name);

    /**
     * Returns property map for this profile.
     *
     * @return property map for this profile.
     */
    PropertyMap getProperties();

    /**
     * Set property data of this profile.
     *
     * @param properties new property data.
     */
    void setProperties(PropertyMap properties);

    /**
     * Returns true if profile contains name and uuid.
     *
     * @return true if profile contains name and uuid.
     */
    boolean isComplete();
}
