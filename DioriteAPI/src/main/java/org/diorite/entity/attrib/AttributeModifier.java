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

import java.util.Optional;
import java.util.UUID;

import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtTagCompound;

/**
 * Represent attribute modifier that have uuid, value and type.
 */
public interface AttributeModifier extends NbtSerializable
{
    /**
     * Increment X by Value. <br>
     * The game first sets X = Base, then executes all Operation 0 modifiers, then sets Y = X, then executes all Operation 1 modifiers, and finally executes all Operation 2 modifiers.
     */
    byte OPERATION_ADD_NUMBER          = 0;
    /**
     * Increment Y by X * Value. <br>
     * The game first sets X = Base, then executes all Operation 0 modifiers, then sets Y = X, then executes all Operation 1 modifiers, and finally executes all Operation 2 modifiers.
     */
    byte OPERATION_MULTIPLY_PERCENTAGE = 1;
    /**
     * Y = Y * (1 + Amount) (equivalent to Increment Y by Y * Amount). <br>
     * The game first sets X = Base, then executes all Operation 0 modifiers, then sets Y = X, then executes all Operation 1 modifiers, and finally executes all Operation 2 modifiers.
     */
    byte OPERATION_ADD_PERCENTAGE      = 2;

    /**
     * Returns type of attribute that will be modified by this modifier. <br>
     * This field is only needed in item modifiers.
     *
     * @return type of attribute that will be modified by this modifier.
     */
    default Optional<AttributeType> getType()
    {
        return Optional.empty();
    }

    /**
     * Returns uuid of this modifier.
     *
     * @return uuid of this modifier.
     */
    UUID getUuid();

    /**
     * Returns name of this modifier.
     *
     * @return name of this modifier.
     */
    default Optional<String> getName()
    {
        return Optional.empty();
    }

    /**
     * Returns value of this modifier.
     *
     * @return value of this modifier.
     */
    double getValue();

    /**
     * Returns operation type of this modifier.
     *
     * @return operation type of this modifier.
     */
    ModifierOperation getOperation();

    /**
     * Returns slot where item needs to be hold for this modifier. <br>
     * Returns {@link ModifierSlot#NOT_SET} if this isn't item modifier.
     *
     * @return slot where item needs to be hold for this modifier.
     */
    default ModifierSlot getModifierSlot()
    {
        return ModifierSlot.NOT_SET;
    }

    /**
     * Deserialize AttributeModifier from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     *
     * @return deserialized AttributeModifier.
     */
    static AttributeModifier fromNbt(final NbtTagCompound tag)
    {
        return new BasicAttributeModifier(tag);
    }

    /**
     * Construct new attribute modifier using builder.
     *
     * @return builder of attribute.
     */
    static AttributeModifierBuilder builder()
    {
        return new AttributeModifierBuilder();
    }
}
