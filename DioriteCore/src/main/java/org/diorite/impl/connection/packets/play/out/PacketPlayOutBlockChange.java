package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;

@PacketClass(id = 0x23, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutBlockChange implements PacketPlayOut
{
    private BlockLocation location;
    private int           rawID;
    private byte          rawType;

    public PacketPlayOutBlockChange()
    {
    }

    public PacketPlayOutBlockChange(final BlockLocation location, final BlockMaterialData material)
    {
        this.location = location;
        this.rawID = material.getId();
        this.rawType = (byte) material.getType();
    }

    public PacketPlayOutBlockChange(final BlockLocation location, final int rawID, final byte rawType)
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
    public void writePacket(final PacketDataSerializer data) throws IOException
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
        return BlockMaterialData.getByID(this.rawID, this.rawType);
    }

    public void setMaterial(final BlockMaterialData material)
    {
        this.rawID = material.getId();
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
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("rawID", this.rawID).append("rawType", this.rawType).toString();
    }
}
