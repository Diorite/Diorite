package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.world.Block;
import org.diorite.world.World;

public class PlayerBlockDestroyEvent extends PlayerEvent
{
    protected final ItemStack itemInHand;
    protected       Block     block;

    public PlayerBlockDestroyEvent(final Player player, final Block block)
    {
        super(player);
        this.block = block;
        final ItemStack item = player.getInventory().getItemInHand();
        this.itemInHand = (item == null) ? null : item.clone();
    }

    public Block getBlock()
    {
        return this.block;
    }

    public void setBlock(final Block block)
    {
        this.block = block;
    }

    public BlockLocation getLocation()
    {
        return this.block.getLocation();
    }

    public World getWorld()
    {
        return this.block.getLocation().getWorld();
    }

    /**
     * Returns clone of ItemStack used when destroying block.
     *
     * @return clone of ItemStack used when destroying block.
     */
    public ItemStack getItemInHand()
    {
        return this.itemInHand;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }
}
