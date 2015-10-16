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
import org.diorite.command.Arguments;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;
import org.diorite.utils.math.DioriteMathUtils;

public class FlyCmd extends SystemCommandImpl
{
    public FlyCmd()
    {
        super("fly", Pattern.compile("(fly)(:(?<speed>((\\d+|)(\\.\\d+|)))|)", Pattern.CASE_INSENSITIVE), CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            final String speedArg = matchedPattern.group("speed");
            final double speed;
            final Player target;
            Boolean on = null;

            if (args.length() >= 2)
            {
                target = args.asPlayer(0);
                on = args.asBoolean(1);
            }
            else
            {
                target = args.has(0) ? args.asPlayer(0) : ((sender instanceof Player) ? (Player) sender : null);
            }

            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4No target...");
                return;
            }

            if (speedArg == null)
            {
                speed = Player.FLY_SPEED;
            }
            else if (speedArg.isEmpty())
            {
                speed = target.getFlySpeed();
            }
            else
            {
                speed = (Arguments.asDouble(speedArg) / 100);
            }
            if (speed > 1)
            {
                sender.sendSimpleColoredMessage("&4Speed can't by larger than 100");
                return;
            }

            if (on == null)
            {
                on = ! ((speedArg == null) || speedArg.isEmpty()) || ! target.canFly();
            }
            target.setCanFly(on, speed);

            //noinspection ObjectEquality
            if (target == sender) // this should be this same object
            {
                sender.sendSimpleColoredMessage("&7Fly &3" + (target.canFly() ? "enabled" : "disabled") + "&7! (Speed: &3" + DioriteMathUtils.formatSimpleDecimal(target.getFlySpeed() * 100) + "&7)");
            }
            else
            {
                sender.sendSimpleColoredMessage("&7Fly &3" + (target.canFly() ? "enabled" : "disabled") + "&7 for &3" + target.getName() + "&7! (Speed: &3" + DioriteMathUtils.formatSimpleDecimal(target.getFlySpeed() * 100) + "&7)");
            }
        });
    }
}
