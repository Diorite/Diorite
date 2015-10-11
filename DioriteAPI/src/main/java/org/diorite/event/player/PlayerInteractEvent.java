package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.diorite.entity.Player;
import org.diorite.world.Block;

/**
 * Fired when player interact
 */
public class PlayerInteractEvent extends PlayerEvent
{
    protected final Action action;
    protected final Block block;

    /**
     * Construct new player interact event.
     *
     * @param player player related to event, can't be null.
     * @param action action that has been done
     * @param block block that has been clicked or null if the block is air
     */
    public PlayerInteractEvent(final Player player, final Action action, final Block block)
    {
        super(player);
        this.action = action;
        this.block = block;
    }

    /**
     * Returns an action that has been done
     *
     * @return an action enum
     */
    public Action getAction()
    {
        return action;
    }

    /**
     * @return targeted block or null if the block is air
     */
    public Block getBlock()
    {
        return block;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).append("action", this.action).toString();
    }

    /**
     * An enum that describing an action that has been done
     */
    public enum Action
    {
        LEFT_CLICK_ON_BLOCK,
        LEFT_CLICK_ON_AIR,
        RIGHT_CLICK_ON_BLOCK,
        RIGHT_CLICK_ON_AIR
    }
}
