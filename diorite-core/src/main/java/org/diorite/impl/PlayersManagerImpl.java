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

package org.diorite.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundKeepAlive;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerInfo;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.world.WorldImpl;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Player;
import org.diorite.event.EventType;
import org.diorite.event.player.PlayerJoinEvent;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;

public class PlayersManagerImpl implements Tickable
{
    private final Map<UUID, IPlayer> players = new ConcurrentHashMap<>(100, 0.2f, 8);
    private final DioriteCore core;
    private final int         keepAliveTimer;

    private transient long lastKeepAlive = System.currentTimeMillis();

    public PlayersManagerImpl(final DioriteCore core)
    {
        this.core = core;
        this.keepAliveTimer = (int) TimeUnit.SECONDS.toMillis(this.core.getKeepAliveTimer());
    }

    public IPlayer createPlayer(final GameProfileImpl gameProfile, final CoreNetworkManager networkManager)
    {// TODO: loading player
        //noinspection MagicNumber

        WorldImpl defaultWorld = this.core.getWorldsManager().getDefaultWorld();

        ImmutableLocation destLocation = defaultWorld.getSpawn();
        GameMode destGameMode = GameMode.SURVIVAL;

        File playerDataFile = new File("players" + File.separator + gameProfile.getId().toString() + ".dat");

        if(!playerDataFile.exists() || !playerDataFile.isFile())
        {
            try
            {
                playerDataFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try(final NbtOutputStream nbtStream = new NbtOutputStream(new FileOutputStream(playerDataFile)))
            {
                final NbtTagCompound nbt = new NbtTagCompound();
                nbt.setString("WorldGroup", this.core.getWorldsManager().getDefaultWorld().getWorldGroup().getName());
                nbt.setString("World", this.core.getWorldsManager().getDefaultWorld().getName());

                nbtStream.write(nbt);
                nbtStream.flush();
                nbtStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(playerDataFile.exists() || playerDataFile.isFile())
        {
            try(final NbtInputStream nbtStream = new NbtInputStream(new FileInputStream(playerDataFile)))
            {
                NbtTagCompound nbt = (NbtTagCompound) nbtStream.readTag(NbtLimiter.getUnlimited());

                final String groupName = nbt.getString("WorldGroup");
                final String worldName = nbt.getString("World");

                WorldImpl destWorld = this.core.getWorldsManager().getWorld(worldName);

                if(destWorld != null)
                {
                    File worldPlayerDataFile = new File("worlds" + File.separator + groupName + File.separator + "_PlayerData_" + File.separator + worldName + "_" + gameProfile.getId().toString() + ".dat");

                    try(final NbtInputStream nbtStream2 = new NbtInputStream(new FileInputStream(worldPlayerDataFile)))
                    {
                        NbtTagCompound nbt2 = (NbtTagCompound) nbtStream2.readTag(NbtLimiter.getUnlimited());

                        destLocation = new ImmutableLocation(nbt2.getDouble("PosX"), nbt2.getDouble("PosY"), nbt2.getDouble("PosZ"), nbt2.getFloat("Yaw"), nbt2.getFloat("Pitch"), destWorld);
                        destGameMode = GameMode.getByEnumName(nbt2.getString("GameMode"));
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        IPlayer player = this.core.getServerManager().getEntityFactory().createPlayer(gameProfile, networkManager, destLocation);
        player.setGameMode(destGameMode);

        return player;
    }

    public void playerJoin(final IPlayer player)
    {
        this.players.put(player.getUniqueID(), player);
        EventType.callEvent(new PlayerJoinEvent(player));
    }

    public List<String> getOnlinePlayersNames()
    {
        return this.players.values().parallelStream().map(IPlayer::getName).collect(Collectors.toList());
    }

    public List<String> getOnlinePlayersNames(final String prefix)
    {
        final String lcPrefix = prefix.toLowerCase();
        return this.players.values().parallelStream().map(IPlayer::getName).filter(s -> s.toLowerCase().startsWith(lcPrefix)).sorted().collect(Collectors.toList());
    }

    public Map<UUID, IPlayer> getRawPlayers()
    {
        return this.players;
    }

    public void playerQuit(final IPlayer player)
    {
        this.forEach(new PacketPlayClientboundPlayerInfo(PacketPlayClientboundPlayerInfo.PlayerInfoAction.REMOVE_PLAYER, player));
        this.players.remove(player.getUniqueID());
        player.onLogout();
    }

//    public void playerQuit(final UUID uuid)
//    {
//        final IPlayer player = this.players.remove(uuid);
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
            this.players.values().parallelStream().forEach(p -> p.getNetworkManager().sendPacket(new PacketPlayClientboundKeepAlive(p.getId())));
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

    public Collection<IPlayer> getOnlinePlayers(final Predicate<IPlayer> predicate)
    {
        return this.players.values().stream().filter(predicate).collect(Collectors.toSet());
    }

    public void forEach(final Predicate<IPlayer> predicate, final Packet<?> packet)
    {
        this.forEach(predicate, player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEachExcept(final Player except, final Predicate<IPlayer> predicate, final Packet<?> packet)
    {
        //noinspection ObjectEquality
        this.forEach(p -> (p != except) && predicate.test(p), player -> player.getNetworkManager().sendPacket(packet));
    }

    public void forEach(final Predicate<IPlayer> predicate, final Packet<?>[] packets)
    {
        this.forEach(predicate, player -> player.getNetworkManager().sendPackets(packets));
    }

    public void forEachExcept(final Player except, final Predicate<IPlayer> predicate, final Packet<?>[] packets)
    {
        //noinspection ObjectEquality
        this.forEach(p -> (p != except) && predicate.test(p), player -> player.getNetworkManager().sendPackets(packets));
    }

    public void forEachExcept(final Player except, final Consumer<IPlayer> consumer)
    {
        //noinspection ObjectEquality
        this.players.values().stream().filter(p -> p != except).forEach(consumer);
    }

    public void forEach(final Consumer<IPlayer> consumer)
    {
        this.players.values().stream().forEach(consumer);
    }

    public void forEachExcept(final Player except, final Predicate<IPlayer> predicate, final Consumer<IPlayer> consumer)
    {
        //noinspection ObjectEquality
        this.players.values().stream().filter(p -> (p != except) && predicate.test(p)).forEach(consumer);
    }

    public void forEach(final Predicate<IPlayer> predicate, final Consumer<IPlayer> consumer)
    {
        this.players.values().stream().filter(predicate).forEach(consumer);
    }


    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("players", this.players).append("server", this.core).toString();
    }
}
