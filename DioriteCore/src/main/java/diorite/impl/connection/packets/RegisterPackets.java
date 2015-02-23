package diorite.impl.connection.packets;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;
import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;
import diorite.impl.connection.packets.status.in.PacketStatusInPing;
import diorite.impl.connection.packets.status.in.PacketStatusInStart;
import diorite.impl.connection.packets.status.out.PacketStatusOutPong;
import diorite.impl.connection.packets.status.out.PacketStatusOutServerInfo;

public final class RegisterPackets
{
    private RegisterPackets()
    {
    }

    @SuppressWarnings("MagicNumber")
    public static void init() // all packets needs to be registred here
    {
        EnumProtocol.HANDSHAKING.init(EnumProtocolDirection.SERVERBOUND, 0, PacketHandshakingInSetProtocol.class);

        EnumProtocol.LOGIN.init(EnumProtocolDirection.CLIENTBOUND, 0, PacketLoginOutDisconnect.class);

        EnumProtocol.STATUS.init(EnumProtocolDirection.SERVERBOUND, 0, PacketStatusInStart.class);
        EnumProtocol.STATUS.init(EnumProtocolDirection.CLIENTBOUND, 0, PacketStatusOutServerInfo.class);
        EnumProtocol.STATUS.init(EnumProtocolDirection.SERVERBOUND, 1, PacketStatusInPing.class);
        EnumProtocol.STATUS.init(EnumProtocolDirection.CLIENTBOUND, 1, PacketStatusOutPong.class);
    }
}
