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

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;
import org.diorite.entity.attrib.ModifierSlot;
import org.diorite.nbt.NbtTagCompound;

/**
 * Additional implementation of attribute modifier used by core code.
 */
public class SimpleAttributeModifier implements AttributeModifier
{
    private final UUID              uuid;
    private final String            name;
    private final double            value;
    private final ModifierOperation operation;
    private final ModifierSlot      slot;
    private final AttributeType     type;

    /**
     * Construct new BasicAttributeModifier.
     *
     * @param uuid      may be null (random uuid will be used instead), uuid of modifier.
     * @param name      may be null, name of modifier.
     * @param value     value of modifier.
     * @param operation operation of modifier, can't be null.
     * @param slot      slot of modifier ({@link ModifierSlot#NOT_SET} will be used instead), may be null.
     * @param type      type of attribute that will be modified by this modifier, may be null.
     *
     * @see AttributeModifier#builder()
     */
    public SimpleAttributeModifier(final UUID uuid, final String name, final double value, final ModifierOperation operation, final ModifierSlot slot, final AttributeType type)
    {
        Validate.notNull(operation, "Operation can't be null.");
        this.uuid = (uuid == null) ? UUID.randomUUID() : uuid;
        this.name = name;
        this.value = value;
        this.operation = operation;
        this.slot = slot;
        this.type = type;
    }

    /**
     * Deserialize BasicAttributeModifier from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     */
    public SimpleAttributeModifier(final NbtTagCompound tag)
    {
        this.uuid = new UUID(tag.getLong("UUIDMost"), tag.getLong("UUIDLeast"));
        this.name = tag.getString("Name", (String) null);
        this.value = tag.getDouble("Amount");
        this.operation = ModifierOperation.getByEnumOrdinal(tag.getInt("Operation"));
        this.slot = ModifierSlot.getByTypeName(tag.getString("Slot", ""));
        this.type = tag.containsTag("AttributeName") ? AttributeType.getByKey(tag.getString("AttributeName")) : null;
    }

    @Override
    public Optional<AttributeType> getType()
    {
        return Optional.ofNullable(this.type);
    }

    @Override
    public UUID getUuid()
    {
        return this.uuid;
    }

    @Override
    public Optional<String> getName()
    {
        return Optional.ofNullable(this.name);
    }

    @Override
    public double getValue()
    {
        return this.value;
    }

    @Override
    public ModifierOperation getOperation()
    {
        return this.operation;
    }

    @Override
    public ModifierSlot getModifierSlot()
    {
        return (this.slot == null) ? ModifierSlot.NOT_SET : this.slot;
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound tag = new NbtTagCompound();
        tag.setLong("UUIDMost", this.uuid.getMostSignificantBits());
        tag.setLong("UUIDLeast", this.uuid.getLeastSignificantBits());
        if (this.name != null)
        {
            tag.setString("Name", this.name);
        }
        tag.setDouble("Amount", this.value);
        tag.setInt("Operation", this.operation.ordinal());
        if ((this.slot != null) && ! this.slot.equals(ModifierSlot.NOT_SET))
        {
            tag.setString("Slot", this.slot.getTypeName());
        }
        if (this.type != null)
        {
            tag.setString("AttributeName", this.type.getKey());
        }
        return tag;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof SimpleAttributeModifier))
        {
            return false;
        }

        final SimpleAttributeModifier that = (SimpleAttributeModifier) o;

        return (Double.compare(that.value, this.value) == 0) && this.uuid.equals(that.uuid) && ! ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null)) && this.operation.equals(that.operation) && this.getModifierSlot().equals(that.getModifierSlot()) && ! ((this.type != null) ? ! this.type.equals(that.type) : (that.type != null));
    }

    @Override
    public int hashCode()
    {
        int result;
        final long temp;
        result = this.uuid.hashCode();
        result = (31 * result) + ((this.name != null) ? this.name.hashCode() : 0);
        temp = Double.doubleToLongBits(this.value);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + this.operation.hashCode();
        result = (31 * result) + this.getModifierSlot().hashCode();
        result = (31 * result) + ((this.type != null) ? this.type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).append("name", this.name).append("value", this.value).append("operation", this.operation).append("slot", this.getModifierSlot()).append("type", this.type).toString();
    }
}
