package diorite.impl.connection.packets.login.out;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.chat.BaseComponent;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.login.PacketLoginOutListener;

@PacketClass(id = 0x00, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginOutDisconnect implements PacketLoginOut
{
    private BaseComponent msg;

    public PacketLoginOutDisconnect()
    {
    }

    public PacketLoginOutDisconnect(final BaseComponent msg)
    {
        this.msg = msg;
    }

    @Override
    public void readPacket(final PacketDataSerializer paramPacketDataSerializer)
    {
        this.msg = paramPacketDataSerializer.readBaseComponent();
    }

    @Override
    public void writePacket(final PacketDataSerializer paramPacketDataSerializer)
    {
        paramPacketDataSerializer.writeBaseComponent(this.msg);
    }

    @Override
    public void handle(final PacketLoginOutListener paramPacketLoginOutListener)
    {
        paramPacketLoginOutListener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("msg", this.msg).toString();
    }
}
