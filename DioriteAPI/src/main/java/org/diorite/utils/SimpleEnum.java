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

package org.diorite.utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.reflections.DioriteReflectionUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Simple interface-based enum, that can be edited in runtime. <br>
 * You should use {@link org.diorite.utils.SimpleEnum.ASimpleEnum} as super class where possible. <br>
 * But you should use this class when casting, or as variable type. <br>
 * <p>
 * Every simple enum must contains static methods: <br>
 * void register(T) <br>
 * T[] values() <br>
 * T getByEnumName(String) <br>
 * T getByEnumOrdinal(int)
 *
 * @param <T> type of values.
 */
public interface SimpleEnum<T extends SimpleEnum<T>>
{
    float SMALL_LOAD_FACTOR = .1f;

    String name();

    int ordinal();

    T byOrdinal(int ordinal);

    T byName(String name);

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name      name of enum field (ignore-case)
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, id, enumClass);
    }

    /**
     * Safe method to get one of enum values by name or ordinal,
     * use -1 as id, to use only name,
     * and null or empty string for name, to use only ordinal id.
     *
     * @param name name of enum field (ignore-case)
     * @param id   ordinal id.
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final int id, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, id, def);
    }

    /**
     * Safe method to get one of enum values by ordinal.
     *
     * @param id        ordinal id.
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final int id, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(null, id, enumClass);
    }

    /**
     * Safe method to get one of enum values by ordinal.
     *
     * @param id  ordinal id.
     * @param def default value, can't be null.
     * @param <T> type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final int id, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(null, id, def);
    }

    /**
     * Safe method to get one of enum values by name.
     *
     * @param name      name of enum field (ignore-case)
     * @param enumClass class of enum.
     * @param <T>       type of enum.
     *
     * @return enum element or null.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final Class<T> enumClass)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, - 1, enumClass);
    }

    /**
     * Safe method to get one of enum values by name.
     *
     * @param name name of enum field (ignore-case)
     * @param def  default value, can't be null.
     * @param <T>  type of enum.
     *
     * @return enum element or def.
     */
    static <T extends SimpleEnum<T>> T getSimpleEnumValueSafe(final String name, final T def)
    {
        return DioriteReflectionUtils.getSimpleEnumValueSafe(name, - 1, def);
    }

    @SuppressWarnings("ObjectEquality")
    static boolean equals(final SimpleEnum<?> a, final SimpleEnum<?> b)
    {
        return (a == b) || (((a != null) && (b != null)) && (a.ordinal() == b.ordinal()));
    }

    /**
     * You should use this class as super class instead of {@link SimpleEnum} where possible. <br>
     * But you should NOT use this class for variable types, casting etc... <br>
     * <p>
     * This class contains default implementations of equals, hashCode and toString methods.
     *
     * @param <T> type of enum.
     */
    @SuppressWarnings("unchecked")
    abstract class ASimpleEnum<T extends SimpleEnum<T>> implements SimpleEnum<T>
    {
        private static final Map<Class<?>, AtomicInteger>                ids       = new HashMap<>(40);
        private static final Map<Class<?>, Map<String, SimpleEnum<?>>>   byName    = new HashMap<>(40);
        private static final Map<Class<?>, TIntObjectMap<SimpleEnum<?>>> byOrdinal = new HashMap<>(40);

        protected final String enumName;
        protected final int    ordinal;

        protected ASimpleEnum(final String enumName, final int ordinal)
        {
            this.enumName = enumName;
            this.ordinal = ordinal;
        }

        protected ASimpleEnum(final String enumName)
        {
            this.enumName = enumName;
            AtomicInteger i = ids.get(this.getClass());
            if (i == null)
            {
                i = new AtomicInteger();
                ids.put(this.getClass(), i);
            }
            this.ordinal = i.getAndIncrement();
        }

        @Override
        public T byOrdinal(final int ordinal)
        {
            return (T) getByEnumOrdinal(this.getClass(), ordinal);
        }

        @Override
        public T byName(final String name)
        {
            return (T) getByEnumName(this.getClass(), name);
        }

        @Override
        public int ordinal()
        {
            return this.ordinal;
        }

        @Override
        public String name()
        {
            return this.enumName;
        }

        @Override
        public String toString()
        {
            return this.enumName;
        }

        @Override
        public boolean equals(final Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (! (o instanceof SimpleEnum))
            {
                return false;
            }

            final SimpleEnum<?> that = (SimpleEnum<?>) o;
            return this.ordinal == that.ordinal();
        }

        @Override
        public int hashCode()
        {
            return this.ordinal;
        }

        protected static <T extends SimpleEnum<?>> T getByEnumName(final Class<T> clazz, final String name)
        {
            return (T) byName.get(clazz).get(name);
        }

        protected static <T extends SimpleEnum<?>> T getByEnumOrdinal(final Class<T> clazz, final int id)
        {
            return (T) byOrdinal.get(clazz).get(id);
        }

        protected static void register(final Class<?> clazz, final SimpleEnum<?> e)
        {
            final Entry<Map<String, SimpleEnum<?>>, TIntObjectMap<SimpleEnum<?>>> maps = init(clazz, 10);
            maps.getKey().put(e.name(), e);
            maps.getValue().put(e.ordinal(), e);
        }

        protected static Map<String, SimpleEnum<?>> getByEnumName(final Class<?> clazz)
        {
            return byName.get(clazz);
        }

        protected static TIntObjectMap<SimpleEnum<?>> getByEnumOrdinal(final Class<?> clazz)
        {
            return byOrdinal.get(clazz);
        }

        protected static Entry<Map<String, SimpleEnum<?>>, TIntObjectMap<SimpleEnum<?>>> init(final Class<?> clazz, final int size)
        {
            Map<String, SimpleEnum<?>> byName = ASimpleEnum.byName.get(clazz);
            if (byName == null)
            {
                byName = new CaseInsensitiveMap<>(size, SMALL_LOAD_FACTOR);
                ASimpleEnum.byName.put(clazz, byName);
            }
            TIntObjectMap<SimpleEnum<?>> byID = ASimpleEnum.byOrdinal.get(clazz);
            if (byID == null)
            {
                byID = new TIntObjectHashMap<>(size, SMALL_LOAD_FACTOR);
                ASimpleEnum.byOrdinal.put(clazz, byID);
            }
            return new SimpleEntry<>(byName, byID);
        }

    }
}
