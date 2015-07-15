package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.DisplayedSkinParts;
import org.diorite.chat.ChatVisibility;

@PacketClass(id = 0x15, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInSettings extends PacketPlayIn
{
    private String             locale;
    private byte               viewDistance;
    private ChatVisibility     chatVisibility;
    private boolean            colorsEnabled;
    private DisplayedSkinParts displayedSkinParts;

    public PacketPlayInSettings()
    {
    }

    public PacketPlayInSettings(final String locale, final byte viewDistance, final ChatVisibility chatVisibility, final boolean colorsEnabled, final DisplayedSkinParts displayedSkinParts)
    {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.colorsEnabled = colorsEnabled;
        this.displayedSkinParts = displayedSkinParts;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.locale = data.readText(7);
        this.viewDistance = data.readByte();
        this.chatVisibility = ChatVisibility.getByEnumOrdinal(data.readByte());
        this.colorsEnabled = data.readBoolean();
        this.displayedSkinParts = DisplayedSkinParts.fromByteFlag(data.readUnsignedByte());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.locale);
        data.writeByte(this.viewDistance);
        data.writeByte(this.chatVisibility.ordinal());
        data.writeBoolean(this.colorsEnabled);
        data.writeByte(this.displayedSkinParts.toByteFlag());
    }

    @Override
    public void handle(final PacketPlayInListener listener)
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("locale", this.locale).append("viewDistance", this.viewDistance).append("chatVisibility", this.chatVisibility).append("colorsEnabled", this.colorsEnabled).append("displayedSkinParts", this.displayedSkinParts).toString();
    }
}
