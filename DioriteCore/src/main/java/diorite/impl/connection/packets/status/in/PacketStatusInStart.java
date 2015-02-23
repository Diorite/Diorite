package diorite.impl.connection.packets.status.in;

import java.io.IOException;

import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.listeners.PacketStatusInListener;

public class PacketStatusInStart implements PacketStatusIn
{

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {

    }

    @Override
    public void handle(final PacketStatusInListener listener)
    {
        listener.handle(this);
    }
}