package org.diorite.impl.connection.packets;

import javax.crypto.Cipher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf>
{
    private final PacketEncryptionHandler handler;

    public PacketEncrypter(final Cipher cipher)
    {
        this.handler = new PacketEncryptionHandler(cipher);
    }

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf srcByteBuf, final ByteBuf byteBuf) throws Exception
    {
        this.handler.copy(srcByteBuf, byteBuf);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("handler", this.handler).toString();
    }
}