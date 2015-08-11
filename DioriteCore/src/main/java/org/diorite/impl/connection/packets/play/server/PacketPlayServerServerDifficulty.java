package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.Difficulty;

@PacketClass(id = 0x41, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayServerServerDifficulty extends PacketPlayServer
{
    private Difficulty difficulty;

    public PacketPlayServerServerDifficulty()
    {
    }

    public PacketPlayServerServerDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.difficulty = Difficulty.getByLevel(data.readUnsignedByte());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.difficulty.getLevel());
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    public void setDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("difficulty", this.difficulty).toString();
    }
}
