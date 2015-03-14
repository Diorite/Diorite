package diorite.impl.connection.packets;

import java.io.IOException;

public interface Packet<T extends PacketListener>
{
    void readPacket(PacketDataSerializer data) throws IOException;

    void writePacket(PacketDataSerializer data) throws IOException;

    void handle(T listener);

    default int getPacketID()
    {
        return getPacketID(this.getClass());
    }

    public static int getPacketID(final Packet<?> packet)
    {
        return getPacketID(packet.getClass());
    }

    public static int getPacketID(@SuppressWarnings("rawtypes") final Class<? extends Packet> clazz)
    {
        if (! clazz.isAnnotationPresent(PacketClass.class))
        {
            throw new IllegalArgumentException("To use this method, class must be annotated with PacketClass");
        }
        return clazz.getAnnotation(PacketClass.class).id();
    }
}