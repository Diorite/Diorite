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
    private String        content;
    private BlockLocation blockLocation;

    public PacketPlayInTabComplete()
    {
    }

    public PacketPlayInTabComplete(final String content)
    {
        this.content = content;
    }

    public PacketPlayInTabComplete(final String content, final BlockLocation blockLocation)
    {
        this.content = content;
        this.blockLocation = blockLocation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.content = data.readText(Short.MAX_VALUE);
        this.blockLocation = data.readBoolean() ? data.readBlockLocation() : null;
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.content);
        data.writeBoolean(this.blockLocation != null);
        if (this.blockLocation != null)
        {
            data.writeBlockLocation(this.blockLocation);
        }
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(final String content)
    {
        this.content = content;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("blockLocation", this.blockLocation).toString();
    }
}
