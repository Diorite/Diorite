package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.diorite.BlockFace;
import org.diorite.BlockLocation;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x08, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInBlockPlace implements PacketPlayIn
{
    private BlockLocation location;
    private BlockFace     blockFace;
//    private int

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }
}
