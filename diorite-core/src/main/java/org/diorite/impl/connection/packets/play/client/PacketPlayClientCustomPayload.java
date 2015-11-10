/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x17, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayClientCustomPayload extends PacketPlayClient
{
    public static final int TAG_SIZE = 20;
    public static final int MAX_SIZE = 32767;
    private String               tag;
    private PacketDataSerializer dataSerializer;

    public PacketPlayClientCustomPayload()
    {
    }

    public PacketPlayClientCustomPayload(final String tag, final PacketDataSerializer dataSerializer)
    {
        this.tag = tag;
        this.dataSerializer = dataSerializer;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.tag = data.readText(TAG_SIZE);
        final int size = data.readableBytes();
        if ((size < 0) || (size > MAX_SIZE))
        {
            throw new IOException("Payload may not be larger than " + MAX_SIZE + " bytes");
        }
        this.dataSerializer = new PacketDataSerializer(data.readBytes(size));
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.tag);
        data.writeBytes(this.dataSerializer);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public String getTag()
    {
        return this.tag;
    }

    public void setTag(final String tag)
    {
        this.tag = tag;
    }

    public PacketDataSerializer getDataSerializer()
    {
        return this.dataSerializer;
    }

    public void setDataSerializer(final PacketDataSerializer dataSerializer)
    {
        this.dataSerializer = dataSerializer;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tag", this.tag).toString();
    }
}
