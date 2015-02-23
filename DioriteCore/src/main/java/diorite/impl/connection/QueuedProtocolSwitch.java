package diorite.impl.connection;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import diorite.impl.connection.packets.Packet;

class QueuedProtocolSwitch implements Runnable
{
    private final NetworkManager                                          networkManager;
    private final EnumProtocol                                            ep1;
    private final EnumProtocol                                            ep2;
    private final Packet<?>                                               packet;
    private final GenericFutureListener<? extends Future<? super Void>>[] listeners;

    @SafeVarargs
    QueuedProtocolSwitch(final NetworkManager networkManager, final EnumProtocol ep1, final EnumProtocol ep2, final Packet<?> packet, final GenericFutureListener<? extends Future<? super Void>>... listeners)
    {
        this.networkManager = networkManager;
        this.ep1 = ep1;
        this.ep2 = ep2;
        this.packet = packet;
        this.listeners = listeners;
    }

    @Override
    public void run()
    {
        if (this.ep1 != this.ep2)
        {
            this.networkManager.setProtocol(this.ep1);
        }
        final ChannelFuture channelFuture = this.networkManager.getChannel().writeAndFlush(this.packet);
        if (this.listeners != null)
        {
            //noinspection unchecked
            channelFuture.addListeners(this.listeners);
        }
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("networkManager", this.networkManager).append("ep1", this.ep1).append("ep2", this.ep2).append("packet", this.packet).append("listeners", this.listeners).toString();
    }
}

