package org.diorite.impl.world.chunk.palette;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

public class GlobalPaletteImpl implements PaletteData
{
    private static final   GlobalPaletteImpl inst                   = new GlobalPaletteImpl();
    protected static final int               DEFAULT_BITS_PER_BLOCK = 13;

    private GlobalPaletteImpl()
    {
    }

    @Override
    public void read(final PacketDataSerializer data)
    {
        data.readVarInt();
    }

    public static GlobalPaletteImpl get()
    {
        return inst;
    }

    @Override
    public PaletteData getNext()
    {
        throw new IllegalArgumentException("This is last pattern mode.");
    }

    @Override
    public PaletteData clone()
    {
        return this; // no need to clone
    }

    @Override
    public int put(final int minecraftIDandData)
    {
        return minecraftIDandData;
    }

    @Override
    public int put(final int minecraftID, final byte minecafrData)
    {
        return (minecraftID << 4) | minecafrData;
    }

    @Override
    public int put(final BlockMaterialData data)
    {
        return (data.getId() << 4) | data.getType();
    }

    @Override
    public int getAsInt(final int sectionID)
    {
        return sectionID;
    }

    @Override
    public BlockMaterialData get(final int sectionID)
    {
        final BlockMaterialData data = (BlockMaterialData) BlockMaterialData.getByID(sectionID >> 4, sectionID & 15);
        if (data == null)
        {
            return Material.AIR;
        }
        return data;
    }

    @Override
    public int size()
    {
        return Material.getAllItemMaterialsCount();
    }

    @Override
    public int byteSize()
    {
        return 1;
    }

    @Override
    public int bitsPerBlock()
    {
        return DEFAULT_BITS_PER_BLOCK;
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        data.writeVarInt(0);
    }
}
