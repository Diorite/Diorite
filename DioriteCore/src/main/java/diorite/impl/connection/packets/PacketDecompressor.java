package diorite.impl.connection.packets;

import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

public class PacketDecompressor extends ByteToMessageDecoder
{
    public static final int MAX_PACKET_SIZE = 2097152; // 256 KiB
    private final Inflater inflater;
    private       int      threshold;

    public PacketDecompressor(final int threshold)
    {
        this.threshold = threshold;
        this.inflater = new Inflater();
    }

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> paramList) throws DataFormatException
    {
        if (byteBuf.readableBytes() == 0)
        {
            return;
        }
        final PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(byteBuf);
        final int i = localPacketDataSerializer.readVarInt();
        if (i == 0)
        {
            paramList.add(localPacketDataSerializer.readBytes(localPacketDataSerializer.readableBytes()));
        }
        else
        {
            if (i < this.threshold) {
                throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
            }
            if (i > MAX_PACKET_SIZE) {
                throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + MAX_PACKET_SIZE);
            }
            final byte[] arrayOfByte1 = new byte[localPacketDataSerializer.readableBytes()];
            localPacketDataSerializer.readBytes(arrayOfByte1);
            this.inflater.setInput(arrayOfByte1);

            final byte[] arrayOfByte2 = new byte[i];
            this.inflater.inflate(arrayOfByte2);
            paramList.add(Unpooled.wrappedBuffer(arrayOfByte2));

            this.inflater.reset();
        }
    }

    public void a(final int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("inflater", this.inflater).append("threshold", this.threshold).toString();
    }
}

