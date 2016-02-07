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

package org.diorite.impl.world.chunk;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.palette.Palette;
import org.diorite.material.BlockMaterialData;

public class ChunkBlockData
{
    private static final int MAX_BITS_PER_BLOCK = 32;
    private static final int MIN_BITS_PER_BLOCK = 1;
    private static final int BITS_PER_ENTRY     = 64;

    private final long[] blocksArray;
    private final int    bitsPerBlock;
    private final long   typeSize;
    private final int    size;

    private ChunkBlockData(final long[] blocksArray, final int bitsPerBlock, final long typeSize, final int size)
    {
        this.blocksArray = blocksArray;
        this.bitsPerBlock = bitsPerBlock;
        this.typeSize = typeSize;
        this.size = size;
    }

    public ChunkBlockData(final int bitsPerBlock, final int size)
    {
        Validate.inclusiveBetween(MIN_BITS_PER_BLOCK, MAX_BITS_PER_BLOCK, bitsPerBlock);
        this.size = size;
        this.bitsPerBlock = bitsPerBlock;
        this.typeSize = (1L << bitsPerBlock) - 1L;
        this.blocksArray = new long[roundToMultiple(size * bitsPerBlock, BITS_PER_ENTRY) / BITS_PER_ENTRY];
    }

    private static int roundToMultiple(final int i, int multipleOf)
    {
        if (multipleOf == 0)
        {
            return 0;
        }
        else if (i == 0)
        {
            return multipleOf;
        }
        else
        {
            if (i < 0)
            {
                multipleOf *= - 1;
            }

            final int rest = i % multipleOf;
            return (rest == 0) ? i : ((i + multipleOf) - rest);
        }
    }

    public int getAsIntAndSet(final int index, final int patternID, final Palette palette)
    {
        return palette.getAsInt(this.getAndSet(index, patternID));
    }

    public BlockMaterialData getAndSet(final int index, final int patternID, final Palette palette)
    {
        return palette.get(this.getAndSet(index, patternID));
    }

    public int getAndSet(final int index, final int patternID)
    {
        Validate.inclusiveBetween(0, (this.size - 1), index);
        Validate.inclusiveBetween(0, this.typeSize, patternID);

        final int baseOffset = index * this.bitsPerBlock;
        final int dataIndex = baseOffset / BITS_PER_ENTRY;
        final int indexOfLastBit = (((index + 1) * this.bitsPerBlock) - 1) / BITS_PER_ENTRY;
        final int bitOffset = baseOffset % BITS_PER_ENTRY;

        final long longA = this.blocksArray[dataIndex];

        this.blocksArray[dataIndex] = (longA & ~ (this.typeSize << bitOffset)) | (((long) patternID & this.typeSize) << bitOffset);
        if (dataIndex == indexOfLastBit)
        {
            return (int) ((longA >>> bitOffset) & this.typeSize);
        }
        else
        {
            final int bitOffsetDelta = BITS_PER_ENTRY - bitOffset;
            final int bitDelta = this.bitsPerBlock - bitOffsetDelta;

            final long longB = this.blocksArray[indexOfLastBit];
            this.blocksArray[indexOfLastBit] = ((longB >>> bitDelta) << bitDelta) | (((long) patternID & this.typeSize) >> bitOffsetDelta);

            return (int) (((longA >>> bitOffset) | (longB << bitOffsetDelta)) & this.typeSize);
        }
    }

    public void set(final int index, final int patternID)
    {
        Validate.inclusiveBetween(0, (this.size - 1), index);
//        Validate.inclusiveBetween(0, this.typeSize, patternID);
        final int baseOffset = index * this.bitsPerBlock;
        final int dataIndex = baseOffset / BITS_PER_ENTRY;
        final int indexOfLastBit = (((index + 1) * this.bitsPerBlock) - 1) / BITS_PER_ENTRY;
        final int bitOffset = baseOffset % BITS_PER_ENTRY;
        this.blocksArray[dataIndex] = (this.blocksArray[dataIndex] & ~ (this.typeSize << bitOffset)) | (((long) patternID & this.typeSize) << bitOffset);
        if (dataIndex != indexOfLastBit)
        {
            final int bitOffsetDelta = BITS_PER_ENTRY - bitOffset;
            final int bitDelta = this.bitsPerBlock - bitOffsetDelta;
            this.blocksArray[indexOfLastBit] = ((this.blocksArray[indexOfLastBit] >>> bitDelta) << bitDelta) | (((long) patternID & this.typeSize) >> bitOffsetDelta);
        }

    }

    public int getAsInt(final int index, final Palette palette)
    {
        return palette.getAsInt(this.get(index));
    }

    public BlockMaterialData get(final int index, final Palette palette)
    {
        return palette.get(this.get(index));
    }

    public int get(final int index)
    {
        Validate.inclusiveBetween(0, (this.size - 1), index);
        final int baseOffset = index * this.bitsPerBlock;
        final int dataIndex = baseOffset / BITS_PER_ENTRY;
        final int indexOfLastBit = (((index + 1) * this.bitsPerBlock) - 1) / BITS_PER_ENTRY;
        final int bitOffset = baseOffset % BITS_PER_ENTRY;
        if (dataIndex == indexOfLastBit)
        {
            return (int) ((this.blocksArray[dataIndex] >>> bitOffset) & this.typeSize);
        }
        else
        {
            final int bitOffsetDelta = BITS_PER_ENTRY - bitOffset;
            return (int) (((this.blocksArray[dataIndex] >>> bitOffset) | (this.blocksArray[indexOfLastBit] << bitOffsetDelta)) & this.typeSize);
        }
    }

    public long[] getDataArray()
    {
        return this.blocksArray;
    }

    public int size()
    {
        return this.size;
    }

    @Override
    public ChunkBlockData clone()
    {
        return new ChunkBlockData(this.blocksArray.clone(), this.bitsPerBlock, this.typeSize, this.size);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("bitsPerBlock", this.bitsPerBlock).append("typeSize", this.typeSize).append("size", this.size).toString();
    }
}
