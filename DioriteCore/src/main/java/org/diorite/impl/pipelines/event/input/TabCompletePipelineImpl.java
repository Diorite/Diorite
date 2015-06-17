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
                evt.setCompletes(this.server.getCommandMap().tabComplete(sender, msg.substring(1)));
            }
            else
            {
                evt.setCompletes((msg.isEmpty()) ? this.server.getOnlinePlayersNames() : this.server.getOnlinePlayersNames(msg));
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
