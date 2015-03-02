package diorite.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.GameProfile;

import diorite.BlockLocation;
import diorite.Difficulty;
import diorite.Dimension;
import diorite.GameMode;
import diorite.TeleportData;
import diorite.WorldType;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import diorite.impl.entity.PlayerImpl;
import diorite.impl.map.chunk.ChunkImpl;
import diorite.impl.map.chunk.ChunkManagerImpl;
import io.netty.buffer.Unpooled;

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

    public PlayerImpl createPlayer(final GameProfile gameProfile, final NetworkManager networkManager)
    {
        final PlayerImpl player = new PlayerImpl(this.server, this.server.entityManager.getNextID(), gameProfile, networkManager);
        this.players.put(gameProfile.getId(), player);
        return player;
    }

    public void playerJoin(final PlayerImpl player)
    {

        // TODO: this is only test code
       player.getNetworkManager().handle(new PacketPlayOutLogin(player.getId(), GameMode.SURVIVAL, false, Dimension.OVERWORLD, Difficulty.PEACEFUL, 20, WorldType.FLAT));
       player.getNetworkManager().handle(new PacketPlayOutCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(this.server.getServerModName())));
       player.getNetworkManager().handle(new PacketPlayOutServerDifficulty(Difficulty.EASY));
       player.getNetworkManager().handle(new PacketPlayOutSpawnPosition(new BlockLocation(2, 71, - 2)));
       player.getNetworkManager().handle(new PacketPlayOutAbilities(false, false, false, false, 0.5f, 0.5f));
       player.getNetworkManager().handle(new PacketPlayOutHeldItemSlot(3));
       player.getNetworkManager().handle(new PacketPlayOutPosition(new TeleportData(4, 71, - 4)));
        ChunkManagerImpl mag = new ChunkManagerImpl();
        ArrayList<ChunkImpl> chunks = new ArrayList<>(15);
        for (int x = - 1; x < 1; x++)
        {
            for (int z = - 2; z < 2; z++)
            {
                chunks.add(mag.getChunkAt(x, z));
            }
        }
        player.getNetworkManager().handle(new PacketPlayOutMapChunkBulk(true, chunks.toArray(new ChunkImpl[chunks.size()])));

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
