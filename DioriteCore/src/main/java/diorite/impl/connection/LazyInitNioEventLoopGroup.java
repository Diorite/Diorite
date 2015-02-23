package diorite.impl.connection;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import diorite.impl.utils.LazyInitVar;
import io.netty.channel.nio.NioEventLoopGroup;

public class LazyInitNioEventLoopGroup extends LazyInitVar<NioEventLoopGroup>
{
    @Override
    protected NioEventLoopGroup init()
    {
        return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
    }
}
