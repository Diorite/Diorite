package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x2B, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutGameStateChange implements PacketPlayOut
{
    private int   reason;
    private float value;

    public PacketPlayOutGameStateChange()
    {
    }

    public PacketPlayOutGameStateChange(final int reason)
    {
        this.reason = reason;
    }

    public PacketPlayOutGameStateChange(final int reason, final float value)
    {
        this.reason = reason;
        this.value = value;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.reason = data.readUnsignedByte();
        this.value = data.readFloat();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.reason);
        data.writeFloat(this.value);
    }

    public int getReason()
    {
        return this.reason;
    }

    public void setReason(final int reason)
    {
        this.reason = reason;
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(final float value)
    {
        this.value = value;
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public static final class ReasonCodes
    {
        public static final byte INVALID_BED      = 0x00;
        public static final byte END_RAINING      = 0x01;
        public static final byte BEGIN_RAINING    = 0x02;
        public static final byte CHANGE_GAME_MODE = 0x03;
        public static final byte ENTER_CREDITS    = 0x04;
        public static final byte DEMO_MESSAGE     = 0x05;
        public static final byte ARROW_HIT        = 0x06;
        public static final byte FADE_VALUE       = 0x07;
        public static final byte FADE_TIME        = 0x08;
        public static final byte MOB_APPEARANCE   = 0x0A;

        private ReasonCodes()
        {
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("reason", this.reason).append("value", this.value).toString();
    }
}
