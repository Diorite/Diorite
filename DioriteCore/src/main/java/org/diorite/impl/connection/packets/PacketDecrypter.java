package org.diorite.impl.connection.packets;

import javax.crypto.Cipher;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketDecrypter extends MessageToMessageDecoder<ByteBuf>
{
    private final PacketEncryptionHandler handler;

    public PacketDecrypter(final Cipher paramCipher)
    {
        this.handler = new PacketEncryptionHandler(paramCipher);
    }

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> list) throws Exception
    {
        list.add(this.handler.handle(channelHandlerContext, byteBuf));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("handler", this.handler).toString();
    }
}

