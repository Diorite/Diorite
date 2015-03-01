package diorite.impl.connection.packets.play.in;

import java.io.IOException;

import diorite.BlockLocation;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x07, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInBlockDig implements PacketPlayIn
{
    private BlockLocation blockLocation; // TODO: implement

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
