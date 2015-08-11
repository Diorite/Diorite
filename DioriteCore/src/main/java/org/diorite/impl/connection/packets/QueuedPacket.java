package org.diorite.impl.connection.packets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class QueuedPacket
{
    private final Packet<?>                                               packet;
    private final GenericFutureListener<? extends Future<? super Void>>[] listeners;

    @SafeVarargs
    public QueuedPacket(final Packet<?> packet, final GenericFutureListener<? extends Future<? super Void>>... listeners)
    {
        this.packet = packet;
        this.listeners = listeners;
    }

    public Packet<?> getPacket()
    {
        return this.packet;
    }

    public GenericFutureListener<? extends Future<? super Void>>[] getListeners()
    {
        return this.listeners;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("packet", this.packet).append("listeners", this.listeners).toString();
    }
}
