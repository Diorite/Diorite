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

package org.diorite.world;

import java.util.Set;

import org.diorite.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;

public interface TileEntity
{
    BlockLocation getLocation();

    Block getBlock();

    /**
     * Implementation of this method should simulate real drop from this tile-entity. <br>
     * Method should only add items resulting from being tile-entity, like chest should add
     * all items from it, but should not add a chest item itself. <br>
     * From simple blocks that drops itself first element of drop list should be always
     * item representation of this block. You may edit it from this method.
     *
     * @param rand  random instance, should be used if random number is needed.
     * @param drops drop list, add drops here.
     */
    void simulateDrop(DioriteRandom rand, Set<ItemStack> drops);
}
