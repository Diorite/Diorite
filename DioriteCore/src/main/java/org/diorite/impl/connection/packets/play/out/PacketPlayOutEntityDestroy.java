package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x13, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutEntityDestroy extends PacketPlayOut
{
    private int[] ids;

    public PacketPlayOutEntityDestroy()
    {
    }

    public PacketPlayOutEntityDestroy(final int... ids)
    {
        this.ids = ids;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        final int size = data.readVarInt();
        this.ids = new int[size];
        for (int i = 0; i < size; i++)
        {
            this.ids[i] = data.readVarInt();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.ids.length);
        for (final int i : this.ids)
        {
            data.writeVarInt(i);
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public int[] getIds()
    {
        return this.ids;
    }

    public void setIds(final int[] ids)
    {
        this.ids = ids;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ids", this.ids).toString();
    }
}
