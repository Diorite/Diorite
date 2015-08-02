package org.diorite.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCustomPayload;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerHeldItemSlot;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerKeepAlive;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerLogin;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerInfo;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPosition;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerServerDifficulty;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnPosition;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.TeleportData;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;

import io.netty.buffer.Unpooled;

public class PlayersManagerImpl implements Tickable
{
    private final Map<UUID, PlayerImpl> players = new ConcurrentHashMap<>(100, 0.2f, 8);
    private final ServerImpl server;
    private final int        keepAliveTimer;

    private transient long lastKeepAlive = System.currentTimeMillis();

    public PlayersManagerImpl(final ServerImpl server)
    {
        this.server = server;
        this.keepAliveTimer = (int) TimeUnit.SECONDS.toMillis(this.server.getKeepAliveTimer());
    }

    public PlayerImpl createPlayer(final GameProfile gameProfile, final NetworkManager networkManager)
    {// TODO: loading player
        //noinspection MagicNumber

        return new PlayerImpl(this.server, EntityImpl.getNextEntityID(), gameProfile, networkManager, new ImmutableLocation(4, 255, - 4, 0, 0, this.server.getWorldsManager().getDefaultWorld()));
    }

    @SuppressWarnings("MagicNumber")
    public void playerJoin(final PlayerImpl player)
    {
        this.players.put(player.getUniqueID(), player);
        this.forEach(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.ADD_PLAYER, player));
        this.server.getPlayersManager().forEach(p -> player.getNetworkManager().sendPacket(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.ADD_PLAYER, p)));

        // TODO: this is only test code
        player.getNetworkManager().sendPacket(new PacketPlayServerLogin(player.getId(), GameMode.SURVIVAL, false, Dimension.OVERWORLD, Difficulty.PEACEFUL, 20, WorldType.FLAT));
        player.getNetworkManager().sendPacket(new PacketPlayServerCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(this.server.getServerModName())));
        player.getNetworkManager().sendPacket(new PacketPlayServerServerDifficulty(Difficulty.EASY));
        player.getNetworkManager().sendPacket(new PacketPlayServerSpawnPosition(new BlockLocation(2, 255, - 2)));
        player.getNetworkManager().sendPacket(new PacketPlayServerAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED));
        player.getNetworkManager().sendPacket(new PacketPlayServerHeldItemSlot(3));
        player.getNetworkManager().sendPacket(new PacketPlayServerPosition(new TeleportData(4, 255, - 4)));

        // TODO: changeable message, events, etc..
        this.server.broadcastSimpleColoredMessage(ChatPosition.ACTION, "&3&l" + player.getName() + "&7&l joined the server!");
        this.server.broadcastSimpleColoredMessage(ChatPosition.SYSTEM, "&3" + player.getName() + "&7 joined the server!");
//        this.server.sendConsoleSimpleColoredMessage("&3" + player.getName() + " &7join to the server.");

        this.server.updatePlayerListHeaderAndFooter(TextComponent.fromLegacyText("Welcome to Diorite!"), TextComponent.fromLegacyText("http://diorite.org"), player); // TODO Tests, remove it
        player.sendTitle(TextComponent.fromLegacyText("Welcome to Diorite"), TextComponent.fromLegacyText("http://diorite.org"), 20, 100, 20); // TODO Tests, remove it

        player.getWorld().addEntity(player);

        this.server.addSync(() -> {
            PacketPlayServer[] newPackets = player.getSpawnPackets();
            this.server.getPlayersManager().forEachExcept(player, p -> {
                PacketPlayServer[] playerPackets = p.getSpawnPackets();
                player.getNetworkManager().sendPackets(playerPackets);
                p.getNetworkManager().sendPackets(newPackets);
            });
        });
    }

    public List<String> getOnlinePlayersNames()
    {
        return this.players.values().parallelStream().map(PlayerImpl::getName).collect(Collectors.toList());
    }

    public List<String> getOnlinePlayersNames(final String prefix)
    {
        final String lcPrefix = prefix.toLowerCase();
        return this.players.values().parallelStream().map(PlayerImpl::getName).filter(s -> s.toLowerCase().startsWith(lcPrefix)).sorted().collect(Collectors.toList());
    }

    public Map<UUID, PlayerImpl> getRawPlayers()
    {
        return this.players;
    }

    public void playerQuit(final PlayerImpl player)
    {
        this.forEach(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.REMOVE_PLAYER, player));
        this.players.remove(player.getUniqueID());
        player.onLogout();
    }

//    public void playerQuit(final UUID uuid)
//    {
//        final PlayerImpl player = this.players.remove(uuid);
//        if (player != null)
//        {
//            this.playerQuit(player);
//        }
//        else
//        {
//            this.forEach(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.PlayerInfoAction.REMOVE_PLAYER, new GameProfile(uuid, null))); // name isn't needed when removing player
//        }
//    }

    @Override
    public void doTick(final int tps)
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastKeepAlive) > this.keepAliveTimer)
        {
            this.players.values().parallelStream().forEach(p -> p.getNetworkManager().sendPacket(new PacketPlayServerKeepAlive(p.getId())));
            this.lastKeepAlive = curr;
        }
    }

    public void forEach(final Packet<?> packet)
    {
        this.forEach(player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEachExcept(final Player except, final Packet<?> packet)
    {
        //noinspection ObjectEquality
        this.forEach(p -> p != except, player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEach(final Packet<?>[] packets)
    {
        this.forEach(player -> player.getNetworkManager().sendPackets(packets));
    }

    public void forEachExcept(final Player except, final Packet<?>[] packets)
    {
        //noinspection ObjectEquality
        this.forEach(p -> p != except, player -> player.getNetworkManager().sendPackets(packets));
    }

    public Collection<PlayerImpl> getOnlinePlayers(final Predicate<PlayerImpl> predicate)
    {
        return this.players.values().stream().filter(predicate).collect(Collectors.toSet());
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Packet<?> packet)
    {
        this.forEach(predicate, player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEachExcept(final Player except, final Predicate<PlayerImpl> predicate, final Packet<?> packet)
    {
        //noinspection ObjectEquality
        this.forEach(p -> (p != except) && predicate.test(p), player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Packet<?>[] packets)
    {
        this.forEach(predicate, player -> player.getNetworkManager().sendPackets(packets));
    }

    public void forEachExcept(final Player except, final Predicate<PlayerImpl> predicate, final Packet<?>[] packets)
    {
        //noinspection ObjectEquality
        this.forEach(p -> (p != except) && predicate.test(p), player -> player.getNetworkManager().sendPackets(packets));
    }

    public void forEachExcept(final Player except, final Consumer<PlayerImpl> consumer)
    {
        //noinspection ObjectEquality
        this.players.values().stream().filter(p -> p != except).forEach(consumer);
    }

    public void forEach(final Consumer<PlayerImpl> consumer)
    {
        this.players.values().stream().forEach(consumer);
    }

    public void forEachExcept(final Player except, final Predicate<PlayerImpl> predicate, final Consumer<PlayerImpl> consumer)
    {
        //noinspection ObjectEquality
        this.players.values().stream().filter(p -> (p != except) && predicate.test(p)).forEach(consumer);
    }

    public void forEach(final Predicate<PlayerImpl> predicate, final Consumer<PlayerImpl> consumer)
    {
        this.players.values().stream().filter(predicate).forEach(consumer);
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
