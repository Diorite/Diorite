package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x0A, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 0)
public class PacketPlayClientArmAnimation extends PacketPlayClient
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
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }
}
