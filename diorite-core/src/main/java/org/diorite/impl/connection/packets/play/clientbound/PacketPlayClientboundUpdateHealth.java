package org.diorite.impl.connection.packets.play.clientbound;


import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x3E, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 13)
public class PacketPlayClientboundUpdateHealth extends PacketPlayClientbound
{
    private float health;         //  4 bytes
    private int   food;           // ~5 bytes
    private float foodSaturation; //  4 bytes

    public PacketPlayClientboundUpdateHealth()
    {
    }

    public PacketPlayClientboundUpdateHealth(final float health, final int food, final float foodSaturation)
    {
        this.health = health;
        this.food = food;
        this.foodSaturation = foodSaturation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.health = data.readFloat();
        this.food = data.readVarInt();
        this.foodSaturation = data.readFloat();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeFloat(this.health);
        data.writeVarInt(this.food);
        data.writeFloat(this.foodSaturation);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("health", this.health).append("food", this.food).append("foodSaturation", this.foodSaturation).toString();
    }
}
