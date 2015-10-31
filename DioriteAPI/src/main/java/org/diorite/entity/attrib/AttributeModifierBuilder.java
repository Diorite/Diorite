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

package org.diorite.entity.attrib;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

/**
 * Attribute modifier builder, uuid, name and slot don't need to be set.
 */
public class AttributeModifierBuilder
{
    private UUID              uuid      = null;
    private String            name      = null;
    private double            value     = 0;
    private ModifierOperation operation = ModifierOperation.ADD_NUMBER;
    private ModifierSlot      slot      = null;
    private AttributeType     type      = null;

    protected AttributeModifierBuilder()
    {
    }

    /**
     * Copy all data from given modifier.
     *
     * @param modifier modifier to copy.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder copy(final AttributeModifier modifier)
    {
        this.uuid = modifier.getUuid();
        this.name = modifier.getName().orElse(null);
        this.value = modifier.getValue();
        this.operation = modifier.getOperation();
        this.slot = modifier.getModifierSlot();
        this.type = modifier.getType().orElse(null);
        return this;
    }

    /**
     * Set uuid of this modifier, builder will generate random uuid if null value is used.
     *
     * @param uuid uuid to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder setUuid(final UUID uuid)
    {
        this.uuid = uuid;
        return this;
    }

    /**
     * Set name of this modifier, may be null.
     *
     * @param name name to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder setName(final String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Set value of this modifier.
     *
     * @param value value to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder setValue(final double value)
    {
        this.value = value;
        return this;
    }

    /**
     * Set operation of this modifier, it can't be null.
     *
     * @param operation operation to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder setOperation(final ModifierOperation operation)
    {
        Validate.notNull(operation, "Operation can't be null.");
        this.operation = operation;
        return this;
    }

    /**
     * Set slot of this modifier, {@link ModifierSlot#NOT_SET} is used as default value.
     *
     * @param slot slot to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     */
    public AttributeModifierBuilder setSlot(final ModifierSlot slot)
    {
        this.slot = slot;
        return this;
    }

    /**
     * Set type of this modifier, may be null, needed for item modifiers.
     *
     * @param type type to set.
     *
     * @return this same instance of AttributeModifierBuilder for method chains.
     *
     * @see org.diorite.inventory.item.meta.ItemMeta#setAttributeModifiers(List, boolean)
     */
    public AttributeModifierBuilder setType(final AttributeType type)
    {
        this.type = type;
        return this;
    }

    /**
     * Build new modifier.
     *
     * @return created modifier.
     */
    public AttributeModifier build()
    {
        return new BasicAttributeModifier((this.uuid == null) ? UUID.randomUUID() : this.uuid, this.name, this.value, this.operation, (this.slot == null) ? ModifierSlot.NOT_SET : this.slot, this.type);
    }
}