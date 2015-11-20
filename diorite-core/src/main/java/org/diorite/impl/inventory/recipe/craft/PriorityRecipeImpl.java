package org.diorite.impl.inventory.recipe.craft;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.recipe.craft.Recipe;

abstract class PriorityRecipeImpl implements Recipe
{
    protected final long priority;

    protected PriorityRecipeImpl(final long priority)
    {
        this.priority = priority;
    }

    @Override
    public long getPriority()
    {
        return this.priority;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("priority", this.priority).toString();
    }
}
