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

import org.diorite.cfg.messages.DioriteMesssges;
import org.diorite.cfg.messages.Message;
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
        this.setDescription("Kick player");
        this.setCommandExecutor((sender, command, label, matchedPattern, args) -> {
            if (CoreMain.isEnabledDebug() && !sender.isConsole())
            {
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_CMD_CMD_DISABLED, sender, sender.getPreferedLocale(), Message.MessageData.e("sender", sender));
                return;
            }
            if (! args.has(1))
            {
                sender.sendSimpleColoredMessage("&cCorrect usage &7/kick <player> <reason>");
                return;
            }

            final Player target = args.asPlayer(0);

            if (target == null)
            {
                DioriteMesssges.sendMessage(DioriteMesssges.MSG_CMD_NO_TARGET, sender, sender.getPreferedLocale(), Message.MessageData.e("sender", sender), Message.MessageData.e("target", target));
                return;
            }

            final String reason = args.asText(1);

            target.kick(TextComponent.fromLegacyText(reason));

            sender.getCore().broadcastMessage("&7" + target.getName() + " &chas been kicked by&7 " + sender.getName()); //TODO: Send only to ops
        });
    }
}
