package org.diorite.impl.inventory.recipe.craft;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;

abstract class SimpleRecipeImpl extends PriorityRecipeImpl
{
    protected final ItemStack result;

    private final transient List<ItemStack> resultList;

    protected SimpleRecipeImpl(final ItemStack result, final long priority)
    {
        super(priority);
        this.result = result;
        this.resultList = Collections.singletonList(result.clone());
    }

    @Override
    public List<ItemStack> getResult()
    {
        return this.resultList;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("result", this.result).toString();
    }
}
