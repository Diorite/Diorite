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

package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.utils.collections.arrays.ReflectArrayIterator;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SimpleArrayTemplateElement<T> extends TemplateElement<T>
{
    /**
     * Instance of boolean[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<boolean[]> INSTANCE_BOOLEANS = new SimpleArrayTemplateElement<>(boolean[].class);

    /**
     * Instance of char[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<char[]> INSTANCE_CHARS = new SimpleArrayTemplateElement<>(char[].class);

    /**
     * Instance of byte[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<byte[]> INSTANCE_BYTES = new SimpleArrayTemplateElement<>(byte[].class);

    /**
     * Instance of short[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<short[]> INSTANCE_SHORTS = new SimpleArrayTemplateElement<>(short[].class);

    /**
     * Instance of int[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<int[]> INSTANCE_INTS = new SimpleArrayTemplateElement<>(int[].class);

    /**
     * Instance of long[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<long[]> INSTANCE_LONGS = new SimpleArrayTemplateElement<>(long[].class);

    /**
     * Instance of float[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<float[]> INSTANCE_FLOATS = new SimpleArrayTemplateElement<>(float[].class);

    /**
     * Instance of double[] template to direct-use.
     */
    public static final SimpleArrayTemplateElement<double[]> INSTANCE_DOUBLES = new SimpleArrayTemplateElement<>(double[].class);

    /**
     * Construct new array template handler for given class.
     *
     * @param fieldType      type of template element, should be array type.
     * @param function       function used to convert other types to this type (may throw errors)
     * @param classPredicate returns true for classes that can be converted into supproted type.
     */
    public SimpleArrayTemplateElement(final Class<T> fieldType, final Function<Object, T> function, final Predicate<Class<?>> classPredicate)
    {
        super(fieldType, function, classPredicate);
    }

    /**
     * Construct new array template handler for given class.
     *
     * @param clazz type of template element, should be array type.
     */
    public SimpleArrayTemplateElement(final Class<T> clazz)
    {
        super(clazz, obj -> {
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to " + clazz.getSimpleName() + ": " + obj);
        }, c -> false);
    }

    @Override
    protected T convertDefault0(final Object def, final Class<?> fieldType)
    {
        final Class<?> c = def.getClass();
        if (fieldType.isAssignableFrom(c))
        {
            return (T) def;
        }
        final Iterator it;
        final int size;
        if (c.isArray())
        {
            it = new ReflectArrayIterator(def);
            size = Array.getLength(def);
        }
        else if (def instanceof Iterator)
        {
            it = (Iterator) def;
            size = Iterators.size(it);
        }
        else if (def instanceof Iterable)
        {
            it = ((Iterable) def).iterator();
            size = Iterables.size((Iterable) def);
        }
        else
        {
            throw new UnsupportedOperationException("Can't convert default value (" + c.getName() + "): " + def);
        }
        final T array = (T) Array.newInstance(fieldType.getComponentType(), size);
        int i = 0;
        while (it.hasNext())
        {
            final Object obj = it.next();
            if (obj == null)
            {
                Array.set(array, i++, null);
                continue;
            }
            if (fieldType.getComponentType().isAssignableFrom(obj.getClass()))
            {
                Array.set(array, i++, obj);
                continue;
            }
            Array.set(array, i++, TemplateElements.getElement(fieldType.getComponentType()).convertDefault(obj, fieldType.getComponentType()));
        }
        return array;
    }


    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object object, final int level, final ElementPlace elementPlace) throws IOException
    {
        IterableTemplateElement.INSTANCE.appendValue(writer, field, source, new ReflectArrayIterator(object), level, elementPlace);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("function", this.function).toString();
    }
}
