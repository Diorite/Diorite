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

package org.diorite.impl.entity.attrib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.GameObject;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeStorage;
import org.diorite.entity.attrib.AttributeType;

public class AttributeStorageImpl implements AttributeStorage
{
    private final GameObject gameObject;
    private final Map<AttributeType, AttributeProperty> attributes = new ConcurrentHashMap<>(4, 0.3f, 8);

    public AttributeStorageImpl(final GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    @Override
    public void removeAttributeProperty(final AttributeType type)
    {
        this.attributes.remove(type);
    }

    @Override
    public void removeModifier(final AttributeType type, final UUID uuid)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return;
        }
        prop.removeModifier(uuid);
    }

    @Override
    public void addAttributeProperty(final AttributeProperty property)
    {
        this.attributes.put(property.getType(), property);
    }

    @Override
    public Collection<AttributeProperty> getProperties()
    {
        return this.attributes.values();
    }

    @Override
    public Collection<AttributeModifier> getModifiers(final AttributeType type)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return new HashSet<>(1);
        }
        return new HashSet<>(prop.getModifiersCollection());
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type)
    {
        AttributeProperty attrib = this.attributes.get(type);
        if (attrib == null)
        {
            this.attributes.put(type, attrib = new AttributePropertyImpl(type));
        }
        return attrib;
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type, final double value)
    {
        AttributeProperty attrib = this.attributes.get(type);
        if (attrib == null)
        {
            this.attributes.put(type, attrib = new AttributePropertyImpl(type, value));
        }
        return attrib;
    }

    @Override
    public void addModifier(final AttributeType type, final AttributeModifier modifer)
    {
        AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            prop = new AttributePropertyImpl(type, type.getDefaultValue());
            this.attributes.put(type, prop);
        }
        prop.addModifier(modifer);
    }

    @Override
    public GameObject getGameObject()
    {
        return this.gameObject;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("attributes", this.attributes).toString();
    }
}
