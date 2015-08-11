package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x0D, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayClientCloseWindow extends PacketPlayClient
{
    private int id;

    public PacketPlayClientCloseWindow()
    {
    }

    public PacketPlayClientCloseWindow(final int id)
    {
        this.id = id;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.id = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.id);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(final int id)
    {
        this.id = id;
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).toString();
    }
}
