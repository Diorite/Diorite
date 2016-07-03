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

package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundAnimation;
import org.diorite.entity.data.HandType;
import org.diorite.event.pipelines.event.player.ArmAnimationPipeline;
import org.diorite.event.player.PlayerArmAnimationEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ArmAnimationPipelineImpl extends SimpleEventPipeline<PlayerArmAnimationEvent> implements ArmAnimationPipeline
{
    @Override
    public void reset_()
    {
        this.addLast("Diorite|UpdateClients", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            
            DioriteCore.getInstance().getPlayersManager().forEachExcept(evt.getPlayer(), p -> p.getWorld().equals(evt.getPlayer().getWorld()), new PacketPlayClientboundAnimation(evt.getPlayer().getId(), evt.getHand() == HandType.MAIN ? 0 : 3));
        });
    }
}
