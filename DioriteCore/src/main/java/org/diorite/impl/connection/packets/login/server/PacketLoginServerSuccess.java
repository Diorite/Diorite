package org.diorite.impl.connection.packets.login.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginServerListener;

@PacketClass(id = 0x02, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginServerSuccess extends PacketLoginServer
{
    private GameProfile profile;

    public PacketLoginServerSuccess()
    {
    }

    public PacketLoginServerSuccess(final GameProfile profile)
    {
        this.profile = profile;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.profile = data.readGameProfile();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeGameProfile(this.profile);
    }

    @Override
    public void handle(final PacketLoginServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("profile", this.profile).toString();
    }
}
