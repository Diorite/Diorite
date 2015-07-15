package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x18, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInSpectate extends PacketPlayIn
{
    private UUID playerUuid;

    public PacketPlayInSpectate()
    {
    }

    public PacketPlayInSpectate(final UUID playerUuid)
    {
        this.playerUuid = playerUuid;
    }

    public UUID getPlayerUuid()
    {
        return this.playerUuid;
    }

    public void setPlayerUuid(final UUID playerUuid)
    {
        this.playerUuid = playerUuid;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.playerUuid = data.readUUID();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeUUID(this.playerUuid);
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("playerUuid", this.playerUuid).toString();
    }
}
