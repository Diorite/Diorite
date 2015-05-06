package org.diorite.impl.inventory;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryHolder;
import org.diorite.utils.collections.ConcurrentSet;

public abstract class InventoryImpl<T extends InventoryHolder> implements Inventory
{
    protected final T holder;
    protected       String          title;
    protected final Set<Player> viewers = new ConcurrentSet<>(5, 0.2f, 6);

    protected InventoryImpl(final T holder)
    {
        this.holder = holder;
    }

    @Override
    public InventoryIterator iterator()
    {
        return new InventoryIterator(this);
    }

    @Override
    public void setTitle(final String str)
    {
        this.title = str;
        this.update();
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public T getHolder()
    {
        return this.holder;
    }

    @Override
    public Set<Player> getViewers()
    {
        return this.viewers;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("title", this.title).toString();
    }
}
