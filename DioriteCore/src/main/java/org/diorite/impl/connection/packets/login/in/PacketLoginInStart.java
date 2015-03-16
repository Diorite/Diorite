package org.diorite.impl.connection.packets.login.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import org.diorite.Server;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginInListener;

@PacketClass(id = 0x00, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketLoginInStart implements PacketLoginIn
{
    private GameProfile profile;

    public PacketLoginInStart()
    {
    }

    public PacketLoginInStart(final GameProfile profile)
    {
        this.profile = profile;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.profile = new GameProfile(null, data.readText(Server.MAX_NICKNAME_SIZE));
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.profile.getName());
    }

    @Override
    public void handle(final PacketLoginInListener listener)
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
