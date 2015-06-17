package org.diorite.impl.world.tick;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;

public class WorldsTickGroup implements TickGroupImpl
{
    private final Set<WorldImpl> worlds;

    public WorldsTickGroup(final Set<WorldImpl> worlds)
    {
        this.worlds = worlds;
    }

    @Override
    public void doTick(final int tps)
    {
        this.worlds.forEach(w -> w.doTick(tps));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("worlds", this.worlds).toString();
    }
}
