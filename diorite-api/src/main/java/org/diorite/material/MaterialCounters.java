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

package org.diorite.material;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * DO NOT USE.
 */
class MaterialCounters
{
    /**
     * Counter for all (with subtypes) item materials.
     */
    protected static final AtomicInteger allItems  = new AtomicInteger();
    /**
     * Counter for all (with subtypes) blocks materials.
     */
    protected static final AtomicInteger allBlocks = new AtomicInteger();
    /**
     * Counter for item materials.
     */
    protected static final AtomicInteger items     = new AtomicInteger();
    /**
     * Counter for blocks materials.
     */
    protected static final AtomicInteger blocks    = new AtomicInteger();

    /**
     * Block with largest id
     */
    protected static BlockMaterialData lastBlock;
}
