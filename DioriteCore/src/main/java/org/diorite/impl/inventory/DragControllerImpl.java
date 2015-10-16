/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
