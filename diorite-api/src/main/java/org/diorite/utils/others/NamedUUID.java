/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.others;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent UUID with name.
 */
public class NamedUUID
{
    private final String name;
    private final UUID   uuid;

    /**
     * Construct new named UUID with given name and uuid.
     *
     * @param name name of uuid.
     * @param uuid named uuid.
     */
    public NamedUUID(final String name, final UUID uuid)
    {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Returns name of this uuid.
     *
     * @return name of this uuid.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns uuid of this named uuid.
     *
     * @return uuid of this named uuid.
     */
    public UUID getUuid()
    {
        return this.uuid;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NamedUUID))
        {
            return false;
        }

        final NamedUUID namedUUID = (NamedUUID) o;

        return (this.name != null) ? this.name.equals(namedUUID.name) : ((namedUUID.name == null) && ((this.uuid != null) ? this.uuid.equals(namedUUID.uuid) : (namedUUID.uuid == null)));
    }

    @Override
    public int hashCode()
    {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = (31 * result) + ((this.uuid != null) ? this.uuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("uuid", this.uuid).toString();
    }
}
