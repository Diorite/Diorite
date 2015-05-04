package org.diorite.nbt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtLimiter
{
    public static final int DEFAULT_MAX_COMPLEXITY = 100;
    public static final int DEFAULT_MAX_BYTES      = 2097152; // 2 MB
    public static final int DEFAULT_MAX_ELEMENTS   = 300;

    private final int maxBytes;
    private final int maxComplexity;
    private final int maxElements;
    private       int bytes;
    private       int complexity;
    private       int elements;

    public NbtLimiter(final int maxBytes, final int maxComplexity, final int maxElements)
    {
        this.maxBytes = maxBytes;
        this.maxComplexity = maxComplexity;
        this.maxElements = maxElements;
    }

    public int getBytes()
    {
        return this.bytes;
    }

    public int getComplexity()
    {
        return this.complexity;
    }

    public int getMaxBytes()
    {
        return this.maxBytes;
    }

    public int getMaxComplexity()
    {
        return this.maxComplexity;
    }

    public int getMaxElements()
    {
        return this.maxElements;
    }

    public int getElements()
    {
        return this.elements;
    }

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

    public static NbtLimiter getDefault()
    {
        return new NbtLimiter(DEFAULT_MAX_BYTES, DEFAULT_MAX_COMPLEXITY, DEFAULT_MAX_ELEMENTS);
    }

    public static NbtLimiter getUnlimited()
    {
        return new NbtLimiter(0, 0, 0);
    }
}
