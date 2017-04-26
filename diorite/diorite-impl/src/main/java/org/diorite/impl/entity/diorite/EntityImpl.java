/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.entity.diorite;

import org.diorite.ImmutableLocation;
import org.diorite.Location;
import org.diorite.impl.entity.IEntity;

import javax.annotation.Nullable;

public class EntityImpl implements IEntity
{
    private int id;

    private Location location;

    //TODO: core and uuid arg
    EntityImpl(final int id, final ImmutableLocation location)
    {
        //TODO: game object

        this.id = id;
        this.location = location; //TODO: check

        //TODO: metadata, lazy entity
    }

    @Nullable
    @Override
    public String getCustomName()
    {
        return ""; //TODO: metadata
    }

    @Override
    public void setCustomName(@Nullable String name)
    {
        //TODO: metadata
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return false; //TODO: metadata
    }

    @Override
    public void setCustomNameVisible(boolean visible)
    {
        //TODO: metadata
    }
}
