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

package org.diorite.impl.server.connection;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.listeners.PacketPlayListener;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerInfo;
import org.diorite.impl.server.connection.listeners.PlayListener;
import org.diorite.entity.Player;

public class NetworkManager extends CoreNetworkManager
{
    public NetworkManager(final DioriteCore core)
    {
        super(core);
    }

    @Override
    public void setPing(final int ping)
    {
        super.setPing(ping);
        if (this.packetListener instanceof PlayListener)
        {
            final Player player = ((PacketPlayListener) this.packetListener).getPlayer();
            this.core.getPlayersManager().forEach(new PacketPlayClientboundPlayerInfo(PacketPlayClientboundPlayerInfo.PlayerInfoAction.UPDATE_LATENCY, new PacketPlayClientboundPlayerInfo.PlayerInfoData(player.getUniqueID(), ping)));
        }
    }

    @Override
    public void handleClosed()
    {
        ((ServerConnection) this.core.getConnectionHandler()).remove(this);
    }
}
