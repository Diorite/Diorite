package org.diorite.impl.connection.packets.play.clientbound;

import static org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundLogin.MAX_WORLD_NAME_SIZE;


import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;

@PacketClass(id = 0x33, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 38)
public class PacketPlayClientboundRespawn extends PacketPlayClientbound
{
    private Dimension  dimension;  //  1 byte
    private Difficulty difficulty; //  1 byte
    private GameMode   gameMode;   //  1/2 byte
    private WorldType  worldType;  // ~34 bytes

    public PacketPlayClientboundRespawn()
    {
    }

    public PacketPlayClientboundRespawn(final Dimension dimension, final Difficulty difficulty, final GameMode gameMode, final WorldType worldType)
    {
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.worldType = worldType;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.dimension = Dimension.getByDimensionId(data.readInt());
        this.difficulty = Difficulty.getByLevel(data.readUnsignedByte());
        this.gameMode = GameMode.getByEnumOrdinal(data.readUnsignedByte());
        this.worldType = WorldType.getType(data.readText(MAX_WORLD_NAME_SIZE));
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.dimension.getDimensionId());
        data.writeByte(this.difficulty.getLevel());
        data.writeByte(this.gameMode.ordinal());
        data.writeText(this.worldType.getName());
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dimension", this.dimension).append("difficulty", this.difficulty).append("gameMode", this.gameMode).append("worldType", this.worldType).toString();
    }
}
