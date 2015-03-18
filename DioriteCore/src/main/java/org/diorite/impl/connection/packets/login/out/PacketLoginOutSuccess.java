package org.diorite.impl.connection.packets.login.out;

import java.io.IOException;

import com.mojang.authlib.GameProfile;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginOutListener;

@PacketClass(id = 0x02, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginOutSuccess implements PacketLoginOut
{
    private GameProfile profile;

    public PacketLoginOutSuccess()
    {
    }

    public PacketLoginOutSuccess(final GameProfile profile)
    {
        this.profile = profile;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.profile = data.readGameProfile();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeGameProfile(this.profile);
    }

    @Override
    public void handle(final PacketLoginOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("profile", this.profile).toString();
    }
}
