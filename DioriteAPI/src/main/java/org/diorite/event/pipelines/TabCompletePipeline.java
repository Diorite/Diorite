package org.diorite.event.pipelines;

import java.util.List;

import org.diorite.event.others.SenderTabCompleteEvent;
import org.diorite.event.player.PlayerChatEvent;

/**
 * {@link EventPipeline} using {@link PlayerChatEvent} as type.
 * <p>
 * Default handlers are: <br>
 * <b>Diorite|Base</b> {@literal ->} Find default tab completes. <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 * <b>Diorite|Send</b> {@literal ->} send tab complete packet to player. {@link org.diorite.entity.Player#sendTabCompletes(List)}
 */
public interface TabCompletePipeline extends EventPipeline<SenderTabCompleteEvent>
{
}
