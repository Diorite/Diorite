package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.world.Block;

public class PlayerBlockPlaceEvent extends PlayerEvent
{
    private Block block;

    /**
     * Construct new player event.
     *
     * @param player player related to event, can't be null.
     */
    public PlayerBlockPlaceEvent(final Player player, final Block block)
    {
        super(player);
        this.block = block;
    }

    public Block getBlock()
    {
        return this.block;
    }

    public void setBlock(final Block block)
    {
        this.block = block;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }
}
