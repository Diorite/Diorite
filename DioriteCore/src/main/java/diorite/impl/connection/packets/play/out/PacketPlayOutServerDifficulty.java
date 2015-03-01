package diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.Difficulty;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x41, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutServerDifficulty implements PacketPlayOut
{
    private Difficulty difficulty;

    public PacketPlayOutServerDifficulty()
    {
    }

    public PacketPlayOutServerDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.difficulty = Difficulty.getByLevel(data.readUnsignedByte());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.difficulty.getLevel());
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
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
