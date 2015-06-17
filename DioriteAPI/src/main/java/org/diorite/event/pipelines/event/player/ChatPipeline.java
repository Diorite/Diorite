package org.diorite.event.pipelines.event.player;

import org.diorite.event.pipelines.EventPipeline;
import org.diorite.event.player.PlayerChatEvent;

/**
 * {@link EventPipeline} using {@link PlayerChatEvent} as type.
 * <p>
 * Default handlers are: <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * <b>Diorite|Format</b> {@literal ->} add nickname to message, add some colors. <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 * <b>Diorite|Send</b> {@literal ->} send message to players and console.
 */
public interface ChatPipeline extends EventPipeline<PlayerChatEvent>
{
}
