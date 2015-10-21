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

package org.diorite.nbt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent class used to limit nbt data readed from stream. <br>
 * It is used in packets to deny packets with too complex NBT data that can be used to "exploit" server. (ram usage, creating many nested lists etc)
 */
public class NbtLimiter
{
    /**
     * Default max complexity level, nested lists/maps are increasing complexity level.
     */
    public static final int DEFAULT_MAX_COMPLEXITY = 10;
    /**
     * Default max amount of bytes that can be read from input stream.
     */
    public static final int DEFAULT_MAX_BYTES      = 2097152; // 2 MB
    /**
     * Default max amount of elements that can be read from input stream.
     */
    public static final int DEFAULT_MAX_ELEMENTS   = 300;

    private final int maxBytes;
    private final int maxComplexity;
    private final int maxElements;
    private       int bytes;
    private       int complexity;
    private       int elements;

    /**
     * Construct new nbt limiter with given limits.
     *
     * @param maxBytes      max amount of bytes that can be read from input stream.
     * @param maxComplexity max complexity level, nested lists/maps are increasing complexity level.
     * @param maxElements   max amount of elements that can be read from input stream.
     */
    public NbtLimiter(final int maxBytes, final int maxComplexity, final int maxElements)
    {
        this.maxBytes = maxBytes;
        this.maxComplexity = maxComplexity;
        this.maxElements = maxElements;
    }

    /**
     * Returns bytes that was already readed from stream.
     *
     * @return bytes that was already readed from stream.
     */
    public int getBytes()
    {
        return this.bytes;
    }

    /**
     * Returns current amount of elements that was already readed from stream.
     *
     * @return current amount of elements that was already readed from stream.
     */
    public int getElements()
    {
        return this.elements;
    }

    /**
     * Returns current level of complexity.
     *
     * @return current level of complexity.
     */
    public int getComplexity()
    {
        return this.complexity;
    }

    /**
     * Returns bytes limit for this limiter.
     *
     * @return bytes limit for this limiter.
     */
    public int getMaxBytes()
    {
        return this.maxBytes;
    }

    /**
     * Returns complexity level limit for this limiter.
     *
     * @return complexity level limit for this limiter.
     */
    public int getMaxComplexity()
    {
        return this.maxComplexity;
    }

    /**
     * Returns elements limit for this limiter.
     *
     * @return elements limit for this limiter.
     */
    public int getMaxElements()
    {
        return this.maxElements;
    }

    /**
     * Add readed bytes to this limiter, method will throw exception If the limit is exceeded.
     *
     * @param amount amount of readed bytes.
     */
    public void countBytes(final int amount)
    {
        if (this.maxBytes <= 0)
        {
            return;
        }
        this.bytes += amount;
        if (this.bytes > this.maxBytes)
        {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.bytes + " bytes where max allowed: " + this.maxBytes);
        }
    }


    /**
     * Add complexity level to this limiter, method will throw exception If the limit is exceeded.
     */
    public void incrementComplexity()
    {
        if (this.maxComplexity <= 0)
        {
            return;
        }
        if (++ this.complexity > this.maxComplexity)
        {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, " + this.complexity + " > " + this.maxComplexity);
        }
    }


    /**
     * Add readed elements to this limiter, method will throw exception If the limit is exceeded.
     *
     * @param amount amount of readed elements.
     */
    public void incrementElementsCount(final int amount)
    {
        if (this.maxElements <= 0)
        {
            return;
        }
        this.elements += amount;
        if (this.elements > this.maxElements)
        {
            throw new RuntimeException("Tried to read NBT tag with too many elements, " + this.elements + " > " + this.maxElements);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxBytes", this.maxBytes).append("maxComplexity", this.maxComplexity).append("maxElements", this.maxElements).append("bytes", this.bytes).append("complexity", this.complexity).append("elements", this.elements).toString();
    }

    /**
     * Create NbtLimiter with default settings.
     *
     * @return new NbtLimiter with default settings.
     */
    public static NbtLimiter getDefault()
    {
        return new NbtLimiter(DEFAULT_MAX_BYTES, DEFAULT_MAX_COMPLEXITY, DEFAULT_MAX_ELEMENTS);
    }

    /**
     * Create NbtLimiter without limit.
     *
     * @return new NbtLimiter without limit.
     */
    public static NbtLimiter getUnlimited()
    {
        return new NbtLimiter(0, 0, 0);
    }
}
