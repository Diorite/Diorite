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

@PacketClass(id = 0x14, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInTabComplete implements PacketPlayIn
{
    private String        cmdLine;
    private BlockLocation blockLocation;

    public PacketPlayInTabComplete()
    {
    }

    public PacketPlayInTabComplete(final String cmdLine)
    {
        this.cmdLine = cmdLine;
    }

    public PacketPlayInTabComplete(final String cmdLine, final BlockLocation blockLocation)
    {
        this.cmdLine = cmdLine;
        this.blockLocation = blockLocation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.cmdLine = data.readText(Short.MAX_VALUE);
        this.blockLocation = data.readBoolean() ? data.readBlockLocation() : null;
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.cmdLine);
        data.writeBoolean(this.blockLocation != null);
        if (this.blockLocation != null)
        {
            data.writeBlockLocation(this.blockLocation);
        }
    }

    public String getCmdLine()
    {
        return this.cmdLine;
    }

    public void setCmdLine(final String cmdLine)
    {
        this.cmdLine = cmdLine;
    }

    public BlockLocation getBlockLocation()
    {
        return this.blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cmdLine", this.cmdLine).append("blockLocation", this.blockLocation).toString();
    }
}
