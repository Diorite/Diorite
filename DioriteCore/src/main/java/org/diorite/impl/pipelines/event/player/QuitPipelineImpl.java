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
