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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;

@PacketClass(id = 0x23, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 13)
public class PacketPlayServerBlockChange extends PacketPlayServer
{
    private BlockLocation location; // 8 bytes
    private int           rawID; // ~5 bytes with rawType.
    private byte          rawType; // ~5 bytes with rawID.

    public PacketPlayServerBlockChange()
    {
    }

    public PacketPlayServerBlockChange(final BlockLocation location, final BlockMaterialData material)
    {
        this.location = location;
        this.rawID = material.ordinal();
        this.rawType = (byte) material.getType();
    }

    public PacketPlayServerBlockChange(final BlockLocation location, final int rawID, final byte rawType)
    {
        this.location = location;
        this.rawID = rawID;
        this.rawType = rawType;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();
        final int blockData = data.readVarInt();
        this.rawID = (blockData >> 4);
        this.rawType = (byte) (blockData & 15);
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
        data.writeVarInt(((this.rawID << 4) | this.rawType));
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public void setLocation(final BlockLocation location)
    {
        this.location = location;
    }

    public BlockMaterialData getMaterial()
    {
        final Material mat = Material.getByID(this.rawID, this.rawType);
        return (mat instanceof BlockMaterialData) ? (BlockMaterialData) mat : null;
    }

    public void setMaterial(final BlockMaterialData material)
    {
        this.rawID = material.ordinal();
        this.rawType = (byte) material.getType();
    }

    public int getRawID()
    {
        return this.rawID;
    }

    public void setRawID(final int rawID)
    {
        this.rawID = rawID;
    }

    public byte getRawType()
    {
        return this.rawType;
    }

    public void setRawType(final byte rawType)
    {
        this.rawType = rawType;
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("rawID", this.rawID).append("rawType", this.rawType).toString();
    }
}
