package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.event.others.SenderEvent;

/**
 * Base class for player related events.
 */
public abstract class PlayerEvent extends SenderEvent
{
    /**
     * Construct new player event.
     *
     * @param player player related to event, can't be null.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public PlayerEvent(final Player player)
    {
        super(player);
    }

    /**
     * @return player related to event.
     */
    public Player getPlayer()
    {
        return (Player) this.sender;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
