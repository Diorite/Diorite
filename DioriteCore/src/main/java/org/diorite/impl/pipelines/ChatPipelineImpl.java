package org.diorite.impl.pipelines;

import org.diorite.chat.ChatColor;
import org.diorite.chat.ChatPosition;
import org.diorite.chat.component.ComponentBuilder;
import org.diorite.event.EventPriority;
import org.diorite.event.player.PlayerChatEvent;
import org.diorite.event.pipelines.ChatPipeline;

public class ChatPipelineImpl extends EventPipelineImpl<PlayerChatEvent> implements ChatPipeline
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
            this.server.broadcastMessage(ChatPosition.CHAT, evt.getMessage());
            this.server.getConsoleSender().sendMessage(evt.getMessage());
        });
    }
}
