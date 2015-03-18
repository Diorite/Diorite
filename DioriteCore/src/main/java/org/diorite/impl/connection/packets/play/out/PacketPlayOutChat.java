package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.ChatPosition;

@PacketClass(id = 0x02, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutChat implements PacketPlayOut
{
    private BaseComponent content;
    private ChatPosition chatPosition = ChatPosition.CHAT;

    public PacketPlayOutChat()
    {
    }

    public PacketPlayOutChat(final BaseComponent content)
    {
        this.content = content;
    }

    public PacketPlayOutChat(final BaseComponent content, final ChatPosition chatPosition)
    {
        this.content = content;
        this.chatPosition = chatPosition;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.content = data.readBaseComponent();
        this.chatPosition = ChatPosition.getByID(data.readByte());
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBaseComponent(this.content);
        data.writeByte(this.chatPosition.getId());
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public ChatPosition getChatPosition()
    {
        return this.chatPosition;
    }

    public void setChatPosition(final ChatPosition chatPosition)
    {
        this.chatPosition = chatPosition;
    }

    public BaseComponent getContent()
    {
        return this.content;
    }

    public void setContent(final BaseComponent content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("chatPosition", this.chatPosition).toString();
    }
}
