package org.diorite.impl.world.chunk.palette;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;

public class PaletteImpl implements Palette
{
    private PaletteData palette = new ArrayPaletteImpl();

    public PaletteImpl()
    {
    }

    @Override
    public Palette getNext()
    {
        this.palette = this.palette.getNext();

        return this;
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        int k = this.palette.put(minecraftIDandData);
        if (k == - 1)
        {
            this.getNext();
            k = this.palette.put(minecraftIDandData);
        }
        return k;
    }

    @Override
    public int put(final int minecraftID, final byte minecafrData)
    {
        return this.palette.put(minecraftID, minecafrData);
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        return this.palette.put(data);
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        return this.palette.get(sectionID);
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return this.palette.getAsInt(sectionID);
    }

    @Override
    public int size()
    {
        return this.palette.size();
    }

    @Override
    public int bitsPerBlock()
    {
        return this.palette.bitsPerBlock();
    }

    @Override
    public int byteSize()
    {
        return this.palette.byteSize();
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        data.writeByte(this.bitsPerBlock());
        this.palette.write(data);
    }

    public void read(final PacketDataSerializer data)
    {
        final int bpb = data.readUnsignedByte();
        if (bpb == 0)
        {
            this.palette = GlobalPaletteImpl.get();
        }
        if (bpb <= 4)
        {
            this.palette = new ArrayPaletteImpl();
        }
        else
        {
            this.palette = new MapPaletteImpl();
        }
        this.palette.read(data);
    }

    private PaletteImpl(final PaletteData data)
    {
        this.palette = data;
    }

    @Override
    public PaletteImpl clone()
    {
        return new PaletteImpl(this.palette.clone());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("palette", this.palette).toString();
    }

}
