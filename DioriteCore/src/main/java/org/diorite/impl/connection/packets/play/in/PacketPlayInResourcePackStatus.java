package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x19, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInResourcePackStatus implements PacketPlayIn
{
    private String hash;
    private ResourcePackStatus status;

    public PacketPlayInResourcePackStatus()
    {
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
        this.hash = data.readText(40);
        this.status = ResourcePackStatus.byId(data.readVarInt());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.hash);
        data.writeVarInt(this.status.getId());
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    public enum ResourcePackStatus
    {
        LOADED(0), DECLINED(1), FAILED_DOWNLOAD(2), ACCEPTED(3);

        private int id;

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
            for(final ResourcePackStatus rps : ResourcePackStatus.values())
            {
                if(id == rps.getId())
                {
                    return rps;
                }
            }
            return null;
        }
    }
}
