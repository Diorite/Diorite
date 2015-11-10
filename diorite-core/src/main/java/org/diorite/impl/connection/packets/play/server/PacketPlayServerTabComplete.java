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

package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;

@PacketClass(id = 0x3A, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayServerTabComplete extends PacketPlayServer
{
    private Collection<String> strings;

    public PacketPlayServerTabComplete()
    {
    }

    public PacketPlayServerTabComplete(final Collection<String> strings)
    {
        this.strings = strings;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        final int size = data.readVarInt();
        if (size == 0)
        {
            return;
        }
        if (this.strings == null)
        {
            this.strings = new ArrayList<>(size);
        }
        for (int i = 0; i < size; i++)
        {
            this.strings.add(data.readText(Short.MAX_VALUE));
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        if ((this.strings == null) || this.strings.isEmpty())
        {
            data.writeVarInt(0);
            return;
        }
        data.writeVarInt(this.strings.size());
        this.strings.stream().forEach(data::writeText);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public Collection<String> getStrings()
    {
        return this.strings;
    }

    public void setStrings(final Collection<String> strings)
    {
        this.strings = strings;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("strings", this.strings).toString();
    }
}
