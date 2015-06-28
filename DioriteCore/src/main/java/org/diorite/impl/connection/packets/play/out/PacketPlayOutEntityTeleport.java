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

@PacketClass(id = 0x18, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutEntityTeleport implements PacketPlayOut
{
    private int     entityId;
    private int     x; // WARNING! This is 'fixed-point' number
    private int     y; // WARNING! This is 'fixed-point' number
    private int     z; // WARNING! This is 'fixed-point' number
    private byte    yaw;
    private byte    pitch;
    private boolean onGround;

    public PacketPlayOutEntityTeleport()
    {
    }

    public PacketPlayOutEntityTeleport(final int entityId, final int x, final int y, final int z, final byte yaw, final byte pitch, final boolean onGround)
    {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayOutEntityTeleport(final EntityImpl entity)
    {
        this.entityId = entity.getId();
        this.x = (int) (entity.getX() * 32);
        this.y = (int) (entity.getY() * 32);
        this.z = (int) (entity.getZ() * 32);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.onGround = entity.isOnGround();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeBoolean(this.onGround);
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

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public void setOnGround(final boolean onGround)
    {
        this.onGround = onGround;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("x", this.x).append("y", this.y).append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch).append("onGround", this.onGround).toString();
    }
}
