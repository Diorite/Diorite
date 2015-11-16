package org.diorite.impl.world.chunk.pattern;

import org.diorite.impl.connection.packets.PacketDataSerializer;

public interface PatternData extends Pattern
{
    void read(PacketDataSerializer data, int size);

    @Override
    PatternData getNext();

    PatternData clone();
}
