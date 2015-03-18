package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.BlockLocation;

@PacketClass(id = 0x05, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutSpawnPosition implements PacketPlayOut
{
    private BlockLocation location;

    public PacketPlayOutSpawnPosition()
    {
    }

    public PacketPlayOutSpawnPosition(final BlockLocation location)
    {
        this.location = location;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).toString();
    }
}
