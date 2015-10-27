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

/**
 * Represent item that can be repaired using anvil.
 */
public interface RepairableMeta extends ItemMeta
{
    /**
     * Checks to see if this has a repair penalty.
     *
     * @return true if this has a repair penalty.
     */
    boolean hasRepairCost();

    /**
     * Returns the repair penalty.
     *
     * @return the repair penalty.
     */
    int getRepairCost();

    /**
     * Sets the repair penalty.
     *
     * @param cost repair penalty
     */
    void setRepairCost(int cost);

    /**
     * Set if this tool is unbreakable.
     *
     * @param unbreakable if tool should be unbreakable.
     */
    void setUnbreakable(boolean unbreakable);

    /**
     * Returns true if tool is unbreakable.
     *
     * @return true if tool is unbreakable.
     */
    boolean isUnbreakable();

    @Override
    RepairableMeta clone();
}
