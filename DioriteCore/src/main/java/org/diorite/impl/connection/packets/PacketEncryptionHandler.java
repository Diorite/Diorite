package org.diorite.impl.connection.packets;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketEncryptionHandler
{
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final Cipher cipher;
    private byte[] b = EMPTY_BYTES;
    private byte[] c = EMPTY_BYTES;

    protected PacketEncryptionHandler(final Cipher cipher)
    {
        this.cipher = cipher;
    }

    private byte[] getBytes(final ByteBuf byteBuf)
    {
        final int readableBytes = byteBuf.readableBytes();
        if (this.b.length < readableBytes)
        {
            this.b = new byte[readableBytes];
        }
        byteBuf.readBytes(this.b, 0, readableBytes);
        return this.b;
    }

    protected ByteBuf handle(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws ShortBufferException
    {
        final int readableBytes = byteBuf.readableBytes();
        final byte[] arrayOfByte = this.getBytes(byteBuf);

        final ByteBuf newBuf = channelHandlerContext.alloc().heapBuffer(this.cipher.getOutputSize(readableBytes));
        newBuf.writerIndex(this.cipher.update(arrayOfByte, 0, readableBytes, newBuf.array(), newBuf.arrayOffset()));

        return newBuf;
    }

    protected void copy(final ByteBuf srcByteBuf, final ByteBuf byteBuf) throws ShortBufferException
    {
        final int readableBytes = srcByteBuf.readableBytes();
        final byte[] bytes = this.getBytes(srcByteBuf);

        final int outputSize = this.cipher.getOutputSize(readableBytes);
        if (this.c.length < outputSize)
        {
            this.c = new byte[outputSize];
        }
        byteBuf.writeBytes(this.c, 0, this.cipher.update(bytes, 0, readableBytes, this.c));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cipher", this.cipher).toString();
    }
}

