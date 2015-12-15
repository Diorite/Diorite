package org.diorite.impl.world.chunk.palette;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

public class ArrayPaletteImpl implements PaletteData
{
    private static final int SIZE = 16;

    protected final BlockMaterialData[] pattern;
    protected int lastIndex = 1;

    public ArrayPaletteImpl()
    {
        this.pattern = new BlockMaterialData[SIZE];
        this.pattern[0] = Material.AIR;
    }

    private ArrayPaletteImpl(final BlockMaterialData[] pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        final BlockMaterialData[] pattern = this.pattern;
        for (int i = 0; i < this.lastIndex; i++)
        {
            final BlockMaterialData materialData = pattern[i];
            if (materialData.getIdAndMeta() == data.getIdAndMeta())
            {
                return i;
            }
        }
        if (this.lastIndex >= pattern.length)
        {
            return - 1;
        }
        final int index = this.lastIndex++;
        pattern[index] = data;
        return index;
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        final BlockMaterialData[] pattern = this.pattern;
        for (int i = 0; i < this.lastIndex; i++)
        {
            final BlockMaterialData materialData = pattern[i];
            if (materialData.getIdAndMeta() == minecraftIDandData)
            {
                return i;
            }
        }
        if (this.lastIndex >= this.pattern.length)
        {
            return - 1;
        }
        final Material mat = Material.getByID(minecraftIDandData >> 4, minecraftIDandData & 15);
        if (! (mat instanceof BlockMaterialData))
        {
            throw new IllegalArgumentException("Unknown material: " + minecraftIDandData + " (" + (minecraftIDandData >> 4) + ":" + (minecraftIDandData & 15) + ")");
        }
        final int index = this.lastIndex++;
        pattern[index] = (BlockMaterialData) mat;
        return index;
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        final BlockMaterialData mat = this.pattern[sectionID];
        if (mat == null)
        {
            return 0;
        }
        return mat.getIdAndMeta();
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        final BlockMaterialData mat = this.pattern[sectionID];
        if (mat == null)
        {
            return BlockMaterialData.AIR;
        }
        return mat;
    }

    @Override
    public int size()
    {
        return this.lastIndex;
    }

    @Override
    public int bitsPerBlock()
    {
        return 4;
    }

    @Override
    public int byteSize()
    {
        int bytes = PacketDataSerializer.varintSize(this.lastIndex);
        for (int var2 = 0; var2 < this.lastIndex; ++ var2)
        {
            bytes += PacketDataSerializer.varintSize(this.pattern[var2].getIdAndMeta());
        }
        return bytes;
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        if (CoreMain.isEnabledDebug()) // TODO: remove
        {
            int i = 0;
            data.writeVarInt(this.lastIndex);
            for (final BlockMaterialData materialData : this.pattern)
            {
                if (materialData == null)
                {
                    break;
                }
                i++;
                data.writeVarInt(materialData.getIdAndMeta());
            }
            if (this.lastIndex != i)
            {
                throw new AssertionError("Fail");
            }
            return;
        }
        data.writeVarInt(this.lastIndex);
        for (final BlockMaterialData materialData : this.pattern)
        {
            if (materialData == null)
            {
                break;
            }
            data.writeVarInt(materialData.getIdAndMeta());
        }
    }

    @Override
    public void read(final PacketDataSerializer data)
    {
        final int size = data.readVarInt();
        for (int i = 0; i < size; i++)
        {
            final int id = data.readVarInt();
            final Material mat = Material.getByID(id >> 4, id & 15);
            if (! (mat instanceof BlockMaterialData))
            {
                throw new IllegalArgumentException("Unknown material: " + id + " (" + (id >> 4) + ":" + (id & 15) + ")");
            }
            this.pattern[i] = (BlockMaterialData) mat;
        }
    }

    @Override
    public PaletteData getNext()
    {
        return new MapPaletteImpl(this);
    }

    @Override
    public ArrayPaletteImpl clone()
    {
        return new ArrayPaletteImpl(this.pattern.clone());
    }

//    @Override
//    public void clear()
//    {
//        Arrays.fill(this.pattern, - 1);
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).toString();
    }
}
