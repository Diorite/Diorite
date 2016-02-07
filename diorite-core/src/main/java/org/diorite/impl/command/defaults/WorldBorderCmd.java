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

package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.world.WorldImpl;
import org.diorite.Diorite;
import org.diorite.command.CommandPriority;
import org.diorite.world.WorldBorder;

public class WorldBorderCmd extends SystemCommandImpl
{
    public WorldBorderCmd()
    {
        // TODO Move messages to DioriteMessages
        super("worldborder", Pattern.compile("((worldborder|)(wb|)(world-border|))(:(?<world>([a-z0-9_]*))|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setDescription("Manages world border");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final WorldImpl world;
            final String parWorld = matchedPattern.group("world");
            if (parWorld == null)
            {
                if (sender instanceof IPlayer)
                {
                    world = ((IPlayer) sender).getWorld();
                }
                else
                {
                    sender.sendSimpleColoredMessage("&cYou must specify world! Use /worldborder:<worldName>");
                    return;
                }
            }
            else
            {
                world = (WorldImpl) Diorite.getWorldsManager().getWorld(parWorld);
                if (world == null)
                {
                    sender.sendSimpleColoredMessage("&cWorld &3'" + parWorld + "' &cdoesn't exists!");
                    return;
                }
            }

            if (args.length() == 0)
            {
                sender.sendSimpleColoredMessage("&7Current world border status in &3" + world.getName());
                sender.sendSimpleColoredMessage(" &7Border state: &3" + world.getWorldBorder().getWorldBorderState());
                if (world.getWorldBorder().getWorldBorderState() != WorldBorder.State.STATIONARY)
                {
                    sender.sendSimpleColoredMessage(" &7Current size: &3~" + Math.round(world.getWorldBorder().getSize()));
                    sender.sendSimpleColoredMessage(" &7Target size: &3" + world.getWorldBorder().getTargetSize());
                    sender.sendSimpleColoredMessage(" &7Target size reach time: &3" + world.getWorldBorder().getTargetSizeReachTime());
                }
                else
                {
                    sender.sendSimpleColoredMessage(" &7Current size: &3" + world.getWorldBorder().getSize());
                }

                sender.sendSimpleColoredMessage("&7World border commands");
                sender.sendSimpleColoredMessage(" &7/wb &3reset &7- restore default world border settings");
                sender.sendSimpleColoredMessage(" &7/wb &3setsize <size> [<time>] &7- change world border size");
                sender.sendSimpleColoredMessage(" &7/wb &3setcenter <x> <z> &7- change center point of world border");
                return;
            }

            switch (args.asString(0).toLowerCase())
            {
                case "reset":
                {
                    world.getWorldBorder().reset();
                    break;
                }

                case "setsize":
                {
                    if (args.length() == 2)
                    {
                        world.getWorldBorder().setSize(args.asDouble(1));
                    }
                    else if (args.length() == 3)
                    {
                        world.getWorldBorder().setSize(args.asDouble(1), args.asLong(2));
                    }
                    else
                    {
                        sender.sendSimpleColoredMessage("&cInvalid arguments!");
                    }
                    break;
                }

                case "setcenter":
                {
                    if (args.length() != 3)
                    {
                        sender.sendSimpleColoredMessage("&cInvalid arguments!");
                        return;
                    }
                    world.getWorldBorder().setCenter(args.asDouble(1), args.asDouble(2));
                    break;
                }

                default:
                {
                    sender.sendSimpleColoredMessage("&cInvalid sub-command!");
                    break;
                }
            }
        });
    }
}
