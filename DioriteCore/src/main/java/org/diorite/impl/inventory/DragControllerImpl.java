package org.diorite.impl.inventory;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.DragController;

public class DragControllerImpl implements DragController
{
    private boolean started;
    private boolean right;
    private final Collection<Integer> slots = new LinkedList<>();

    @Override
    public boolean start(final boolean right)
    {
        if (this.started)
        {
            return false; // We can start new drag while other is in progress
        }
        this.right = right;
        this.slots.clear();
        this.started = true;
        return true;
    }

    @Override
    public boolean addSlot(final boolean right, final int slot)
    {
        if (! this.started)
        {
            return false; // We can't add slot while drag isn't started
        }

        if (this.right != right)
        {
            return false;
        }

        this.slots.add(slot);
        return true;
    }

    @Override
    public Collection<Integer> end(final boolean right)
    {
        if (! this.started)
        {
            return null;
        }

        if (this.right != right)
        {
            return null;
        }

        this.started = false;

        return this.slots;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("started", this.started).append("right", this.right).append("slots", this.slots).toString();
    }
}
