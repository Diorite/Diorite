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

package org.diorite.impl.pipelines.event.player;

import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.ComponentBuilder;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.ChatPipeline;
import org.diorite.event.player.PlayerChatEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChatPipelineImpl extends SimpleEventPipeline<PlayerChatEvent> implements ChatPipeline
{
    @Override
    public void reset_()
    {
        this.addBefore(EventPriority.NORMAL, "Diorite|Format", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            evt.setMessage(ComponentBuilder.start(evt.getPlayer().getName()).append(": ").color(ChatColor.AQUA).appendLegacy(evt.getMessage().getText()).color(ChatColor.GRAY).create());
        });
        this.addLast("Diorite|Send", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            this.core.broadcastMessage(ChatPosition.CHAT, evt.getMessage());
//            this.server.getConsoleSender().sendMessage(evt.getMessage());
        });
    }
}
