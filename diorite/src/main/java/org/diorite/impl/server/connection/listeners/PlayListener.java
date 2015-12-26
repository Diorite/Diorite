/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.server.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientAbilities;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientArmAnimation;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientBlockDig;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientBlockDig.BlockDigAction;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientBlockPlace;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientChat;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientClientCommand;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientCloseWindow;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientCustomPayload;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientEnchantItem;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientEntityAction;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientFlying;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientHeldItemSlot;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientKeepAlive;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientLook;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientPosition;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientPositionLook;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientResourcePackStatus;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSetCreativeSlot;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSettings;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSpectate;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientSteerVehicle;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientTabComplete;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientTeleportAccept;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientTransaction;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientUpdateSign;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientUseEntity;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientUseItem;
import org.diorite.impl.connection.packets.play.client.PacketPlayClientWindowClick;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerDisconnect;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.input.InputAction;
import org.diorite.impl.input.InputActionType;
import org.diorite.GameMode;
import org.diorite.chat.component.BaseComponent;
import org.diorite.event.EventType;
import org.diorite.event.player.PlayerBlockDestroyEvent;
import org.diorite.event.player.PlayerBlockPlaceEvent;
import org.diorite.event.player.PlayerInteractEvent;
import org.diorite.event.player.PlayerInteractEvent.Action;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.inventory.ClickType;
import org.diorite.world.chunk.Chunk;

public class PlayListener implements PacketPlayClientListener
{
    private final DioriteCore        core;
    private final CoreNetworkManager networkManager;
    private final IPlayer            player;

    public PlayListener(final DioriteCore core, final CoreNetworkManager networkManager, final IPlayer player)
    {
        this.core = core;
        this.networkManager = networkManager;
        this.player = player;
    }


    @Override
    public void handle(final PacketPlayClientKeepAlive packet)
    {
        this.networkManager.updateKeepAlive();
    }

    @Override
    public void handle(final PacketPlayClientSettings packet)
    {
//        final byte oldViewDistance = this.player.getViewDistance();
        this.core.sync(() -> this.player.setViewDistance(packet.getViewDistance()), this.player);
//        if (oldViewDistance != this.player.getViewDistance())
//        {
//            this.player.getPlayerChunks().wantUpdate();
//        }
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientCustomPayload packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientHeldItemSlot packet)
    {
        this.core.sync(() -> this.player.setHeldItemSlot(packet.getSlot()), this.player);
    }

