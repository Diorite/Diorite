package org.diorite.impl.connection.packets;

import javax.crypto.Cipher;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class PacketEncryptor extends ByteToMessageCodec<ByteBuf>
{
    private final PacketEncryptionHandler handler1;
    private final PacketEncryptionHandler handler2;

    public PacketEncryptor(final Cipher cipher1, final Cipher cipher2)
    {
        this.handler1 = new PacketEncryptionHandler(cipher1);
        this.handler2 = new PacketEncryptionHandler(cipher2);
    }

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf srcByteBuf, final ByteBuf byteBuf) throws Exception
    {
        this.handler1.copy(srcByteBuf, byteBuf);
    }

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws Exception
    {
        list.add(this.handler2.handle(channelHandlerContext, byteBuf));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("handler1", this.handler1).append("handler2", this.handler2).toString();
    }
}

