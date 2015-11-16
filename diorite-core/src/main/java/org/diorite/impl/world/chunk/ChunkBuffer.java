package org.diorite.impl.world.chunk;

import java.util.BitSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("MagicNumber")
public class ChunkBuffer
{
    private final BitSet bitSet;
    private final int    bitsPerBlock;
    private       int    nonZero;

    public ChunkBuffer(final int bitsPerBlock)
    {
        this.bitsPerBlock = (bitsPerBlock == 0) ? 13 : bitsPerBlock;
        this.bitSet = new BitSet(bitsPerBlock * ChunkPartImpl.CHUNK_DATA_SIZE);
    }

    private ChunkBuffer(final BitSet bitSet, final int bitsPerBlock)
    {
        this.bitSet = bitSet;
        this.bitsPerBlock = (bitsPerBlock == 0) ? 13 : bitsPerBlock;
        this.recalculateNonZero();
    }

    public void recalculateNonZero()
    {
        int k = 0;
        for (int i = 0; i < ChunkPartImpl.CHUNK_DATA_SIZE; i++)
        {
            if (this.get(i) != 0)
            {
                k++;
            }
        }
        this.nonZero = k;
    }

    public int nonZero()
    {
        return this.nonZero;
    }

    @Override
    public ChunkBuffer clone()
    {
        return new ChunkBuffer((BitSet) this.bitSet.clone(), this.bitsPerBlock);
    }

    public int getAndSet(final int n, final int value)
    {
        final int old = this.get(n);
        {
            final int size = this.bitsPerBlock;
            int powerOf2 = 1;
            final int abs = Math.abs(value);
            for (int i = 0; i < (size - 1); i++)
            {
                if ((abs & powerOf2) == powerOf2)
                {
                    this.bitSet.set((n * size) + i);
                }
                powerOf2 <<= 1;
            }
            if (value < 0)
            {
                this.bitSet.set(((n + 1) * size) - 1);
            }
        }
        if ((old == 0) && (value != 0))
        {
            this.nonZero++;
        }
        else if ((old != 0) && (value == 0))
        {
            this.nonZero--;
        }
        return old;
    }

    public int get(final int n)
    {
        final int size = this.bitsPerBlock;
        int ret = 0;
        int powerOf2 = 1;
        for (int i = 0; i < (size - 1); i++)
        {
            if (this.bitSet.get((n * size) + i))
            {
                ret += powerOf2;
            }
            powerOf2 <<= 1;
        }
        if (this.bitSet.get(((n + 1) * size) - 1))
        {
            ret = - ret;
        }
        return ret;
    }

    public void set(final int n, final int value)
    {
        this.getAndSet(n, value);
    }

    public byte[] getBytes()
    {
        final byte[] bytes = new byte[(this.bitsPerBlock * ChunkPartImpl.CHUNK_DATA_SIZE) / 8];
        for (int i = 0; i < this.bitSet.length(); i++)
        {
            if (this.bitSet.get(i))
            {
                if ((i % 8) == 0)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 1);
                }
                else if ((i % 8) == 1)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 2);
                }
                else if ((i % 8) == 2)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 4);
                }
                else if ((i % 8) == 3)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 8);
                }
                else if ((i % 8) == 4)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 16);
                }
                else if ((i % 8) == 5)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 32);
                }
                else if ((i % 8) == 6)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | 64);
                }
                else if ((i % 8) == 7)
                {
                    bytes[i / 8] = (byte) (bytes[i / 8] | - 128);
                }
            }
        }
        return bytes;
    }

    public void setBytes(final byte[] bytes)
    {
        for (int i = 0; i < bytes.length; i++)
        {
            if ((bytes[i] & 1) == 1)
            {
                this.bitSet.set((i << 3));
            }
            if ((bytes[i] & 2) == 2)
            {
                this.bitSet.set((i << 3) + 1);
            }
            if ((bytes[i] & 4) == 4)
            {
                this.bitSet.set((i << 3) + 2);
            }
            if ((bytes[i] & 8) == 8)
            {
                this.bitSet.set((i << 3) + 3);
            }
            if ((bytes[i] & 16) == 16)
            {
                this.bitSet.set((i << 3) + 4);
            }
            if ((bytes[i] & 32) == 32)
            {
                this.bitSet.set((i << 3) + 5);
            }
            if ((bytes[i] & 64) == 64)
            {
                this.bitSet.set((i << 3) + 6);
            }
            if ((bytes[i] & - 128) == - 128)
            {
                this.bitSet.set((i << 3) + 7);
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("bitSet", this.bitSet).append("bitsPerBlock", this.bitsPerBlock).toString();
    }
}
