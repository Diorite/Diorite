package diorite.impl.connection;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.channel.DefaultEventLoopGroup;
import diorite.impl.utils.LazyInitVar;

public class LazyInitDefaultEventLoopGroup extends LazyInitVar<DefaultEventLoopGroup>
{
    @Override
    protected DefaultEventLoopGroup init()
    {
        return new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
    }
}
