package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.BlockLocation;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x12, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInUpdateSign implements PacketPlayIn
{
    private BlockLocation location;
    private BaseComponent[] lines;

    public PacketPlayInUpdateSign()
    {
    }

    public PacketPlayInUpdateSign(final BlockLocation location, final BaseComponent... lines)
    {
        this.location = location;
        this.lines = lines;
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public void setLocation(final BlockLocation location)
    {
        this.location = location;
    }

    public BaseComponent getLine(final int id)
    {
        return this.lines[id];
    }

    public void setLine(final int id, final BaseComponent text)
    {
        this.lines[id] = text;
    }

    public BaseComponent[] getLines()
    {
        return this.lines;
    }

    public void setLines(final BaseComponent[] lines)
    {
        this.lines = lines;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();

        this.lines = new BaseComponent[4];
        for (int i = 0; i < 4; i++)
        {
            this.lines[i] = data.readBaseComponent();
        }
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
        for (int i = 0; i < 4; i++)
        {
            data.writeBaseComponent(this.lines[i]);
        }
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("lines", this.lines).toString();
    }
}
