package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x0F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 4)
public class PacketPlayClientTransaction extends PacketPlayClient
{
    private int     windowId; // 1 byte
    private short   actionNumber; // 2 bytes
    private boolean accepted;// 1 byte

    public PacketPlayClientTransaction()
    {
    }

    public PacketPlayClientTransaction(final int windowId, final short actionNumber, final boolean accepted)
    {
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        this.actionNumber = data.readShort();
        this.accepted = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.actionNumber);
        data.writeBoolean(this.accepted);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public void setActionNumber(final short actionNumber)
    {
        this.actionNumber = actionNumber;
    }

    public boolean isAccepted()
    {
        return this.accepted;
    }

    public void setAccepted(final boolean accepted)
    {
        this.accepted = accepted;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("actionNumber", this.actionNumber).append("accepted", this.accepted).toString();
    }
}