    @Override
    public void handle(final PacketPlayClientPositionLook packet)
    {
        this.core.sync(() -> this.player.setPositionAndRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch()), this.player);
    }

    @Override
    public void handle(final PacketPlayClientFlying packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientPosition packet)
    {
        this.core.sync(() -> this.player.setPosition(packet.getX(), packet.getY(), packet.getZ()), this.player);
    }

    @Override
    public void handle(final PacketPlayClientLook packet)
    {
        this.core.sync(() -> this.player.setRotation(packet.getYaw(), packet.getPitch()), this.player);
    }

    @Override
    public void handle(final PacketPlayClientChat packet)
    {
        final String str = packet.getContent();
        //noinspection HardcodedFileSeparator
        if (str.startsWith("/"))
        {
            this.core.getInputThread().add(new InputAction(str.substring(1), this.player, InputActionType.COMMAND));
        }
        else
        {
            this.core.getInputThread().add(new InputAction(str, this.player, InputActionType.CHAT));
        }
    }

    @Override
    public void handle(final PacketPlayClientTabComplete packet)
    {
        this.core.getInputThread().add(new InputAction(packet.getContent(), this.player, InputActionType.TAB_COMPLETE));
    }

    @Override
    public void handle(final PacketPlayClientAbilities packet)
    {
        this.core.sync(() -> this.player.setAbilities(packet), this.player);
    }

    @Override
    public void handle(final PacketPlayClientResourcePackStatus packet)
    {
        // TODO This is not needed? Maybe create event or something other...
    }

    @Override
    public void handle(final PacketPlayClientSetCreativeSlot packet)
    {
        if (this.player.getGameMode().equals(GameMode.CREATIVE))
        {
            this.player.getInventory().setItem(packet.getSlot(), packet.getItem());
        }
//        else
//        {
//            // TODO: maybe do something about this?
//        }
    }

    @Override
    public void handle(final PacketPlayClientSpectate packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientEnchantItem packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientSteerVehicle packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientTransaction packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientUpdateSign packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientUseEntity packet)
    {
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientTeleportAccept packet)
    {
        System.out.println(packet);
    }

    @Override
    public void handle(final PacketPlayClientUseItem packet)
    {
        System.out.println(packet);
        // TODO
    }

    @Override
    public void handle(final PacketPlayClientEntityAction packet)
    {
        this.core.sync(() -> packet.getEntityAction().doAction(this.player, packet.getJumpBoost()), this.player);
    }

    @Override
    public void handle(final PacketPlayClientArmAnimation packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientBlockDig packet)
    {
        this.core.sync(() -> {
            if ((packet.getAction() == BlockDigAction.FINISH_DIG) || ((packet.getAction() == BlockDigAction.START_DIG) && this.player.getGameMode().equals(GameMode.CREATIVE)))
            {
                EventType.callEvent(new PlayerBlockDestroyEvent(this.player, packet.getBlockLocation().setWorld(this.player.getWorld()).getBlock()));
            }

            if (packet.getAction() == BlockDigAction.START_DIG)
            {
                this.core.sync(() -> EventType.callEvent(new PlayerInteractEvent(this.player, Action.LEFT_CLICK_ON_BLOCK, packet.getBlockLocation().setWorld(this.player.getWorld()).getBlock())));
            }

            if (packet.getAction() == BlockDigAction.DROP_ITEM)
            {
                this.core.sync(() -> EventType.callEvent(new PlayerInventoryClickEvent(this.player, (short) - 1, - 1, this.player.getInventory().getHotbarInventory().getSlotOffset() + this.player.getInventory().getHeldItemSlot(), ClickType.DROP_KEY)), this.player);
            }

            if (packet.getAction() == BlockDigAction.DROP_ITEM_STACK)
            {
                this.core.sync(() -> EventType.callEvent(new PlayerInventoryClickEvent(this.player, (short) - 1, - 1, this.player.getInventory().getHotbarInventory().getSlotOffset() + this.player.getInventory().getHeldItemSlot(), ClickType.CTRL_DROP_KEY)), this.player);
            }

            // TODO: implement
        }, this.player);
    }

    @Override
    public void handle(final PacketPlayClientBlockPlace packet)
    {
        System.out.println(packet);
        if (packet.getCursorPos().getBlockFace() == null)
        {
            return;
        }
        this.core.sync(() -> EventType.callEvent(new PlayerInteractEvent(this.player, Action.RIGHT_CLICK_ON_BLOCK, packet.getLocation().setWorld(this.player.getWorld()).getBlock())));

        final int y = packet.getLocation().getY();
        if ((y >= Chunk.CHUNK_FULL_HEIGHT) || (y < 0))
        {
            return; // TODO: some kind of event for that maybe? or edit PlayerBlockPlaceEvent
        }
        this.core.sync(() -> EventType.callEvent(new PlayerBlockPlaceEvent(this.player, packet.getLocation().setWorld(this.player.getWorld()).getBlock().getRelative(packet.getCursorPos().getBlockFace()))), this.player);
    }

    @Override
    public void handle(final PacketPlayClientClientCommand packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientCloseWindow packet)
    {
        CoreMain.debug("Close windows: " + packet.getId());
        this.core.sync(() -> this.player.closeInventory(packet.getId()));
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayClientWindowClick p)
    {
        if (p.getClickType() == null)
        {
            CoreMain.debug("Unknown click type in PacketPlayInWindowClick.");
            return;
        }
        this.core.sync(() -> EventType.callEvent(new PlayerInventoryClickEvent(this.player, p.getActionNumber(), p.getId(), p.getClickedSlot(), p.getClickType())), this.player);
    }

    public CoreNetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        this.core.getPlayersManager().playerQuit(this.player);

        this.networkManager.sendPacket(new PacketPlayServerDisconnect(message));
        this.networkManager.close(message, true);
        // TODO: implement
    }

    @Override
    public Logger getLogger()
    {
        return this.core.getLogger();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("core", this.core).append("networkManager", this.networkManager).toString();
    }

    @Override
    public IPlayer getPlayer()
    {
        return this.player;
    }
}
