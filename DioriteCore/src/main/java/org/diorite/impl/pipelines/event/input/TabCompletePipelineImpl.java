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

package org.diorite.impl.pipelines.event.input;

import org.apache.commons.lang3.StringUtils;

import org.diorite.command.sender.CommandSender;
import org.diorite.entity.Player;
import org.diorite.event.input.SenderTabCompleteEvent;
import org.diorite.event.pipelines.event.input.TabCompletePipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class TabCompletePipelineImpl extends SimpleEventPipeline<SenderTabCompleteEvent> implements TabCompletePipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|Base", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            final CommandSender sender = evt.getSender();
            final String msg = evt.getMessage();
            if (evt.isCommand())
            {
                evt.setCompletes(this.core.getCommandMap().tabComplete(sender, msg.substring(1)));
            }
            else
            {
                evt.setCompletes((msg.isEmpty()) ? this.core.getOnlinePlayersNames() : this.core.getOnlinePlayersNames(msg));
            }
        });
        this.addLast("Diorite|Send", (evt, pipeline) -> {
            if (evt.isCancelled() || (evt.getCompletes() == null) || evt.getCompletes().isEmpty())
            {
                return;
            }
            if (evt.getSender().isPlayer())
            {
                ((Player) evt.getSender()).sendTabCompletes(evt.getCompletes());
            }
            else
            {
                evt.getSender().sendSimpleColoredMessage("&7" + StringUtils.join(evt.getCompletes(), "&r, &7"));
            }
        });
    }
}
