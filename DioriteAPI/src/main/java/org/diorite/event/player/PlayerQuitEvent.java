package org.diorite.event.player;

import org.diorite.entity.Player;

public class PlayerQuitEvent extends PlayerEvent
{
    /**
     * Construct new PlayerQuitEvent.
     *
     * @param player player related to event, can't be null.
     */
    public PlayerQuitEvent(final Player player)
    {
        super(player);
    }
}
