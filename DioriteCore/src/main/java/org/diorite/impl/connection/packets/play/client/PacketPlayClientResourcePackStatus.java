package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x19, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 50)
public class PacketPlayClientResourcePackStatus extends PacketPlayClient
{
    public static final int MAX_HASH_SIZE = 40;
    private String             hash; // ~41 bytes
    private ResourcePackStatus status; // ~1 byte

    public PacketPlayClientResourcePackStatus()
    {
    }

    public PacketPlayClientResourcePackStatus(final String hash, final ResourcePackStatus status)
    {
        this.hash = hash;
        this.status = status;
    }

    public String getHash()
    {
        return this.hash;
    }

    public void setHash(final String hash)
    {
        this.hash = hash;
    }

    public ResourcePackStatus getStatus()
    {
        return this.status;
    }

    public void setStatus(final ResourcePackStatus status)
    {
        this.status = status;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.hash = data.readText(MAX_HASH_SIZE);
        this.status = ResourcePackStatus.byId(data.readVarInt());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.hash);
        data.writeVarInt(this.status.getId());
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public enum ResourcePackStatus
    {
        LOADED(0),
        DECLINED(1),
        FAILED_DOWNLOAD(2),
        ACCEPTED(3);

        private final int id;

        ResourcePackStatus(final int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        public static ResourcePackStatus byId(final int id)
        {
            for (final ResourcePackStatus rps : ResourcePackStatus.values())
            {
                if (id == rps.getId())
                {
                    return rps;
                }
            }
            return null;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("hash", this.hash).append("status", this.status).toString();
    }
}
