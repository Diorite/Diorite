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

package org.diorite.serializers;

import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;
import org.diorite.config.serialization.Serializer;
import org.diorite.gameprofile.Property;
import org.diorite.gameprofile.internal.properties.PropertyImpl;

/**
 * String serializer of {@link Property}.
 */
public class PropertySerializer implements Serializer<Property>
{
    @Override
    public Class<? super Property> getType()
    {
        return Property.class;
    }

    @Override
    public void serialize(Property object, SerializationData data)
    {
        data.add("name", object.getName());
        data.add("value", object.getValue());
        if (object.hasSignature())
        {
            data.add("signature", object.getSignature());
        }
    }

    @Override
    public Property deserialize(DeserializationData data)
    {
        String name = data.getOrThrow("name", String.class);
        String value = data.getOrThrow("value", String.class);
        String signature = data.get("signature", String.class);
        return new PropertyImpl(name, value, signature);
    }

}
