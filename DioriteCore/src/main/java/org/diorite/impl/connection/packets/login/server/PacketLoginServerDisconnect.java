package org.diorite.impl.connection.packets.login.server;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginServerListener;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x00, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginServerDisconnect extends PacketLoginServer
{
    private BaseComponent msg;

    public PacketLoginServerDisconnect()
    {
    }

    public PacketLoginServerDisconnect(final BaseComponent msg)
    {
        this.msg = msg;
    }

    @Override
    public void readPacket(final PacketDataSerializer paramPacketDataSerializer)
    {
        this.msg = paramPacketDataSerializer.readBaseComponent();
    }

    @Override
    public void writeFields(final PacketDataSerializer paramPacketDataSerializer)
    {
        paramPacketDataSerializer.writeBaseComponent(this.msg);
    }

    @Override
    public void handle(final PacketLoginServerListener paramPacketLoginOutListener)
    {
        paramPacketLoginOutListener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("msg", this.msg).toString();
    }
}
