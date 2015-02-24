package diorite.impl.connection.packets;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;
import diorite.impl.connection.packets.login.in.PacketLoginInEncryptionBegin;
import diorite.impl.connection.packets.login.in.PacketLoginInStart;
import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;
import diorite.impl.connection.packets.login.out.PacketLoginOutEncryptionBegin;
import diorite.impl.connection.packets.login.out.PacketLoginOutSetCompression;
import diorite.impl.connection.packets.login.out.PacketLoginOutSuccess;
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
        // handshake
        {
            // serverbound
            {
                EnumProtocol.HANDSHAKING.init(EnumProtocolDirection.SERVERBOUND, 0, PacketHandshakingInSetProtocol.class);
            }
            // clientbound
            {

            }
        }

        // status
        {
            // setrverbound
            {
                EnumProtocol.STATUS.init(EnumProtocolDirection.SERVERBOUND, 0, PacketStatusInStart.class);
                EnumProtocol.STATUS.init(EnumProtocolDirection.SERVERBOUND, 1, PacketStatusInPing.class);
            }
            // clientbound
            {
                EnumProtocol.STATUS.init(EnumProtocolDirection.CLIENTBOUND, 1, PacketStatusOutPong.class);
                EnumProtocol.STATUS.init(EnumProtocolDirection.CLIENTBOUND, 0, PacketStatusOutServerInfo.class);
            }
        }

        // login
        {
            // serverbound
            {
                EnumProtocol.LOGIN.init(EnumProtocolDirection.SERVERBOUND, 0, PacketLoginInStart.class);
                EnumProtocol.LOGIN.init(EnumProtocolDirection.SERVERBOUND, 1, PacketLoginInEncryptionBegin.class);
            }
            // clientbound
            {
                EnumProtocol.LOGIN.init(EnumProtocolDirection.CLIENTBOUND, 0, PacketLoginOutDisconnect.class);
                EnumProtocol.LOGIN.init(EnumProtocolDirection.CLIENTBOUND, 1, PacketLoginOutEncryptionBegin.class);
                EnumProtocol.LOGIN.init(EnumProtocolDirection.CLIENTBOUND, 2, PacketLoginOutSuccess.class);
                EnumProtocol.LOGIN.init(EnumProtocolDirection.CLIENTBOUND, 3, PacketLoginOutSetCompression.class);
            }
        }
    }
}
