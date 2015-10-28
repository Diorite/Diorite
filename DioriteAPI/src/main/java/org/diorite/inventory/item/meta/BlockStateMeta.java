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
 * Represent block item that contains saved block state.
 */
public interface BlockStateMeta extends ItemMeta
{
    // TODO: finish when BlockState/BlockSnapshot will be implemented

    /**
     * Returns whether the item has a block state currently
     * attached to it.
     *
     * @return whether a block state is already attached
     */
    boolean hasBlockState();

//    /**
//     * Returns the currently attached block state for this
//     * item or creates a new one if one doesn't exist.
//     *
//     * The state is a copy, it must be set back (or to another
//     * item) with {@link #setBlockState(org.bukkit.block.BlockState)}
//     *
//     * @return the attached state or a new state
//     */
//    BlockState getBlockState();

//    /**
//     * Attaches a copy of the passed block state to the item.
//     *
//     * @param blockState the block state to attach to the block.
//     *
//     * @throws IllegalArgumentException if the blockState is null
//     *                                  or invalid for this item.
//     */
//    void setBlockState(BlockState blockState);
    @Override
    BlockStateMeta clone();
}
