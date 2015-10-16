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

import org.diorite.impl.CoreMain;
import org.diorite.impl.command.SystemCommandImpl;
import org.diorite.chat.component.TextComponent;
import org.diorite.command.CommandPriority;
import org.diorite.entity.Player;

public class KickCmd extends SystemCommandImpl
{
    public KickCmd()
    {
        super("kick", (Pattern) null, CommandPriority.LOW);
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (CoreMain.isEnabledDebug())
            {
                sender.sendSimpleColoredMessage("&4Command disabled for testing. (Will be re-added with permission system)");
                return;
            }
            if (! args.has(1))
            {
                sender.sendSimpleColoredMessage("&4Invalid usage. Use: /kick <nick> <reason>");
                return;
            }

            final Player target = args.asPlayer(0);

            if (target == null)
            {
                sender.sendSimpleColoredMessage("&4Given player isn't online!");
                return;
            }

            final String reason = args.asText(1);

            target.kick(TextComponent.fromLegacyText(reason));

            sender.getCore().broadcastMessage(target.getName() + " has been kicked by " + sender.getName()); //TODO: Send only to ops
        });
    }
}
