package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.entity.Entity;

@PacketClass(id = 0x0E, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutSpawnEntity implements PacketPlayOut
{
    private Entity entity;
    private int x; // WARNING! This is 'fixed-point' number
    private int y; // WARNING! This is 'fixed-point' number
    private int z; // WARNING! This is 'fixed-point' number
    private int pitch;
    private int yaw;

    public PacketPlayOutSpawnEntity()
    {
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayOutSpawnEntity(final Entity entity)
    {
        this.entity = entity;
        if(entity.getType().isLiving())
        {
            throw new IllegalArgumentException();
        }
        this.x = (int)entity.getX() << 5; // * 32
        this.y = (int)entity.getY() << 5; // * 32
        this.z = (int)entity.getZ() << 5; // * 32
        this.pitch = (int) ((this.pitch * 256.0F) / 360.0F);
        this.yaw = (int) ((this.yaw * 256.0F) / 360.0F);
        // TODO DATA
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        // TODO
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entity.getId());
        data.writeByte(this.entity.getMcId());
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.pitch);
        data.writeByte(this.yaw);
        data.writeInt(0); // TODO DATA
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entity", this.entity).append("x", this.x).append("y", this.y).append("z", this.z).append("pitch", this.pitch).append("yaw", this.yaw).toString();
    }
}
