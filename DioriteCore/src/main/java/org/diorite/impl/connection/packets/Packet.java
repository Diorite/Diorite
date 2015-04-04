package org.diorite.impl.connection.packets;

import java.io.IOException;

import org.diorite.impl.connection.NetworkManager;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface Packet<T extends PacketListener>
{
    void readPacket(PacketDataSerializer data) throws IOException;

    void writePacket(PacketDataSerializer data) throws IOException;

    void handle(T listener);

    default int getPacketID()
    {
        return getPacketID(this.getClass());
    }

    default void send(final NetworkManager networkManager)
    {
        networkManager.sendPacket(this);
    }

    @SuppressWarnings("unchecked")
    default void send(final NetworkManager networkManager, final GenericFutureListener<? extends Future<? super Void>> listener, final GenericFutureListener<? extends Future<? super Void>>... listeners)
    {
        networkManager.sendPacket(this, listener, listeners);
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