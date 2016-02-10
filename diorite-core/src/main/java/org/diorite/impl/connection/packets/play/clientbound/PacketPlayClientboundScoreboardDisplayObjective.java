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

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.scoreboard.Scoreboard;
import org.diorite.scoreboard.ScoreboardPosition;

@PacketClass(id = 0x38, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 180)
public class PacketPlayClientboundScoreboardDisplayObjective extends PacketPlayClientbound
{
    private ScoreboardPosition position;
    private String             name;

    public PacketPlayClientboundScoreboardDisplayObjective(final String scoreboardName, final ScoreboardPosition position)
    {
        this.position = position;
        this.name = scoreboardName;
    }

    public PacketPlayClientboundScoreboardDisplayObjective(final Scoreboard scoreboard)
    {
        this.position = scoreboard.getPosition();
        this.name = scoreboard.getName();
    }

    public ScoreboardPosition getPosition()
    {
        return position;
    }

    public void setPosition(final ScoreboardPosition position)
    {
        this.position = position;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.position = ScoreboardPosition.getByEnumOrdinal(data.readByte());
        this.name = data.readText(16);
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.position.ordinal());
        data.writeText(this.name);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }
}
