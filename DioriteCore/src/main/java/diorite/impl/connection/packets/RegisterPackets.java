package diorite.impl.connection.packets;

import org.reflections.Reflections;

import diorite.impl.connection.EnumProtocol;

public final class RegisterPackets
{
    private RegisterPackets()
    {
    }

    public static void init()
    {
        //noinspection unchecked
        new Reflections(RegisterPackets.class.getPackage().getName()).getTypesAnnotatedWith(PacketClass.class, false).parallelStream().filter(Packet.class::isAssignableFrom).map(packet -> (Class<Packet<?>>) packet).forEach(EnumProtocol::init);
    }
}
