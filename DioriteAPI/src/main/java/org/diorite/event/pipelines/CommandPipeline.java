package org.diorite.event.pipelines;

import org.diorite.event.others.SenderCommandEvent;

/**
 * {@link EventPipeline} using {@link SenderCommandEvent} as type.
 * <p>
 * Default handlers are: <br>
 * <b>Diorite|Cmd</b> {@literal ->} find command to execute. (only if command ism't already set) <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 * <b>Diorite|Exec</b> {@literal ->} execute command or send unknown command message.
 */
public interface CommandPipeline extends EventPipeline<SenderCommandEvent>
{
}
