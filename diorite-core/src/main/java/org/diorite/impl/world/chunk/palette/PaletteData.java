package org.diorite.impl.world.chunk.palette;

import org.diorite.impl.connection.packets.PacketDataSerializer;

public interface PaletteData extends Palette
{
    void read(PacketDataSerializer data);

    @Override
    PaletteData getNext();

    PaletteData clone();
}
