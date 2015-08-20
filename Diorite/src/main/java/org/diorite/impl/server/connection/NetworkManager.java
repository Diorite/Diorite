package org.diorite.impl.server.connection;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerInfo;
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
            final Player player = ((PlayListener) this.packetListener).getPlayer();
            this.core.getPlayersManager().forEach(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.UPDATE_LATENCY, new PacketPlayServerPlayerInfo.PlayerInfoData(player.getUniqueID(), ping)));
        }
    }

    @Override
    public void handleClosed()
    {
        ((ServerConnection) this.core.getConnectionHandler()).remove(this);
    }
}
