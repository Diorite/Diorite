/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;

@PacketClass(id = 0x2B, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 5)
public class PacketPlayServerGameStateChange extends PacketPlayServer
{
    private int   reason; // 1 byte
    private float value; // 4 bytes

    public PacketPlayServerGameStateChange()
    {
    }

    public PacketPlayServerGameStateChange(final int reason)
    {
        this.reason = reason;
    }

    public PacketPlayServerGameStateChange(final int reason, final float value)
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
    public void writeFields(final PacketDataSerializer data) throws IOException
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
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public static final class ReasonCodes
    {
        /**
         * just show message about bed.
         */
        public static final byte INVALID_BED      = 0x00;
        /**
         * Fast rain stop.
         */
        public static final byte END_RAINING      = 0x01;
        /**
         * Fast rain start.
         */
        public static final byte BEGIN_RAINING    = 0x02;
        /**
         * Use gamemode as value.
         */
        public static final byte CHANGE_GAME_MODE = 0x03;
        /**
         * Game end credits
         */
        public static final byte ENTER_CREDITS    = 0x04;
        /**
         * values:
         * 0 - show popup about demo
         * 101 - movement controls. (chat message)
         * 102 - jump controls. (chat message)
         * 103 - inv controls. (chat message)
         */
        public static final byte DEMO_MESSAGE     = 0x05;
        /**
         * That "ding" sound.
         */
        public static final byte ARROW_HIT        = 0x06;
        /**
         * Used to make sky brighter
         * Weird sky, rain without rain etc...
         */
        public static final byte FADE_VALUE       = 0x07;
        /**
         * some fade speed, or, whatever.
         */
        public static final byte FADE_TIME        = 0x08;
        /**
         * It shows elder guardian ghost.
         */
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
