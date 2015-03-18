package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.ClientCommand;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x16, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInClientCommand implements PacketPlayIn
{
    private ClientCommand command;

    public PacketPlayInClientCommand()
    {
    }

    public PacketPlayInClientCommand(final ClientCommand command)
    {
        this.command = command;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.command = ClientCommand.getByID(data.readVarInt());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.command.getId());
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    public ClientCommand getCommand()
    {
        return this.command;
    }

    public void setCommand(final ClientCommand command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("command", this.command).toString();
    }
}
