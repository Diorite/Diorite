package org.diorite.event.player;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.event.Event;

/**
 * Base class for player related events.
 */
public abstract class PlayerEvent extends Event
{
    private final Player player;

    /**
     * Construct new player event.
     *
     * @param player player related to event, can't be null.
     */
    public PlayerEvent(final Player player)
    {
        Validate.notNull(player, "player can't be null.");
        this.player = player;
    }

    /**
     * @return player related to event.
     */
    public Player getPlayer()
    {
        return this.player;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PlayerEvent))
        {
            return false;
        }

        final PlayerEvent that = (PlayerEvent) o;
        return this.player.equals(that.player);

    }

    @Override
    public int hashCode()
    {
        return this.player.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("player", this.player).toString();
    }
}
