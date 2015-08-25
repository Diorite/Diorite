package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.TeleportData;

@PacketClass(id = 0x08, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 33)
public class PacketPlayServerPosition extends PacketPlayServer
{
    private TeleportData teleportData; // 33 bytes

    public PacketPlayServerPosition()
    {
    }

    public PacketPlayServerPosition(final TeleportData teleportData)
    {
        this.teleportData = teleportData;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.teleportData = new TeleportData(data.readDouble(), data.readDouble(), data.readDouble(), data.readFloat(), data.readFloat(), data.readUnsignedByte());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeDouble(this.getX());
        data.writeDouble(this.getY());
        data.writeDouble(this.getZ());
        data.writeFloat(this.getYaw());
        data.writeFloat(this.getPitch());
        data.writeByte(this.getRelativeFlags());
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public TeleportData getTeleportData()
    {
        return this.teleportData;
    }

    public void setTeleportData(final TeleportData teleportData)
    {
        this.teleportData = teleportData;
    }

    public double getX()
    {
        return this.teleportData.getX();
    }

    public void setX(final double x)
    {
        this.teleportData.setX(x);
    }

    public double getZ()
    {
        return this.teleportData.getZ();
    }

    public void setZ(final double z)
    {
        this.teleportData.setZ(z);
    }

    public float getYaw()
    {
        return this.teleportData.getYaw();
    }

    public void setYaw(final float yaw)
    {
        this.teleportData.setYaw(yaw);
    }

    public float getPitch()
    {
        return this.teleportData.getPitch();
    }

    public void setPitch(final float pitch)
    {
        this.teleportData.setPitch(pitch);
    }

    public byte getRelativeFlags()
    {
        return this.teleportData.getRelativeFlags();
    }

    public void setRelativeFlags(final int flags)
    {
        this.teleportData.setRelativeFlags(flags);
    }

    public double getY()
    {
        return this.teleportData.getY();
    }

    public void setY(final double y)
    {
        this.teleportData.setY(y);
    }

    public boolean isZRelatvie()
    {
        return this.teleportData.isZRelatvie();
    }

    public void setZRelatvie(final boolean isZRelatvie)
    {
        this.teleportData.setZRelatvie(isZRelatvie);
    }

    public boolean isXRelatvie()
    {
        return this.teleportData.isXRelatvie();
    }

    public void setXRelatvie(final boolean isXRelatvie)
    {
        this.teleportData.setXRelatvie(isXRelatvie);
    }

    public boolean isYawRelatvie()
    {
        return this.teleportData.isYawRelatvie();
    }

    public void setYawRelatvie(final boolean isYawRelatvie)
    {
        this.teleportData.setYawRelatvie(isYawRelatvie);
    }

    public boolean isYRelatvie()
    {
        return this.teleportData.isYRelatvie();
    }

    public void setYRelatvie(final boolean isYRelatvie)
    {
        this.teleportData.setYRelatvie(isYRelatvie);
    }

    public boolean isPitchRelatvie()
    {
        return this.teleportData.isPitchRelatvie();
    }

    public void setPitchRelatvie(final boolean isPitchRelatvie)
    {
        this.teleportData.setPitchRelatvie(isPitchRelatvie);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("teleportData", this.teleportData).toString();
    }
}
