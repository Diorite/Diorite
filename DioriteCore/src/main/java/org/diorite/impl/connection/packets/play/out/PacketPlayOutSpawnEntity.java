package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.EntityObject;

@PacketClass(id = 0x0E, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutSpawnEntity extends PacketPlayOut
{
    private int   entityId;
    private byte  entityTypeId;
    private int   x; // WARNING! This is 'fixed-point' number
    private int   y; // WARNING! This is 'fixed-point' number
    private int   z; // WARNING! This is 'fixed-point' number
    private byte  pitch;
    private byte  yaw;
    private int   objectData;
    private short movX;
    private short movY;
    private short movZ;

    public PacketPlayOutSpawnEntity()
    {
    }

    @SuppressWarnings("MagicNumber")
    public <T extends EntityImpl & EntityObject> PacketPlayOutSpawnEntity(final T entity)
    {
        this.entityId = entity.getId();
        this.entityTypeId = (byte) entity.getMcId();
        if (entity.getType().isLiving())
        {
            throw new IllegalArgumentException();
        }
        this.x = (int) (entity.getX() * 32);
        this.y = (int) (entity.getY() * 32);
        this.z = (int) (entity.getZ() * 32);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);

        this.objectData = entity.getEntityObjectData();

        this.movX = (short) (entity.getVelocityX() * 8000); // IDK why 8000
        this.movY = (short) (entity.getVelocityY() * 8000);
        this.movZ = (short) (entity.getVelocityZ() * 8000);
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.entityTypeId = data.readByte();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.pitch = data.readByte();
        this.yaw = data.readByte();
        this.objectData = data.readInt();
        if (this.objectData > 0)
        {
            this.movX = data.readShort();
            this.movY = data.readShort();
            this.movZ = data.readShort();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
//        System.out.println("write packet...2");
        data.writeVarInt(this.entityId);
        data.writeByte(this.entityTypeId);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.pitch);
        data.writeByte(this.yaw);
        data.writeInt(this.objectData);
        if (this.objectData > 0)
        {
            data.writeShort(this.movX);
            data.writeShort(this.movY);
            data.writeShort(this.movZ);
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public byte getEntityTypeId()
    {
        return this.entityTypeId;
    }

    public void setEntityTypeId(final byte entityTypeId)
    {
        this.entityTypeId = entityTypeId;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(final int z)
    {
        this.z = z;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public int getObjectData()
    {
        return this.objectData;
    }

    public void setObjectData(final int objectData)
    {
        this.objectData = objectData;
    }

    public short getMovX()
    {
        return this.movX;
    }

    public void setMovX(final short movX)
    {
        this.movX = movX;
    }

    public short getMovY()
    {
        return this.movY;
    }

    public void setMovY(final short movY)
    {
        this.movY = movY;
    }

    public short getMovZ()
    {
        return this.movZ;
    }

    public void setMovZ(final short movZ)
    {
        this.movZ = movZ;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("entityTypeId", this.entityTypeId).append("x", this.x).append("y", this.y).append("z", this.z).append("pitch", this.pitch).append("yaw", this.yaw).append("objectData", this.objectData).append("movX", this.movX).append("movY", this.movY).append("movZ", this.movZ).toString();
    }
}
