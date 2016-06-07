/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x45, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 100)
public class PacketPlayClientboundTitle extends PacketPlayClientbound
{
    private TitleAction   action; // ~1 byte
    private BaseComponent text; // ~64 bytes
    private int           fadeIn; // ~5 bytes
    private int           stay; // ~5 bytes
    private int           fadeOut; // ~5 bytes

    public PacketPlayClientboundTitle()
    {
    }

    public PacketPlayClientboundTitle(final TitleAction action, final BaseComponent text)
    {
        this.action = action;
        this.text = text;
    }

    public PacketPlayClientboundTitle(final TitleAction action, final int fadeIn, final int stay, final int fadeOut)
    {
        this.action = action;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public PacketPlayClientboundTitle(final TitleAction action)
    {
        this.action = action;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = data.readEnum(TitleAction.class);
        if (this.action == null)
        {
            return;
        }
        switch (this.action)
        {
            case SET_TITLE:
            case SET_SUBTITLE:
                this.text = data.readBaseComponent();
                break;
            case SET_TIMES:
                this.fadeIn = data.readVarInt();
                this.stay = data.readVarInt();
                this.fadeOut = data.readVarInt();
                break;
            case HIDE:
            case RESET:
                // No fields
                break;
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeEnum(this.action);
        switch (this.action)
        {
            case SET_TITLE:
            case SET_SUBTITLE:
                data.writeBaseComponent(this.text);
                break;
            case SET_TIMES:
                data.writeInt(this.fadeIn);
                data.writeInt(this.stay);
                data.writeInt(this.fadeOut);
                break;
            case HIDE:
            case RESET:
                // No fields
                break;
        }
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public enum TitleAction
    {
        SET_TITLE,
        SET_SUBTITLE,
        SET_TIMES,
        HIDE,
        RESET
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("text", this.text).append("fadeIn", this.fadeIn).append("stay", this.stay).append("fadeOut", this.fadeOut).toString();
    }
}
