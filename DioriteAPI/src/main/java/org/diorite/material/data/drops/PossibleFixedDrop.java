package org.diorite.material.data.drops;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.Block;

public class PossibleFixedDrop extends PossibleDrop
{
    protected final int amount;

    public PossibleFixedDrop(final ItemStack itemStack, final int amount)
    {
        super(itemStack);
        this.amount = amount;
    }

    public PossibleFixedDrop(final ItemStack itemStack)
    {
        super(itemStack);
        this.amount = 1;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops, final ItemStack usedTool, final Block block)
    {
        if (this.amount == 0)
        {
            return;
        }
        if (this.amount == 1)
        {
            drops.add(this.getItemStack());
            return;
        }
        for (int i = 0; i < this.amount; i++)
        {
            final ItemStack itemStack = this.getItemStack();
            if ((itemStack.getMaterial().getId() != 0) && (itemStack.getAmount() != 0))
            {
                drops.add(itemStack);
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("amount", this.amount).toString();
    }
}
