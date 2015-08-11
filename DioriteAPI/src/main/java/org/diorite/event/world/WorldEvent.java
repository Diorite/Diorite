package org.diorite.event.world;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.Event;
import org.diorite.world.World;

/**
 * Base class for world related events.
 */
public class WorldEvent extends Event
{
    protected final World world;

    /**
     * Construct new world event.
     *
     * @param world world related to event, can't be null.
     */
    public WorldEvent(final World world)
    {
        Validate.notNull(world, "world can't be null.");
        this.world = world;
    }

    /**
     * @return world related to event.
     */
    public World getWorld()
    {
        return this.world;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).toString();
    }
}
