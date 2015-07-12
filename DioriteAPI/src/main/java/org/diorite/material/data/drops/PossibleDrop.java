package org.diorite.material.data.drops;

import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.Block;

// TODO: add more sublcasses for easier creating drops configs.
public abstract class PossibleDrop
{
    protected final ItemStack itemStack;

    protected PossibleDrop(final ItemStack itemStack)
    {
        Validate.notNull(itemStack, "Drop item can't be null");
        this.itemStack = itemStack;
    }

    /**
     * Returns raw ItemStack of this possible drop. <br>
     * This item may be modified by different subclasses before real drop.
     *
     * @return raw ItemStack of this possible drop.
     *
     * @see #simulateDrop(Random)
     */
    public ItemStack getItemStack()
    {
        return this.itemStack.clone();
    }

    /**
     * Implementation of this method should simulate real drop from block. <br>
     * Method must handle case when block or usedTool is null.
     *
     * @param rand     random instance, should be used if random number is needed.
     * @param drops    drops list, now drops should be add here.
     * @param usedTool may be null! Used tool.
     * @param block    may be null! Block related to this drop simulation if exist.
     */
    public abstract void simulateDrop(DioriteRandom rand, Set<ItemStack> drops, ItemStack usedTool, Block block);

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PossibleDrop))
        {
            return false;
        }

        final PossibleDrop that = (PossibleDrop) o;

        return this.itemStack.equals(that.itemStack);
    }

    @Override
    public int hashCode()
    {
        return this.itemStack.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("itemStack", this.itemStack).toString();
    }
}
