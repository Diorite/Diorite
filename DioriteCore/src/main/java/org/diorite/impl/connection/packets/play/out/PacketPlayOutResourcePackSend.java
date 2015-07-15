package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.impl.connection.packets.play.in.PacketPlayInResourcePackStatus;

@PacketClass(id = 0x48, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutResourcePackSend extends PacketPlayOut
{
    private String url;
    private String hash;

    public PacketPlayOutResourcePackSend()
    {
    }

    public PacketPlayOutResourcePackSend(final String url, final String hash)
    {
        if (hash.length() > PacketPlayInResourcePackStatus.MAX_HASH_SIZE)
        {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
        }

        this.url = url;
        this.hash = hash;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(final String url)
    {
        this.url = url;
    }

    public String getHash()
    {
        return this.hash;
    }

    public void setHash(final String hash)
    {
        this.hash = hash;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.url = data.readText(Short.MAX_VALUE);
        this.hash = data.readText(PacketPlayInResourcePackStatus.MAX_HASH_SIZE);
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.url);
        data.writeText(this.hash);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("url", this.url).append("hash", this.hash).toString();
    }
}
