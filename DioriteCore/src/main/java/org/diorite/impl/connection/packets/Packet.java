package org.diorite.impl.connection.packets;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.NetworkManager;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class Packet<T extends PacketListener>
{
    public static final int INITIAL_CAPACITY = 512;
    protected volatile byte[] data;

    public Packet()
    {
    }

    public Packet(final byte[] data)
    {
        this.data = data;
    }

    public abstract void readPacket(PacketDataSerializer data) throws IOException;

    public abstract void writeFields(PacketDataSerializer data) throws IOException;

    public abstract void handle(T listener);

    public synchronized byte[] preparePacket(final boolean force) throws IOException
    {
        if (force || (this.data == null))
        {
            final PacketDataSerializer newData = new PacketDataSerializer(Unpooled.buffer(INITIAL_CAPACITY));
            this.writeFields(newData);
            this.data = new byte[newData.readableBytes()];
            System.arraycopy(newData.array(), 0, this.data, 0, newData.readableBytes());
        }
        return this.data;
    }

    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        byte[] tempData = this.data;
        if (tempData == null)
        {
            tempData = this.preparePacket(false);
        }
        if (tempData == null)
        {
            throw new IllegalStateException("Packet data isn't prepared after preparing!");
        }
        data.writeBytes(tempData);
    }

    public byte[] getCachedData()
    {
        return this.data;
    }

    public void setCachedData(final byte[] data)
    {
        this.data = data;
    }

    public int getPacketID()
    {
        return getPacketID(this.getClass());
    }

    public void send(final NetworkManager networkManager)
    {
        networkManager.sendPacket(this);
    }

    @SuppressWarnings("unchecked")
    public void send(final NetworkManager networkManager, final GenericFutureListener<? extends Future<? super Void>> listener, final GenericFutureListener<? extends Future<? super Void>>... listeners)
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", Integer.toHexString(this.getPacketID())).toString();
    }
}