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

import org.diorite.chat.ChatPosition;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.QuitPipeline;
import org.diorite.event.player.PlayerQuitEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class QuitPipelineImpl extends SimpleEventPipeline<PlayerQuitEvent> implements QuitPipeline
{
    @Override
    public void reset_()
    {
        this.addAfter(EventPriority.NORMAL, "Diorite|SendMessages", ((evt, pipeline) -> {
            this.core.broadcastSimpleColoredMessage(ChatPosition.ACTION, "&3&l" + evt.getPlayer().getName() + "&7&l left from the server!");
            this.core.broadcastSimpleColoredMessage(ChatPosition.SYSTEM, "&3" + evt.getPlayer().getName() + "&7 left from the server!");
        }));
    }
}
