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

import java.util.Collections;

import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.GameMode;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;

public class GamemodeCmd extends SystemCommandImpl
{
    public GamemodeCmd()
    {
        super("gamemode", Collections.singletonList("gm"), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final Player target = args.has(1) ? args.asPlayer(1) : ((sender instanceof Player) ? (Player) sender : null);
            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4No target...");
                return;
            }
            GameMode gameMode = args.has(0) ? args.asSimpleEnumValue(GameMode.class, 0) : (target.getGameMode().equals(GameMode.CREATIVE) ? GameMode.SURVIVAL : GameMode.CREATIVE);
            if (gameMode == null)
            {
                sender.sendSimpleColoredMessage("&4No gamemode named: &c" + args.asString(0));
                return;
            }
            target.setGameMode(gameMode);

            //noinspection ObjectEquality
            if (target == sender) // this should be this same object
            {
                sender.sendSimpleColoredMessage("&7Changed gamemode to &3" + gameMode.getName());
            }
            else
            {
                sender.sendSimpleColoredMessage("&7Changed gamemode of &3" + target.getName() + "&7 to &3" + gameMode.getName());
                target.sendSimpleColoredMessage("&7Changed gamemode to &3" + gameMode.getName());
            }
        });
    }
}
