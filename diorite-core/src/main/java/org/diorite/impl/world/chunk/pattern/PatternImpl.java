package org.diorite.impl.world.chunk.pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;

public class PatternImpl implements Pattern
{
    private PatternData pattern = new ArrayPatternImpl();

    public PatternImpl()
    {
    }

    @Override
    public Pattern getNext()
    {
        this.pattern = this.pattern.getNext();

        return this;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public int put(final int minecraftIDandData)
    {
        int k = this.pattern.put(minecraftIDandData);
        if (k == - 1)
        {
            this.getNext();
            k = this.pattern.put(minecraftIDandData);
        }
        return k;
    }

    @Override
    public int put(final int minecraftID, final byte minecafrData)
    {
        return this.pattern.put(minecraftID, minecafrData);
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        return this.pattern.put(data);
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        return this.pattern.get(sectionID);
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return this.pattern.getAsInt(sectionID);
    }

    @Override
    public int removeBySection(final int sectionID)
    {
        return this.pattern.removeBySection(sectionID);
    }

    @Override
    public int removeByMinecraft(final int minecraftID)
    {
        return this.pattern.removeByMinecraft(minecraftID);
    }

    @Override
    public int size()
    {
        return this.pattern.size();
    }

    @Override
    public int bitsPerBlock()
    {
        return this.pattern.bitsPerBlock();
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        this.pattern.write(data);
    }

    public void read(final PacketDataSerializer data)
    {
        final int bpb = data.readUnsignedByte();
        if (bpb == 0)
        {
            this.pattern = GlobalPatternImpl.get();
            return;
        }
        final int size = data.readVarInt();
        if (size <= 16)
        {
            this.pattern = new ArrayPatternImpl();
            this.pattern.read(data, size);
            return;
        }
        this.pattern = new MapPatternImpl();
        this.pattern.read(data, size);
    }

    private PatternImpl(final PatternData data)
    {
        this.pattern = data;
    }

    @Override
    public PatternImpl clone()
    {
        return new PatternImpl(this.pattern.clone());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }

}
