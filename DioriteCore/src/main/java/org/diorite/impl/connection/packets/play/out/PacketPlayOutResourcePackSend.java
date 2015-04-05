package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x48, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutResourcePackSend implements PacketPlayOut
{
    private String url;
    private String hash;

    public PacketPlayOutResourcePackSend()
    {
    }

    public PacketPlayOutResourcePackSend(final String url, final String hash)
    {
        if (hash.length() > 40)
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
        this.url = data.readText(32767);
        this.hash = data.readText(40);
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.url);
        data.writeText(this.hash);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }
}
