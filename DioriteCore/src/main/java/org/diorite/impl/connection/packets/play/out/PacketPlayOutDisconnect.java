package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x40, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutDisconnect implements PacketPlayOut
{
    private BaseComponent content;

    public PacketPlayOutDisconnect()
    {
    }

    public PacketPlayOutDisconnect(final BaseComponent content)
    {
        this.content = content;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.content = data.readBaseComponent();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBaseComponent(this.content);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public BaseComponent getContent()
    {
        return this.content;
    }

    public void setContent(final BaseComponent content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).toString();
    }
}
