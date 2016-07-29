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
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.BossBar.Color;
import org.diorite.BossBar.Style;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x0C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 20)
public class PacketPlayClientboundBoss extends PacketPlayClientbound
{
    private UUID          uuid;
    private Action        action;
    private BaseComponent title;
    private float         health;
    private Color         color;
    private Style         style;
    private boolean       darkSky;
    private boolean       isDragonBar;

    public PacketPlayClientboundBoss()
    {
    }

    public PacketPlayClientboundBoss(final UUID uuid, final BaseComponent title, final float health, final Color color, final Style style, final boolean darkSky, final boolean isDragonBar)
    {
        this.action = Action.ADD;
        this.uuid = uuid;
        this.title = title;
        this.health = health;
        this.color = color;
        this.style = style;
        this.darkSky = darkSky;
        this.isDragonBar = isDragonBar;
    }

    public PacketPlayClientboundBoss(final UUID uuid)
    {
        this.action = Action.REMOVE;
        this.uuid = uuid;
    }

    public PacketPlayClientboundBoss(final UUID uuid, final float health)
    {
        this.action = Action.UPDATE_HEALTH;
        this.uuid = uuid;
        this.health = health;
    }

    public PacketPlayClientboundBoss(final UUID uuid, final BaseComponent title)
    {
        this.action = Action.UPDATE_TITLE;
        this.uuid = uuid;
        this.title = title;
    }

    public PacketPlayClientboundBoss(final UUID uuid, final Color color, final Style style)
    {
        this.action = Action.UPDATE_STYLE;
        this.uuid = uuid;
        this.color = color;
        this.style = style;
    }

    public PacketPlayClientboundBoss(final UUID uuid, final boolean darkSky, final boolean isDragonBar)
    {
        this.action = Action.UPDATE_FLAGS;
        this.uuid = uuid;
        this.darkSky = darkSky;
        this.isDragonBar = isDragonBar;
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(final UUID uuid)
    {
        this.uuid = uuid;
    }

    public Action getAction()
    {
        return action;
    }

    public void setAction(final Action action)
    {
        this.action = action;
    }

    public BaseComponent getTitle()
    {
        return title;
    }

    public void setTitle(final BaseComponent title)
    {
        this.title = title;
    }

    public float getHealth()
    {
        return health;
    }

    public void setHealth(final float health)
    {
        this.health = health;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(final Color color)
    {
        this.color = color;
    }

    public Style getStyle()
    {
        return style;
    }

    public void setStyle(final Style style)
    {
        this.style = style;
    }

    public boolean isDarkSky()
    {
        return darkSky;
    }

    public void setDarkSky(final boolean darkSky)
    {
        this.darkSky = darkSky;
    }

    public boolean isDragonBar()
    {
        return isDragonBar;
    }

    public void setDragonBar(final boolean dragonBar)
    {
        isDragonBar = dragonBar;
    }

    private byte encodeFlags()
    {
        byte flags = 0;
        if (this.darkSky)
        {
            flags |= 0x01;
        }
        if (this.isDragonBar)
        {
            flags |= 0x02;
        }
        return flags;
    }

    private void decodeFlags(final byte flags)
    {
        this.darkSky = (flags & 0x01) > 0;
        this.isDragonBar = (flags & 0x02) > 0;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.uuid = data.readUUID();
        this.action = data.readEnum(Action.class);
        switch (this.action)
        {
            case ADD:
            {
                this.title = data.readBaseComponent();
                this.health = data.readFloat();
                this.color = data.readEnum(Color.class);
                this.style = data.readEnum(Style.class);
                this.decodeFlags(data.readByte());
                break;
            }

            case UPDATE_HEALTH:
            {
                this.health = data.readFloat();
                break;
            }

            case UPDATE_TITLE:
            {
                this.title = data.readBaseComponent();
                break;
            }

            case UPDATE_STYLE:
            {
                this.color = data.readEnum(Color.class);
                this.style = data.readEnum(Style.class);
                break;
            }

            case UPDATE_FLAGS:
            {
                this.decodeFlags(data.readByte());
                break;
            }
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeUUID(this.uuid);
        data.writeEnum(this.action);
        switch (this.action)
        {
            case ADD:
            {
                data.writeBaseComponent(this.title);
                data.writeFloat(this.health);
                data.writeEnum(this.color);
                data.writeEnum(this.style);
                data.writeByte(this.encodeFlags());
                break;
            }

            case UPDATE_HEALTH:
            {
                data.writeFloat(this.health);
                break;
            }

            case UPDATE_TITLE:
            {
                data.writeBaseComponent(this.title);
                break;
            }

            case UPDATE_STYLE:
            {
                data.writeEnum(this.color);
                data.writeEnum(this.style);
                break;
            }

            case UPDATE_FLAGS:
            {
                data.writeByte(this.encodeFlags());
                break;
            }
        }
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public enum Action
    {
        ADD,
        REMOVE,
        UPDATE_HEALTH,
        UPDATE_TITLE,
        UPDATE_STYLE,
        UPDATE_FLAGS
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).append("action", this.action).append("title", this.title).append("health", this.health).append("color", this.color).append("style", this.style).append("darkSky", this.darkSky).append("isDragonBar", this.isDragonBar).toString();
    }
}
