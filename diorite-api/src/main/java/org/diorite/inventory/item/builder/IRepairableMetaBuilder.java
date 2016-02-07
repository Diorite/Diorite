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

package org.diorite.inventory.item.builder;

import org.diorite.inventory.item.meta.RepairableMeta;

/**
 * Interface of builder of repairable item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IRepairableMetaBuilder<B extends IRepairableMetaBuilder<B, M>, M extends RepairableMeta> extends IMetaBuilder<B, M>
{
    /**
     * Set unbreakable state of item.
     *
     * @param unbreakable if item should be unbreakable.
     *
     * @return builder for method chains.
     */
    default B unbreakable(final boolean unbreakable)
    {
        this.meta().setUnbreakable(unbreakable);
        return this.getBuilder();
    }

    /**
     * Set unbreakable state of item.
     *
     * @param src source item meta to copy unbreakable state from it.
     *
     * @return builder for method chains.
     */
    default B unbreakable(final RepairableMeta src)
    {
        this.meta().setUnbreakable(src.isUnbreakable());
        return this.getBuilder();
    }

    /**
     * Set repair cost of item.
     *
     * @param repairCost new repair cost of item.
     *
     * @return builder for method chains.
     */
    default B repairCost(final int repairCost)
    {
        this.meta().setRepairCost(repairCost);
        return this.getBuilder();
    }

    /**
     * Set repair cost of item.
     *
     * @param src source item meta to copy repair cost from it.
     *
     * @return builder for method chains.
     */
    default B repairCost(final RepairableMeta src)
    {
        this.meta().setRepairCost(src.getRepairCost());
        return this.getBuilder();
    }
}
