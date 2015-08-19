package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerAbilities;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerCustomPayload;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerHeldItemSlot;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerLogin;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPlayerInfo;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerPosition;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerServerDifficulty;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnPosition;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.TeleportData;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.TextComponent;
import org.diorite.entity.Player;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.JoinPipeline;
import org.diorite.event.player.PlayerJoinEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;
import org.diorite.world.Dimension;
import org.diorite.world.WorldType;

import io.netty.buffer.Unpooled;

public class JoinPipelineImpl extends SimpleEventPipeline<PlayerJoinEvent> implements JoinPipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|StartPackets", ((evt, pipeline) -> {
            // TODO
            final PlayerImpl player = (PlayerImpl) evt.getPlayer();
            player.getNetworkManager().sendPacket(new PacketPlayServerLogin(player.getId(), player.getGameMode(), false, Dimension.OVERWORLD, Difficulty.PEACEFUL, 20, WorldType.FLAT));
            player.getNetworkManager().sendPacket(new PacketPlayServerCustomPayload("MC|Brand", new PacketDataSerializer(Unpooled.buffer()).writeText(DioriteCore.getInstance().getServerModName())));
            player.getNetworkManager().sendPacket(new PacketPlayServerServerDifficulty(Difficulty.EASY));
            player.getNetworkManager().sendPacket(new PacketPlayServerSpawnPosition(new BlockLocation(2, 255, - 2)));
            player.getNetworkManager().sendPacket(new PacketPlayServerAbilities(false, false, false, false, Player.WALK_SPEED, Player.FLY_SPEED));
            player.getNetworkManager().sendPacket(new PacketPlayServerHeldItemSlot(player.getHeldItemSlot()));
            player.getNetworkManager().sendPacket(new PacketPlayServerPosition(new TeleportData(2, 255, - 2)));
        }));

        this.addAfter("Diorite|StartPackets", "Diorite|EntityStuff", ((evt, pipeline) -> {
            final PlayerImpl player = (PlayerImpl) evt.getPlayer();
            player.getWorld().addEntity(player);

            DioriteCore.getInstance().addSync(() -> {
                PacketPlayServer[] newPackets = player.getSpawnPackets();
                DioriteCore.getInstance().getPlayersManager().forEachExcept(player, p -> {
                    PacketPlayServer[] playerPackets = p.getSpawnPackets();
                    player.getNetworkManager().sendPackets(playerPackets);
                    p.getNetworkManager().sendPackets(newPackets);
                });
            });
        }));

        this.addAfter("Diorite|EntityStuff", "Diorite|PlayerListStuff", ((evt, pipeline) -> {
            final PlayerImpl player = (PlayerImpl) evt.getPlayer();

            DioriteCore.getInstance().getPlayersManager().forEach(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.ADD_PLAYER, player));
            DioriteCore.getInstance().getPlayersManager().forEach(p -> player.getNetworkManager().sendPacket(new PacketPlayServerPlayerInfo(PacketPlayServerPlayerInfo.PlayerInfoAction.ADD_PLAYER, p)));
        }));

        this.addAfter(EventPriority.NORMAL, "Diorite|JoinMessage", ((evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            final Player player = evt.getPlayer();
            this.core.broadcastSimpleColoredMessage(ChatPosition.ACTION, "&3&l" + player.getName() + "&7&l joined the server!");
            this.core.broadcastSimpleColoredMessage(ChatPosition.SYSTEM, "&3" + player.getName() + "&7 joined the server!");
//        this.server.sendConsoleSimpleColoredMessage("&3" + player.getName() + " &7join to the server.");

            this.core.updatePlayerListHeaderAndFooter(TextComponent.fromLegacyText("Welcome to Diorite!"), TextComponent.fromLegacyText("http://diorite.org"), player); // TODO Tests, remove it
            player.sendTitle(TextComponent.fromLegacyText("Welcome to Diorite"), TextComponent.fromLegacyText("http://diorite.org"), 20, 100, 20); // TODO Tests, remove it
        }));

        this.addLast("Diorite|KickIfCancelled", ((evt, pipeline) -> {
            if (evt.isCancelled())
            {
                evt.getPlayer().kick("Kicked");
            }
        }));
    }
}
