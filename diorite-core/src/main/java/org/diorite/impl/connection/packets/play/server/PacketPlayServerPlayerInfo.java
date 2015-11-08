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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.Diorite;
import org.diorite.GameMode;
import org.diorite.auth.Property;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;

@PacketClass(id = 0x38, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 512)
public class PacketPlayServerPlayerInfo extends PacketPlayServer
{
    private PlayerInfoAction           action; // ~5 bytes
    private Collection<PlayerInfoData> players; // ~depending on action.

    public PacketPlayServerPlayerInfo()
    {
    }

    public PacketPlayServerPlayerInfo(final Collection<PlayerInfoData> players, final PlayerInfoAction action)
    {
        this.action = action;
        this.players = new ArrayList<>(players);
    }

    public PacketPlayServerPlayerInfo(final PlayerInfoAction action, final PlayerInfoData... players)
    {
        this.action = action;
        this.players = Arrays.asList(players);
    }

    public PacketPlayServerPlayerInfo(final PlayerInfoAction action, final Collection<GameProfileImpl> players)
    {
        this.action = action;
        this.players = new ArrayList<>(players.size());
        for (final GameProfileImpl gp : players)
        {
            final Player p = Diorite.getCore().getPlayer(gp.getId());
            this.players.add(new PlayerInfoData(gp, p.getPing(), p.getGameMode(), TextComponent.fromLegacyText(p.getName())));
        }
    }

    public PacketPlayServerPlayerInfo(final PlayerInfoAction action, final GameProfileImpl... players)
    {
        this.action = action;
        this.players = new ArrayList<>(players.length);
        for (final GameProfileImpl gp : players)
        {
            final Player p = Diorite.getCore().getPlayer(gp.getId());
            this.players.add(new PlayerInfoData(gp, p.getPing(), p.getGameMode(), TextComponent.fromLegacyText(p.getName())));
        }
    }

    public PacketPlayServerPlayerInfo(final PlayerInfoAction action, final PlayerImpl... players)
    {
        this.action = action;
        this.players = new ArrayList<>(players.length);
        for (final PlayerImpl p : players)
        {
            this.players.add(new PlayerInfoData(p.getGameProfile(), p.getPing(), p.getGameMode(), TextComponent.fromLegacyText(p.getName())));
        }
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        // TODO
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.action.getActionId()); // VarInt with action id
        data.writeVarInt(this.players.size());
        for (final PlayerInfoData pid : this.players)
        {
            data.writeUUID(pid.getGameProfile().getId()); // we always sent UUID, other data depend on action
            switch (this.action)
            {
                case ADD_PLAYER:
                    data.writeText(pid.getGameProfile().getName());
                    data.writeVarInt(pid.getGameProfile().getProperties().size());
                    for (final Property p : pid.getGameProfile().getProperties().values())
                    {
                        data.writeText(p.getName());
                        data.writeText(p.getValue());
                        if (p.hasSignature())
                        {
                            data.writeBoolean(true);
                            data.writeText(p.getSignature());
                        }
                        else
                        {
                            data.writeBoolean(false);
                        }
                    }
                    data.writeVarInt(pid.getGameMode().ordinal());
                    data.writeVarInt(pid.getLatency());
                    if (pid.getDisplayName() == null)
                    {
                        data.writeBoolean(false);
                    }
                    else
                    {
                        data.writeBoolean(true);
                        data.writeBaseComponent(pid.getDisplayName());
                    }
                    break;
                case UPDATE_LATENCY:
                    data.writeVarInt(pid.getLatency());
                    break;
                case UPDATE_GAMEMODE:
                    data.writeVarInt(pid.getGameMode().ordinal());
                    break;
                case UPDATE_DISPLAY_NAME:
                    if (pid.getDisplayName() == null)
                    {
                        data.writeBoolean(false);
                    }
                    else
                    {
                        data.writeBoolean(true);
                        data.writeBaseComponent(pid.getDisplayName());
                    }
                    break;
                case REMOVE_PLAYER:
                    // No fields
                    break;
            }
        }
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public PlayerInfoAction getAction()
    {
        return this.action;
    }

    public void setAction(final PlayerInfoAction action)
    {
        this.action = action;
    }

    public Collection<PlayerInfoData> getPlayers()
    {
        return this.players;
    }

    public void setPlayers(final Collection<PlayerInfoData> players)
    {
        this.players = players;
    }

    public static class PlayerInfoData
    {
        private final GameProfileImpl gameProfile;
        private final int             latency;
        private final GameMode        gameMode;
        private final BaseComponent   displayName;

        public PlayerInfoData(final GameProfileImpl gameProfile, final int latency, final GameMode gameMode, final BaseComponent displayName)
        {
            this.latency = latency;
            this.gameProfile = gameProfile;
            this.gameMode = gameMode;
            this.displayName = displayName;
        }

        public PlayerInfoData(final UUID uuid, final BaseComponent displayName)
        {
            this(new GameProfileImpl(uuid, null), 0, null, displayName);
        }

        public PlayerInfoData(final UUID uuid, final GameMode gameMode)
        {
            this(new GameProfileImpl(uuid, null), 0, gameMode, null);
        }

        public PlayerInfoData(final UUID uuid)
        {
            this(new GameProfileImpl(uuid, null), 0, null, null);
        }

        public PlayerInfoData(final UUID uuid, final int latency)
        {
            this(new GameProfileImpl(uuid, null), latency, null, null);
        }

        public GameProfileImpl getGameProfile()
        {
            return this.gameProfile;
        }

        public int getLatency()
        {
            return this.latency;
        }

        public GameMode getGameMode()
        {
            return this.gameMode;
        }

        public BaseComponent getDisplayName()
        {
            return this.displayName;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("latency", this.latency).append("gameProfile", this.gameProfile).append("gameMode", this.gameMode).append("displayName", this.displayName).toString();
        }
    }

    public enum PlayerInfoAction
    {
        ADD_PLAYER(0),
        REMOVE_PLAYER(4),
        UPDATE_GAMEMODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3);

        private final int actionId;

        PlayerInfoAction(final int actionId)
        {
            this.actionId = actionId;
        }

        public int getActionId()
        {
            return this.actionId;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("players", this.players).toString();
    }
}
