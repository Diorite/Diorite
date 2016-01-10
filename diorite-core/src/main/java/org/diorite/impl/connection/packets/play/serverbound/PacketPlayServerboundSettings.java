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

package org.diorite.impl.connection.packets.play.serverbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerboundListener;
import org.diorite.DisplayedSkinParts;
import org.diorite.chat.ChatVisibility;
import org.diorite.entity.data.HandType;

@PacketClass(id = 0x04, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 13)
public class PacketPlayServerboundSettings extends PacketPlayServerbound
{
    private String             locale; // ~8 bytes
    private byte               viewDistance; // 1 byte
    private ChatVisibility     chatVisibility; // 1 byte
    private boolean            colorsEnabled; // 1 byte
    private DisplayedSkinParts displayedSkinParts; // 1 byte
    private HandType           handType; // 1 byte

    public PacketPlayServerboundSettings()
    {
    }

    public PacketPlayServerboundSettings(final String locale, final byte viewDistance, final ChatVisibility chatVisibility, final boolean colorsEnabled, final DisplayedSkinParts displayedSkinParts, final HandType handType)
    {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.colorsEnabled = colorsEnabled;
        this.displayedSkinParts = displayedSkinParts;
        this.handType = handType;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.locale = data.readText(7);
        this.viewDistance = data.readByte();
        this.chatVisibility = ChatVisibility.getByEnumOrdinal(data.readByte());
        this.colorsEnabled = data.readBoolean();
        this.displayedSkinParts = DisplayedSkinParts.fromByteFlag(data.readUnsignedByte());
        this.handType = data.readEnum(HandType.class);
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.locale);
        data.writeByte(this.viewDistance);
        data.writeByte(this.chatVisibility.ordinal());
        data.writeBoolean(this.colorsEnabled);
        data.writeByte(this.displayedSkinParts.toByteFlag());
        data.writeEnum(this.handType);
    }

    @Override
    public void handle(final PacketPlayServerboundListener listener)
    {
        listener.handle(this);
    }

    public String getLocale()
    {
        return this.locale;
    }

    public void setLocale(final String locale)
    {
        this.locale = locale;
    }

    public byte getViewDistance()
    {
        return this.viewDistance;
    }

    public void setViewDistance(final byte viewDistance)
    {
        this.viewDistance = viewDistance;
    }

    public ChatVisibility getChatVisibility()
    {
        return this.chatVisibility;
    }

    public void setChatVisibility(final ChatVisibility chatVisibility)
    {
        this.chatVisibility = chatVisibility;
    }

    public boolean isColorsEnabled()
    {
        return this.colorsEnabled;
    }

    public void setColorsEnabled(final boolean colorsEnabled)
    {
        this.colorsEnabled = colorsEnabled;
    }

    public DisplayedSkinParts getDisplayedSkinParts()
    {
        return this.displayedSkinParts;
    }

    public void setDisplayedSkinParts(final DisplayedSkinParts displayedSkinParts)
    {
        this.displayedSkinParts = displayedSkinParts;
    }

    public HandType getHandType()
    {
        return this.handType;
    }

    public void setHandType(final HandType handType)
    {
        this.handType = handType;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("locale", this.locale).append("viewDistance", this.viewDistance).append("chatVisibility", this.chatVisibility).append("colorsEnabled", this.colorsEnabled).append("displayedSkinParts", this.displayedSkinParts).toString();
    }
}
