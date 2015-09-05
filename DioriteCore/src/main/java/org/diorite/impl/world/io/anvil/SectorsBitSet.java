package org.diorite.impl.world.io.anvil;

import java.util.BitSet;

@SuppressWarnings("ClassHasNoToStringMethod")
class SectorsBitSet extends BitSet
{
    private static final long serialVersionUID = 0L;

    private int largestIndex;

    SectorsBitSet()
    {
    }

    SectorsBitSet(final int nbits)
    {
        super(nbits);
    }

    private void checkLargest(final int index)
    {
        if (index >= this.largestIndex)
        {
            this.largestIndex = index + 1;
        }
    }

    public int getLargestIndex()
    {
        return this.largestIndex;
    }

    @Override
    public void flip(final int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.flip(bitIndex);
    }

    @Override
    public void flip(final int fromIndex, final int toIndex)
    {
        this.checkLargest(toIndex);
        super.flip(fromIndex, toIndex);
    }

    @Override
    public void set(final int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.set(bitIndex);
    }

    @Override
    public void set(final int bitIndex, final boolean value)
    {
        this.checkLargest(bitIndex);
        super.set(bitIndex, value);
    }

    @Override
    public void set(final int fromIndex, final int toIndex)
    {
        this.checkLargest(toIndex);
        super.set(fromIndex, toIndex);
    }

    @Override
    public void set(final int fromIndex, final int toIndex, final boolean value)
    {
        this.checkLargest(toIndex);
        super.set(fromIndex, toIndex, value);
    }

    @Override
    public void clear(final int bitIndex)
    {
        this.checkLargest(bitIndex);
        super.clear(bitIndex);
    }

    @Override
    public void clear(final int fromIndex, final int toIndex)
    {
        this.checkLargest(toIndex);
        super.clear(fromIndex, toIndex);
    }

    @Override
    public boolean get(final int bitIndex)
    {
        this.checkLargest(bitIndex);
        return super.get(bitIndex);
    }

    @Override
    public BitSet get(final int fromIndex, final int toIndex)
    {
        this.checkLargest(toIndex);
        return super.get(fromIndex, toIndex);
    }
}
