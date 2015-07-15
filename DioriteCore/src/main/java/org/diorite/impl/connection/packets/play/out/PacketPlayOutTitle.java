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

@PacketClass(id = 0x45, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutTitle extends PacketPlayOut
{
    private TitleAction   action;
    private BaseComponent text;
    private int           fadeIn;
    private int           stay;
    private int           fadeOut;

    public PacketPlayOutTitle()
    {
    }

    public PacketPlayOutTitle(final TitleAction action, final BaseComponent text)
    {
        this.action = action;
        this.text = text;
    }

    public PacketPlayOutTitle(final TitleAction action, final int fadeIn, final int stay, final int fadeOut)
    {
        this.action = action;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    public PacketPlayOutTitle(final TitleAction action)
    {
        this.action = action;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = TitleAction.fromId(data.readVarInt());
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
                //No fields
                break;
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.action.getActionId());
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
                //No fields
                break;
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public enum TitleAction
    {
        SET_TITLE(0),
        SET_SUBTITLE(1),
        SET_TIMES(2),
        HIDE(3),
        RESET(4);

        private final int actionId;

        TitleAction(final int actionId)
        {
            this.actionId = actionId;
        }

        public int getActionId()
        {
            return this.actionId;
        }

        public static TitleAction fromId(final int id)
        {
            for (final TitleAction ta : TitleAction.values())
            {
                if (ta.getActionId() == id)
                {
                    return ta;
                }
            }
            return null;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("text", this.text).append("fadeIn", this.fadeIn).append("stay", this.stay).append("fadeOut", this.fadeOut).toString();
    }
}
