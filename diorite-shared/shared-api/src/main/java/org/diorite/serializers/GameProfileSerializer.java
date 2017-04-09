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

import java.util.List;
import java.util.UUID;

import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;
import org.diorite.config.serialization.Serializer;
import org.diorite.gameprofile.GameProfile;
import org.diorite.gameprofile.Property;
import org.diorite.gameprofile.PropertyMap;
import org.diorite.gameprofile.internal.GameProfileImpl;

/**
 * String serializer of {@link GameProfile}.
 */
public class GameProfileSerializer implements Serializer<GameProfile>
{
    @Override
    public Class<? super GameProfile> getType()
    {
        return GameProfile.class;
    }

    @Override
    public void serialize(GameProfile object, SerializationData data)
    {
        data.add("id", object.getId());
        data.add("name", object.getName());
        PropertyMap properties = object.getProperties();
        if (! properties.isEmpty())
        {
            data.addCollection("properties", properties.values(), Property.class);
        }
    }

    @Override
    public GameProfile deserialize(DeserializationData data)
    {
        UUID id = data.get("id", UUID.class);
        String name = data.get("name", String.class);
        GameProfileImpl gameProfile = new GameProfileImpl(id, name);
        List<Property> properties = data.getAsList("properties", Property.class);
        for (Property property : properties)
        {
            gameProfile.getProperties().put(property.getName(), property);
        }
        return gameProfile;
    }

}
