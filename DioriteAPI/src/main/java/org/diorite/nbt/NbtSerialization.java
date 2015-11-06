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

package org.diorite.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;

/**
 * Handle classes implementing {@link NbtSerializable} allowing construct nbt element without access to constructors, like in interfaces etc.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class NbtSerialization
{
    private static final Map<Class, Function<NbtTagCompound, Object>> tag     = new HashMap<>(20);
    private static final Map<Class, Class>                            aliases = new HashMap<>(30);

    private NbtSerialization()
    {
    }

    /**
     * Register new deserializer for given class.
     *
     * @param function function of deserializer.
     * @param clazz    main type of deserializer.
     * @param clazz2   alias types of deserializer.
     * @param <T>      type of nbt serializable.
     */
    public static <T extends NbtSerializable> void register(final Function<NbtTagCompound, T> function, final Class<T> clazz, final Class<? extends T>... clazz2)
    {
        Validate.isTrue(NbtSerializable.class.isAssignableFrom(clazz), "Class must implements NbtSerializable");
        if (tag.containsKey(clazz))
        {
            aliases.entrySet().removeIf(e -> e.getValue().equals(clazz));
        }
        tag.put(clazz, (Function) function);
        for (final Class<? extends T> tClass : clazz2)
        {
            aliases.put(tClass, clazz);
        }
    }

    /**
     * Register new deserializer for given class.
     *
     * @param function function of deserializer.
     * @param clazz    main type of deserializer.
     * @param <T>      type of nbt serializable.
     */
    public static <T extends NbtSerializable> void register(final Function<NbtTagCompound, T> function, final Class<T> clazz)
    {
        Validate.isTrue(NbtSerializable.class.isAssignableFrom(clazz), "Class must implements NbtSerializable");
        if (tag.containsKey(clazz))
        {
            aliases.entrySet().removeIf(e -> e.getValue().equals(clazz));
        }
        tag.put(clazz, (Function) function);
    }

    /**
     * Deserialize given data to object of given type.
     *
     * @param clazz type of object to deserialize.
     * @param data  data of object.
     * @param <T>   type of nbt serializable to deserialize.
     *
     * @return deserialized object or null if it isn't registered.
     */
    public static <T extends NbtSerializable> T deserialize(final Class<T> clazz, final NbtTagCompound data)
    {
        Function<NbtTagCompound, Object> f = tag.get(clazz);
        if (f == null)
        {
            final Class c = aliases.get(clazz);
            if (c == null)
            {
                return null;
            }
            f = tag.get(c);
            if (f == null)
            {
                return null;
            }
        }
        return (T) f.apply(data);
    }

    /**
     * Checks if given class is registered nbt serializable.
     *
     * @param clazz class to be checked.
     *
     * @return true if given class is registered nbt serializable.
     */
    public static boolean isRegistered(final Class<? extends NbtSerializable> clazz)
    {
        final Class c;
        return tag.containsKey(clazz) || ((((c = aliases.get(clazz))) != null) && tag.containsKey(c));
    }
}
