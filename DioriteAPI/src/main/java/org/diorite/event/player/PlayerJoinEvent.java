package org.diorite.event.player;

import org.diorite.entity.Player;

public class PlayerJoinEvent extends PlayerEvent
{
    /**
     * Construct new PlayerJoinEvent.
     *
     * @param player player related to event, can't be null.
     */
    public PlayerJoinEvent(final Player player)
    {
        super(player);
    }
}
