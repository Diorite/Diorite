package diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayInListener;

public class PacketPlayInKeepAlive implements PacketPlayIn
{
    private int id;

    public PacketPlayInKeepAlive()
    {
    }

    public PacketPlayInKeepAlive(final int id)
    {
        this.id = id;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.id = data.readVarInt();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.id);
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
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).toString();
    }
}
