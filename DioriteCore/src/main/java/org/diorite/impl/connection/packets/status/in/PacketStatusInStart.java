package org.diorite.impl.connection.packets.status.in;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusInListener;

@PacketClass(id = 0x00, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketStatusInStart extends PacketStatusIn
{

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void handle(final PacketStatusInListener listener)
    {
        listener.handle(this);
    }
}