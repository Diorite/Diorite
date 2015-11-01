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

package org.diorite.impl.world.generator.structures.tree;

import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.WoodType;
import org.diorite.material.blocks.LogMat;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.structures.tree.TreeStructure;

public class SmallTreeStructure extends TreeStructure
{
    private final boolean extraBlock;

    public SmallTreeStructure(final BlockMaterialData logMat, final BlockMaterialData leavesMat, final boolean extraBlock)
    {
        super(logMat, leavesMat);
        this.extraBlock = extraBlock;
    }

    public SmallTreeStructure(final LogMat logMat, final BlockMaterialData leavesMat)
    {
        super(logMat, leavesMat);
        this.extraBlock = Objects.equals(logMat.getWoodType(), WoodType.BIRCH);
    }

    public SmallTreeStructure(final WoodType woodType)
    {
        super(woodType);
        this.extraBlock = Objects.equals(woodType, WoodType.BIRCH);
    }

    public SmallTreeStructure(final WoodType woodType, final boolean extraBlock)
    {
        super(woodType);
        this.extraBlock = extraBlock;
    }

    @Override
    protected boolean genTree(final ChunkPos chunkPos, final Random random, final BlockLocation location)
    {
        return this.genTree(chunkPos, random, location.addY(1), DioriteRandomUtils.getRandInt(random, 4, this.extraBlock ? 7 : 6));
    }

    @SuppressWarnings("PointlessArithmeticExpression") // it looks better, java should simplify that on compilation time
    protected boolean genTree(final ChunkPos chunkPos, final Random random, final BlockLocation c, final int size)
    {
        for (int i = 0; i < size; i++)
        {
            this.setBlock(c, 0 * 0, 0 + i, 0 * 0, this.logMat);
        }
        this.setBlock(c, 0 * 0, size, 0 * 0, this.leavesMat);
        int k = 0;
        for (int i = size, h = (size - 4); i > h; i--, k++)
        {
            this.setBlock(c, 0 + 1, i, 0 * 0, this.leavesMat);
            this.setBlock(c, 0 * 0, i, 0 + 1, this.leavesMat);
            this.setBlock(c, 0 - 1, i, 0 * 0, this.leavesMat);
            this.setBlock(c, 0 * 0, i, 0 - 1, this.leavesMat);
            if (k == 1)
            {
                // corners are set randomly
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 + 1, i, 0 + 1, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 + 1, i, 0 - 1, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 - 1, i, 0 + 1, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 - 1, i, 0 - 1, this.leavesMat);
                }
            }
            else if (k >= 2)
            {
                this.setBlock(c, 0 + 1, i, 0 + 1, this.leavesMat);
                this.setBlock(c, 0 + 1, i, 0 - 1, this.leavesMat);
                this.setBlock(c, 0 - 1, i, 0 + 1, this.leavesMat);
                this.setBlock(c, 0 - 1, i, 0 - 1, this.leavesMat);

                this.setBlock(c, 0 + 2, i, 0 + 1, this.leavesMat);
                this.setBlock(c, 0 + 2, i, 0 * 0, this.leavesMat);
                this.setBlock(c, 0 + 2, i, 0 - 1, this.leavesMat);

                this.setBlock(c, 0 - 2, i, 0 + 1, this.leavesMat);
                this.setBlock(c, 0 - 2, i, 0 * 0, this.leavesMat);
                this.setBlock(c, 0 - 2, i, 0 - 1, this.leavesMat);

                this.setBlock(c, 0 + 1, i, 0 + 2, this.leavesMat);
                this.setBlock(c, 0 * 0, i, 0 + 2, this.leavesMat);
                this.setBlock(c, 0 - 1, i, 0 + 2, this.leavesMat);

                this.setBlock(c, 0 + 1, i, 0 - 2, this.leavesMat);
                this.setBlock(c, 0 * 0, i, 0 - 2, this.leavesMat);
                this.setBlock(c, 0 - 1, i, 0 - 2, this.leavesMat);

                // corners are set randomly
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 + 2, i, 0 + 2, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 + 2, i, 0 - 2, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 - 2, i, 0 + 2, this.leavesMat);
                }
                if (random.nextBoolean())
                {
                    this.setBlock(c, 0 - 2, i, 0 - 2, this.leavesMat);
                }
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("extraBlock", this.extraBlock).toString();
    }
}
