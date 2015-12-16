package org.diorite.impl.world.chunk.palette;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

public interface Palette
{
    Palette getNext();

//    void clear();

    int put(int minecraftIDandData); // returns -1 if id failed to add as pattern can't fit more ids

    default int put(final int minecraftID, final byte minecafrData)
    {
        return this.put(((minecraftID << 4) | minecafrData));
    }

    default int put(final BlockMaterialData data)
    {
        return this.put(((data.getId() << 4) | data.getType()));
    }

    int getAsInt(int sectionID);

    default BlockMaterialData get(final int sectionID)
    {
        final int data = this.getAsInt(sectionID);
        final BlockMaterialData mat = (BlockMaterialData) BlockMaterialData.getByID(data >> 4, data & 15);
        if (mat == null)
        {
            return Material.AIR;
        }
        return mat;
    }

    int size();

    int bitsPerBlock();

    int byteSize();

    void write(PacketDataSerializer data);
}
