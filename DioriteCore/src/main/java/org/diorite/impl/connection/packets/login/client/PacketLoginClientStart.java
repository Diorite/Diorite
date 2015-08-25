package org.diorite.impl.connection.packets.login.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginClientListener;
import org.diorite.Core;

@PacketClass(id = 0x00, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.SERVERBOUND, size = 17)
public class PacketLoginClientStart extends PacketLoginClient
{
    private GameProfile profile; // ~16 bytes + 1 byte for size

    public PacketLoginClientStart()
    {
    }

    public PacketLoginClientStart(final GameProfile profile)
    {
        this.profile = profile;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.profile = new GameProfile(null, data.readText(Core.MAX_NICKNAME_SIZE));
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.profile.getName());
    }

    @Override
    public void handle(final PacketLoginClientListener listener)
    {
        listener.handle(this);
    }

    public GameProfile getProfile()
    {
        return this.profile;
    }

    public void setProfile(final GameProfile profile)
    {
        this.profile = profile;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("profile", this.profile).toString();
    }
}
