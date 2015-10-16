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
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;
import org.diorite.entity.attrib.ModifierValue;
import org.diorite.utils.math.DioriteMathUtils;

public class AttributePropertyImpl implements AttributeProperty
{
    private final AttributeType                type;
    private final double                       value;
    private final Map<UUID, AttributeModifier> modifiers;
    private double finalValue = Double.NaN;
    private int lastHash;

    public AttributePropertyImpl(final AttributeType type, final Collection<AttributeModifier> modifiers)
    {
        this.type = type;
        this.value = type.getDefaultValue();
        if (modifiers != null)
        {
            this.modifiers = new ConcurrentHashMap<>(modifiers.size() + 1, 0.3f, 8);
            modifiers.forEach(mod -> this.modifiers.put(mod.getUuid(), mod));
        }
        else
        {
            this.modifiers = new ConcurrentHashMap<>(5, 0.3f, 8);
        }
        this.recalculateFinalValue();
    }

    public AttributePropertyImpl(final AttributeType type)
    {
        this.type = type;
        this.value = type.getDefaultValue();
        this.modifiers = new ConcurrentHashMap<>(5, 0.3f, 8);
        this.recalculateFinalValue();
    }

    public AttributePropertyImpl(final AttributeType type, final double value)
    {
        this.type = type;
        this.value = DioriteMathUtils.getInRange(value, type.getMinValue(), type.getMaxValue());
        this.modifiers = new ConcurrentHashMap<>(5, 0.3f, 8);
        this.recalculateFinalValue();
    }

    public AttributePropertyImpl(final AttributeType type, final Collection<AttributeModifier> modifiers, final double value)
    {
        this.type = type;
        this.value = value;
        if (modifiers != null)
        {
            this.modifiers = new ConcurrentHashMap<>(modifiers.size() + 1, 0.3f, 8);
            modifiers.forEach(mod -> this.modifiers.put(mod.getUuid(), mod));
        }
        else
        {
            this.modifiers = new ConcurrentHashMap<>(5, 0.3f, 8);
        }
        this.recalculateFinalValue();
    }

    @Override
    public AttributeType getType()
    {
        return this.type;
    }

    @Override
    public double getBaseValue()
    {
        return this.value;
    }

    @Override
    public double getFinalValue()
    {
        if (! Double.isNaN(this.finalValue) && (this.lastHash == this.modifiers.hashCode()))
        {
            return this.finalValue;
        }
        return this.finalValue = this.recalculateFinalValue();
    }

    @Override
    public double recalculateFinalValue()
    {
        ModifierValue modifierValue = new ModifierValue(this.value);
        final Iterable<AttributeModifier> attribs = new HashSet<>(this.getModifiersCollection());
        for (final ModifierOperation operation : ModifierOperation.getSortedByID())
        {
            for (final Iterator<AttributeModifier> iterator = attribs.iterator(); iterator.hasNext(); )
            {
                final AttributeModifier modifier = iterator.next();
                if (modifier.getOperation().ordinal() == operation.ordinal())
                {
                    modifierValue = operation.use(modifierValue, modifier.getValue());
                    iterator.remove();
                }
            }
            modifierValue = operation.onEnd(modifierValue);
        }
        this.lastHash = this.modifiers.hashCode();
        return this.finalValue = modifierValue.getY();
    }

    @Override
    public Collection<AttributeModifier> getModifiersCollection()
    {
        return this.modifiers.values();
    }

    @Override
    public Map<UUID, AttributeModifier> getModifiers()
    {
        return this.modifiers;
    }

    @Override
    public Optional<AttributeModifier> getModifier(final UUID uuid)
    {
        return Optional.ofNullable(this.modifiers.get(uuid));
    }

    @Override
    public AttributeModifier removeModifier(final UUID uuid)
    {
        final AttributeModifier removed = this.modifiers.remove(uuid);
        this.recalculateFinalValue();
        return removed;
    }

    @Override
    public void addModifier(final AttributeModifier mod)
    {
        this.modifiers.put(mod.getUuid(), mod);
        this.recalculateFinalValue();
    }

    @Override
    public int hashCode()
    {
        return this.modifiers.hashCode();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AttributePropertyImpl))
        {
            return false;
        }

        final AttributePropertyImpl that = (AttributePropertyImpl) o;

        return this.modifiers.equals(that.modifiers);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("value", this.value).append("finalValue", this.getFinalValue()).append("modifiers", this.modifiers).toString();
    }
}
