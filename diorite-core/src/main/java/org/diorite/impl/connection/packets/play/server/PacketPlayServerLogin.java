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
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;

@PacketClass(id = 0x01, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 45)
public class PacketPlayServerLogin extends PacketPlayServer
{
    public static final int MAX_WORLD_NAME_SIZE = 32; // should be 16?

    private int        entityID; // 4 bytes
    private GameMode   gameMode; // 1/2 byte
    private boolean    hardcore; // 1/2 byte
    private Dimension  dimension; // 1 byte
    private Difficulty difficulty; // 1 byte
    private int        maxPlayers; // 1 byte
    private WorldType  worldType; // ~34 bytes
    private boolean    debug; // 1 byte

    public PacketPlayServerLogin()
    {
    }

    public PacketPlayServerLogin(final int entityID, final GameMode gameMode, final boolean hardcore, final Dimension dimension, final Difficulty difficulty, final int maxPlayers, final WorldType worldType)
    {
        this.entityID = entityID;
        this.gameMode = gameMode;
        this.hardcore = hardcore;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.worldType = worldType;
    }

    public PacketPlayServerLogin(final int entityID, final GameMode gameMode, final boolean hardcore, final Dimension dimension, final Difficulty difficulty, final int maxPlayers, final WorldType worldType, final boolean debug)
    {
        this.entityID = entityID;
        this.gameMode = gameMode;
        this.hardcore = hardcore;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.worldType = worldType;
        this.debug = debug;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readInt();
        final int gmFlags = data.readUnsignedByte();
        this.hardcore = (gmFlags & 8) == 8;
        this.gameMode = GameMode.getByEnumOrdinal(gmFlags & - 9);
        this.dimension = Dimension.getByDimensionId(data.readByte());
        this.difficulty = Difficulty.getByLevel(data.readUnsignedByte());
        this.maxPlayers = data.readUnsignedByte();
        this.worldType = WorldType.getType(data.readText(MAX_WORLD_NAME_SIZE));
        this.debug = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.entityID);
        int gmFlags = this.gameMode.ordinal();
        if (this.hardcore)
        {
            gmFlags |= 8;
        }
        data.writeByte(gmFlags);
        data.writeByte(this.dimension.getDimensionId());
        data.writeByte(this.difficulty.getLevel());
        data.writeByte(this.maxPlayers);
        data.writeText(this.worldType.getName());
        data.writeBoolean(this.debug);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public boolean isDebug()
    {
        return this.debug;
    }

    public void setDebug(final boolean debug)
    {
        this.debug = debug;
    }

    public WorldType getWorldType()
    {
        return this.worldType;
    }

    public void setWorldType(final WorldType worldType)
    {
        this.worldType = worldType;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    public void setDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    public Dimension getDimension()
    {
        return this.dimension;
    }

    public void setDimension(final Dimension dimension)
    {
        this.dimension = dimension;
    }

    public GameMode getGameMode()
    {
        return this.gameMode;
    }

    public void setGameMode(final GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public boolean isHardcore()
    {
        return this.hardcore;
    }

    public void setHardcore(final boolean hardcore)
    {
        this.hardcore = hardcore;
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("gameMode", this.gameMode).append("hardcore", this.hardcore).append("dimension", this.dimension).append("difficulty", this.difficulty).append("maxPlayers", this.maxPlayers).append("worldType", this.worldType).append("debug", this.debug).toString();
    }
}
