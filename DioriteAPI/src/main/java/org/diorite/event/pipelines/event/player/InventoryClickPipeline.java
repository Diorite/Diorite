package org.diorite.event.pipelines.event.player;

import org.diorite.event.pipelines.EventPipeline;
import org.diorite.event.player.PlayerInventoryClickEvent;

/**
 * {@link EventPipeline} using {@link PlayerInventoryClickEvent} as type.
 * <p>
 * Default handlers are: <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 * <b>DDiorite|Handle</b> {@literal ->} confirm or deny transaction and handle click if not cancelled <br>
 */
public interface InventoryClickPipeline extends EventPipeline<PlayerInventoryClickEvent>
{
}
