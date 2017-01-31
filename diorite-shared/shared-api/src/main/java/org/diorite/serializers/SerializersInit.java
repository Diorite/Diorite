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

import java.util.UUID;

import org.diorite.config.serialization.Serialization;
import org.diorite.gameprofile.GameProfile;
import org.diorite.gameprofile.internal.GameProfileImpl;
import org.diorite.gameprofile.internal.properties.PropertyImpl;

public final class SerializersInit
{
    private SerializersInit() {}

    public static void init()
    {
        Serialization global = Serialization.getInstance();
        global.registerSerializer(new PropertySerializer());
        global.registerSerializer(new GameProfileSerializer());
    }

    public static void main(String[] args)
    {
        GameProfileImpl test = new GameProfileImpl(UUID.randomUUID(), "test");
        test.getProperties().put("test", new PropertyImpl("test", "nah"));
        test.getProperties().put("test", new PropertyImpl("test", "nahhhh", "ugh"));
        Serialization global = Serialization.getInstance();
        System.out.println(global.toJson(test));
        System.out.println(global.fromJson(global.toJson(test), GameProfile.class).equals(test));
    }
}
