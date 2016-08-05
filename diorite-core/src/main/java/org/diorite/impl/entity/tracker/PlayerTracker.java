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

package org.diorite.impl.entity.tracker;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityTeleport;
import org.diorite.impl.entity.IPlayer;

public class PlayerTracker extends BaseTracker<IPlayer>
{
    public PlayerTracker(final IPlayer entity)
    {
        super(entity);
    }

    @Override
    public void tick(final int tps, final Iterable<IPlayer> players)
    {
        if (this.forceLocationUpdate) // update client position if force update = true
        {
            this.tracker.getNetworkManager().sendPacket(new PacketPlayClientboundEntityTeleport(this.tracker));
        }
        super.tick(tps, players);
        //if (this.isMoving)
        //{
        //    this.tracker.getWorld().getEntityTrackers().updatePlayer(this.tracker);
        //}
    }

    @Override
    public void sendToAllExceptOwn(final PacketPlayClientbound packet)
    {
        this.tracked.stream().filter(p -> p != this.tracker).forEach(p -> p.getNetworkManager().sendPacket(packet));
    }

    @Override
    public void sendToAllExceptOwn(final PacketPlayClientbound[] packet)
    {
        this.tracked.stream().filter(p -> p != this.tracker).forEach(p -> p.getNetworkManager().sendPackets(packet));
    }
}
