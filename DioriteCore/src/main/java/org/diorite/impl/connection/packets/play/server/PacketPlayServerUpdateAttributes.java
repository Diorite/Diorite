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
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeStorage;

@PacketClass(id = 0x20, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 512)
public class PacketPlayServerUpdateAttributes extends PacketPlayServer
{
    private int entityID; // ~5 bytes
    private Collection<AttributeProperty> attributes = new HashSet<>(2);

    public PacketPlayServerUpdateAttributes()
    {
    }

    public PacketPlayServerUpdateAttributes(final int entityID)
    {
        this.entityID = entityID;
    }

    public PacketPlayServerUpdateAttributes(final int entityID, final Collection<AttributeProperty> attributes)
    {
        this.entityID = entityID;
        this.attributes = (attributes == null) ? new HashSet<>(1) : attributes;
    }

    public PacketPlayServerUpdateAttributes(final int entityID, final AttributeStorage attributes)
    {
        this.entityID = entityID;
        this.attributes = new HashSet<>(attributes.getProperties());
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readVarInt();
        final int size = data.readInt();
        if (size == 0)
        {
            return;
        }
        this.attributes = new HashSet<>(size);
        for (int i = 0; i < size; i++)
        {
            this.attributes.add(data.readAttributeProperty());
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityID);
        data.writeInt(this.attributes.size());
        this.attributes.forEach(data::writeAttributeProperty);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    public Collection<AttributeProperty> getAttributes()
    {
        return this.attributes;
    }

    public void setAttributes(final Collection<AttributeProperty> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("attributes", this.attributes).toString();
    }
}
