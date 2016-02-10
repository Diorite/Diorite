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

package org.diorite.impl.command.defaults;

import java.util.regex.Pattern;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.world.WorldImpl;
import org.diorite.Diorite;
import org.diorite.cfg.messages.DioriteMessages;
import org.diorite.cfg.messages.Message;
import org.diorite.command.CommandPriority;
import org.diorite.utils.math.DioriteMathUtils;

public class TimeCommand extends SystemCommandImpl
{
    public TimeCommand()
    {
        super("time", Pattern.compile("((time|)(t|))(:(?<world>([a-z0-9_]*))|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
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
                    DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_CONSOLE_NO_WORLD, sender, Message.MessageData.e("command", this));
                    return;
                }
            }
            else
            {
                world = (WorldImpl) Diorite.getWorldsManager().getWorld(parWorld);
                if (world == null)
                {
                    DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NO_WORLD, sender);
                    return;
                }
            }

            if (args.length() == 0)
            {
                DioriteMessages.sendMessage(DioriteMessages.MSG_TIME_CURRENT, sender, Message.MessageData.e("world", world));
                return;
            }
            else if (args.length() == 1)
            {
                DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_INVALID_ARGUMENTS, sender);
            }

            switch (args.asString(0).toLowerCase())
            {
                case "set":
                {
                    final String timeString = args.asString(1);

                    Integer newTime = DioriteMathUtils.asInt(timeString);
                    if (newTime == null)
                    {
                        if (timeString.equalsIgnoreCase("day"))
                        {
                            newTime = 1000;
                        }
                        else if (timeString.equalsIgnoreCase("night"))
                        {
                            newTime = 14000;
                        }
                        else
                        {
                            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_INVALID_ARGUMENTS, sender);
                            return;
                        }
                    }
                    else
                    {
                        if (newTime < 0)
                        {
                            DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_NUMBER_NOT_POSITIVE_OR_ZERO, sender);
                            return;
                        }
                    }

                    world.setTime(newTime);
                    DioriteMessages.sendMessage(DioriteMessages.MSG_TIME_CHANGED, sender, Message.MessageData.e("world", world));
                    break;
                }

                case "add":
                {
                    final Integer timeDelta = DioriteMathUtils.asInt(args.asString(1));
                    if (timeDelta == null)
                    {
                        DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_INVALID_ARGUMENTS, sender);
                        return;
                    }

                    long newTime = world.getTime() + timeDelta;
                    if (newTime > 24000)
                    {
                        newTime =- 24000;
                    }
                    else if (newTime < 0)
                    {
                        newTime =+ 24000;
                    }

                    world.setTime(newTime);
                    DioriteMessages.sendMessage(DioriteMessages.MSG_TIME_CHANGED, sender, Message.MessageData.e("world", world));
                    break;
                }

                default:
                {
                    DioriteMessages.sendMessage(DioriteMessages.MSG_CMD_INVALID_ARGUMENTS, sender);
                    break;
                }
            }
        });
    }
}
