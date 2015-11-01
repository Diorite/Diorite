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

package org.diorite.material;

import org.diorite.material.blocks.OreBlockMat;
import org.diorite.material.blocks.OreMat;

@SuppressWarnings("ClassHasNoToStringMethod")
public interface OreItemMat
{
    /**
     * Returns related {@link OreMat} for this item, may return null.
     *
     * @return related {@link OreMat} for this item.
     */
    OreMat getBlockOreType();

    /**
     * Returns related {@link OreBlockMat} for this item, may return null.
     *
     * @return related {@link OreBlockMat} for this item.
     */
    OreBlockMat getBlockType();

    /**
     * Do not use for checking type or whatever, use {@link OreItemMat}.
     * This is only helper class.
     */
    abstract class OreItemMatExt extends ItemMaterialData implements OreItemMat
    {
        protected final OreMat      blockOreType;
        protected final OreBlockMat blockType;

        protected OreItemMatExt(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
        {
            super(enumName, id, minecraftId, typeName, type);
            this.blockOreType = oreType;
            this.blockType = blockType;
        }

        protected OreItemMatExt(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type);
            this.blockOreType = oreType;
            this.blockType = blockType;
        }

        /**
         * Returns related {@link OreMat} for this item, may return null.
         *
         * @return related {@link OreMat} for this item.
         */
        @Override
        public OreMat getBlockOreType()
        {
            return this.blockOreType;
        }

        /**
         * Returns related {@link OreBlockMat} for this item, may return null.
         *
         * @return related {@link OreBlockMat} for this item.
         */
        @Override
        public OreBlockMat getBlockType()
        {
            return this.blockType;
        }
    }

}
