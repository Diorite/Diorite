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

package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.BossBarImpl;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfiles;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundAbilities;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundCustomPayload;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityHeadRotation;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundHeldItemSlot;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundLogin;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPlayerInfo;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundPosition;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundSpawnPosition;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundUpdateTime;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldDifficulty;
import org.diorite.impl.entity.IPlayer;
import org.diorite.BlockLocation;
import org.diorite.ImmutableLocation;
import org.diorite.TeleportData;
import org.diorite.cfg.messages.DioriteMessages;
import org.diorite.cfg.messages.Message.MessageData;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.JoinPipeline;
import org.diorite.event.player.PlayerJoinEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

import io.netty.buffer.Unpooled;

public class JoinPipelineImpl extends SimpleEventPipeline<PlayerJoinEvent> implements JoinPipeline
{
    @SuppressWarnings("MagicNumber")
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|StartPackets", ((evt, pipeline) -> {
            // TODO
            final IPlayer player = (IPlayer) evt.getPlayer();
            final CoreNetworkManager networkManager = player.getNetworkManager();

            networkManager.sendPacket(new PacketPlayClientboundLogin(player.getId(), player.getGameMode(), player.getWorld().getHardcore().isEnabled(), player.getWorld().getDimension(), player.getWorld().getDifficulty(), DioriteCore.getInstance().getPlayersManager().getRawPlayers().size(), player.getWorld().getWorldType()));
            networkManager.sendPacket(new PacketPlayClientboundCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(DioriteCore.getInstance().getServerModName())));
            networkManager.sendPacket(new PacketPlayClientboundWorldDifficulty(player.getWorld().getDifficulty()));
            networkManager.sendPacket(new PacketPlayClientboundSpawnPosition(player.getWorld().getSpawn().toBlockLocation()));
            networkManager.sendPacket(new PacketPlayClientboundAbilities(false, false, player.canFly(), false, Player.WALK_SPEED, Player.FLY_SPEED));
            networkManager.sendPacket(new PacketPlayClientboundHeldItemSlot(player.getHeldItemSlot()));
            TeleportData teleportData = new TeleportData(player.getX(), player.getY(), player.getZ());
            teleportData.setYaw(player.getYaw());
            teleportData.setPitch(player.getPitch());
            networkManager.sendPacket(new PacketPlayClientboundPosition(teleportData, 5));
            networkManager.sendPacket(new PacketPlayClientboundUpdateTime(player.getWorld()));
        }));

        this.addAfter("Diorite|StartPackets", "Diorite|EntityStuff", ((evt, pipeline) -> {
            final IPlayer player = (IPlayer) evt.getPlayer();
            player.getWorld().addEntity(player);

            DioriteCore.getInstance().addSync(() -> {
                final PacketPlayClientbound[] newPackets = player.getSpawnPackets();
                DioriteCore.getInstance().getPlayersManager().forEachExcept(player, p -> {
                    final PacketPlayClientbound[] playerPackets = p.getSpawnPackets();
                    player.getNetworkManager().sendPackets(playerPackets);
                    p.getNetworkManager().sendPackets(newPackets);
                });
            });
        }));

        this.addAfter("Diorite|EntityStuff", "Diorite|PlayerListStuff", ((evt, pipeline) -> {
            final IPlayer player = (IPlayer) evt.getPlayer();

            DioriteCore.getInstance().getPlayersManager().forEach(new PacketPlayClientboundPlayerInfo(PacketPlayClientboundPlayerInfo.PlayerInfoAction.ADD_PLAYER, player));
            DioriteCore.getInstance().getPlayersManager().forEach(p -> player.getNetworkManager().sendPacket(new PacketPlayClientboundPlayerInfo(PacketPlayClientboundPlayerInfo.PlayerInfoAction.ADD_PLAYER, p)));
        }));

        this.addAfter("Diorite|PlayerListStuff", "Diorite|WorldBorderUpdate", ((evt, pipeline) -> {
            final IPlayer player = (IPlayer) evt.getPlayer();
            player.sendWorldBorderUpdate();
        }));

        this.addAfter("Diorite|WorldBorderUpdate", "Diorite|BossBarUpdate", ((evt, pipeline) -> {
            final IPlayer player = (IPlayer) evt.getPlayer();
            player.getWorld().getBossBars(true).forEach(bossBar -> ((BossBarImpl) bossBar).addHolder(player));
        }));

        this.addAfter(EventPriority.NORMAL, "Diorite|JoinMessage", ((evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            final Player player = evt.getPlayer();
            GameProfiles.addToCache(player.getGameProfile());
            DioriteMessages.broadcastMessage(DioriteMessages.MSG_PLAYER_JOIN, MessageData.e("player", player));
//            this.core.broadcastSimpleColoredMessage(ChatPosition.ACTION, "&3&l" + player.getName() + "&7&l joined the server!"); maybe we should not make users angry with this message :D
//            this.core.broadcastSimpleColoredMessage(ChatPosition.SYSTEM, "&3" + player.getName() + "&7 joined the server!");
//        this.server.sendConsoleSimpleColoredMessage("&3" + player.getName() + " &7join to the server.");

            this.core.updatePlayerListHeaderAndFooter(TextComponent.fromLegacyText("Welcome to Diorite!"), TextComponent.fromLegacyText("http://diorite.org"), player); // TODO Tests, remove it
            player.sendTitle(TextComponent.fromLegacyText("Welcome to Diorite"), TextComponent.fromLegacyText("http://diorite.org"), 20, 60, 20); // TODO Tests, remove it
        }));

        this.addLast("Diorite|KickIfCancelled", ((evt, pipeline) -> {
            if (evt.isCancelled())
            {
                evt.getPlayer().kick("Kicked");
            }
        }));
    }
}
