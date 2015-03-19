package org.diorite.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.authlib.GameProfile;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutAbilities;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutCustomPayload;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutHeldItemSlot;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutKeepAlive;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutLogin;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutPosition;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutServerDifficulty;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutSpawnPosition;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.world.WorldImpl;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.world.Dimension;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.TeleportData;
import org.diorite.world.WorldType;
import org.diorite.entity.Player;

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
        final PlayerImpl player = new PlayerImpl(this.server, this.server.entityManager.getNextID(), gameProfile, networkManager, new ImmutableLocation(4, 71, - 4, 0, 0, new WorldImpl("world", UUID.randomUUID())));
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
        player.getNetworkManager().handle(new PacketPlayOutAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED));
        player.getNetworkManager().handle(new PacketPlayOutHeldItemSlot(3));
        player.getNetworkManager().handle(new PacketPlayOutPosition(new TeleportData(4, 71, - 4)));
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

    public void forEach(final Packet<?> packet)
    {
        this.forEach(player -> player.getNetworkManager().handle(packet));
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Packet<?> packet)
    {
        this.forEach(predicate, player -> player.getNetworkManager().handle(packet));
    }

    public void forEach(final Consumer<PlayerImpl> consumer)
    {
        this.players.values().parallelStream().forEach(consumer);
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Consumer<PlayerImpl> consumer)
    {
        this.players.values().parallelStream().filter(predicate).forEach(consumer);
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
