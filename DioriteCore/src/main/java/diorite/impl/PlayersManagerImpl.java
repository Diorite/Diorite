package diorite.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import diorite.impl.entity.PlayerImpl;

public class PlayersManagerImpl
{
    public static final int                   KEEP_ALIVE_TIMER = 15;
    private final       Map<UUID, PlayerImpl> players          = new ConcurrentHashMap<>(100, 0.2f, 8);
    private final ServerImpl server;

    private transient long lastKeepAlive = System.currentTimeMillis();

    public PlayersManagerImpl(final ServerImpl server)
    {
        this.server = server;
    }

    public void playerJoin(final GameProfile gameProfile, final NetworkManager networkManager)
    {
        final PlayerImpl player = new PlayerImpl(this.server, this.server.entityManager.getNextID(), gameProfile, networkManager);
        this.players.put(gameProfile.getId(), player);
    }

    public void playerQuit(final PlayerImpl player)
    {
        this.playerQuit(player.getUniqueID());
    }

    public void playerQuit(final UUID uuid)
    {
        this.players.remove(uuid);
    }

    public void keepAlive()
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastKeepAlive) > TimeUnit.SECONDS.toMillis(KEEP_ALIVE_TIMER))
        {
            this.players.values().parallelStream().forEach(p -> p.getNetworkManager().handle(new PacketPlayOutKeepAlive(p.getId())));
            this.lastKeepAlive = curr;
        }
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("players", this.players).append("server", this.server).toString();
    }
}
