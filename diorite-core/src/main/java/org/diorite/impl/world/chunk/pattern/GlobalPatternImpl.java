package org.diorite.impl.world.chunk.pattern;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

public class GlobalPatternImpl implements PatternData
{
    private static final   GlobalPatternImpl inst                   = new GlobalPatternImpl();
    protected static final int               DEFAULT_BITS_PER_BLOCK = 13;

    private GlobalPatternImpl()
    {
    }

    @Override
    public void read(final PacketDataSerializer data, final int size)
    {

    }

    public static GlobalPatternImpl get()
    {
        return inst;
    }

    @Override
    public PatternData getNext()
    {
        throw new IllegalArgumentException("This is last pattern mode.");
    }

    @Override
    public PatternData clone()
    {
        return this; // no need to clone
    }

    @Override
    public void clear()
    {

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
//            return Material.AIR;
            return null;
        }
        return data;
    }

    @Override
    public int removeBySection(final int sectionID)
    {
        return - 1;
    }

    @Override
    public int removeByMinecraft(final int minecraftID)
    {
        return - 1;
    }

    @Override
    public int size()
    {
        return Material.getAllItemMaterialsCount();
    }

    @Override
    public int bitsPerBlock()
    {
        return 0;
    }

    @Override
    public void write(final PacketDataSerializer data)
    {
        data.writeByte(this.bitsPerBlock());
    }
}
