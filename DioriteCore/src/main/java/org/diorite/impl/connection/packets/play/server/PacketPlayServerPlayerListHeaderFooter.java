package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x47, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayServerPlayerListHeaderFooter extends PacketPlayServer
{
    private BaseComponent header;
    private BaseComponent footer;

    public PacketPlayServerPlayerListHeaderFooter()
    {
    }

    public PacketPlayServerPlayerListHeaderFooter(final BaseComponent header, final BaseComponent footer)
    {
        this.header = header;
        this.footer = footer;
    }

    public BaseComponent getHeader()
    {
        return this.header;
    }

    public void setHeader(final BaseComponent header)
    {
        this.header = header;
    }

    public BaseComponent getFooter()
    {
        return this.header;
    }

    public void setFooter(final BaseComponent footer)
    {
        this.footer = footer;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.header = data.readBaseComponent();
        this.footer = data.readBaseComponent();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBaseComponent(this.header);
        data.writeBaseComponent(this.footer);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("header", this.header).append("footer", this.footer).toString();
    }
}
