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

package org.diorite.material;

import org.diorite.Diorite;
import org.diorite.registry.GameId;
import org.diorite.registry.Registry;

/**
 * Pseudo-enum of basic available items in vanilla diorite.
 */
public final class Items
{
    public static final ItemType AIR;
    public static final ItemType STONE;
    public static final ItemType DIRT;
    public static final ItemType GRASS;

    private Items() {}

    static
    {
        Diorite diorite = Diorite.getDiorite();
        ItemRegistry<?> itemRegistry = diorite.getItemRegistry();
        AIR = itemRegistry.getOrThrow(GameId.ofMinecraft("air"));
        STONE = itemRegistry.getOrThrow(GameId.ofMinecraft("stone"));
        DIRT = itemRegistry.getOrThrow(GameId.ofMinecraft("dirt"));
        GRASS = itemRegistry.getOrThrow(GameId.ofMinecraft("grass"));
    }
}
