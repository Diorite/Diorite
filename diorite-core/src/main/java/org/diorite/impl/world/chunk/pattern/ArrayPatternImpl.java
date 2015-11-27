package org.diorite.impl.world.chunk.pattern;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.Material;

public class ArrayPatternImpl implements PatternData
{
    private static final int SIZE = 16;

    protected final int[] pattern;

    public ArrayPatternImpl()
    {
        this.pattern = new int[SIZE];
        Arrays.fill(this.pattern, - 1);
        this.pattern[0] = 0;
    }

    private ArrayPatternImpl(final int[] pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        int i = 0;
        for (final int data : this.pattern)
        {
            if (data == minecraftIDandData)
            {
                return i;
            }
            if (data == - 1)
            {
                if (Material.getByID(minecraftIDandData >> 4, minecraftIDandData & 15) == null)
                {
                    throw new IllegalArgumentException("Unknown material: " + minecraftIDandData + " (" + (minecraftIDandData >> 4) + ":" + (minecraftIDandData & 15) + ")");
                }
                this.pattern[i] = minecraftIDandData;
                return i;
            }
            i++;
        }
        return - 1;
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        final int i = this.pattern[sectionID];
        if (i == - 1)
        {
            return 0;
        }
        return i;
    }

    @Override
    public int removeBySection(final int sectionID)
    {
        return this.pattern[sectionID] = - 1;
    }

    @Override
    public int removeByMinecraft(final int minecraftID)
    {
        for (int i = 0; i < this.pattern.length; i++)
        {
            if (this.pattern[i] == minecraftID)
            {
                this.pattern[i] = - 1;
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int size()
    {
        return SIZE;
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        data.writeByte(this.bitsPerBlock());
        data.writeVarInt(this.pattern.length);
        for (final int i : this.pattern)
        {
            data.writeVarInt(i);
        }
    }

    @Override
    public void read(final PacketDataSerializer data, final int size)
    {
        for (int i = 0; i < size; i++)
        {
            this.pattern[i] = data.readVarInt();
        }
    }

    @Override
    public PatternData getNext()
    {
        return new MapPatternImpl(this);
    }

    @Override
    public ArrayPatternImpl clone()
    {
        return new ArrayPatternImpl(this.pattern.clone());
    }

    @Override
    public void clear()
    {
        Arrays.fill(this.pattern, - 1);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
