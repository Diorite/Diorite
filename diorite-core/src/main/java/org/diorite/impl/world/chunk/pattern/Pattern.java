package org.diorite.impl.world.chunk.pattern;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.material.BlockMaterialData;

public interface Pattern
{
    Pattern getNext();

    void clear();

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
//            return Material.AIR;
            return null;
        }
        return mat;
    }

    int removeBySection(int sectionID);

    int removeByMinecraft(int minecraftID);

    int size();

    default int bitsPerBlock()
    {
        final int size = this.size();
        if (size <= 1)
        {
            return 4;
        }
        return Math.max(4, Integer.SIZE - Integer.numberOfLeadingZeros(size - 1));
    }

    void write(PacketDataSerializer data);
}
