/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import sun.misc.Unsafe;

public final class DioriteUtils
{
    private static final Unsafe unsafeInstance;

    private DioriteUtils()
    {
    }

    static
    {
        try
        {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafeInstance = (Unsafe) field.get(null);
        } catch (final Throwable e)
        {
            throw new Error("Can't find unsafe instance.", e);
        }
    }

    /**
     * Returns size of iterator, method will use iterator.
     *
     * @param iterator iterator to be used.
     *
     * @return size of iterator, method will use iterator.
     */
    public static int size(final Iterator<?> iterator)
    {
        int count;
        for (count = 0; iterator.hasNext(); ++ count)
        {
            iterator.next();
        }

        return count;
    }

    /**
     * Returns size of iterable.
     *
     * @param iterable iterable to get size of.
     *
     * @return size of iterable.
     */
    public static int size(final Iterable<?> iterable)
    {
        return (iterable instanceof Collection) ? ((Collection<?>) iterable).size() : size(iterable.iterator());
    }

    /**
     * Don't ise that if you don't know what {@link Unsafe} is.
     *
     * @return unsafe instance.
     */
    public static Unsafe getUnsafe()
    {
        return unsafeInstance;
    }

    /**
     * Create file directory, and then create file.
     *
     * @param file file to create.
     *
     * @throws IOException from {@link File#createNewFile()}
     */
    public static void createFile(final File file) throws IOException
    {
        if (file.exists())
        {
            return;
        }
        file.getAbsoluteFile().getParentFile().mkdirs();
        file.createNewFile();
    }

    public static UUID getCrackedUuid(final String nick)
    {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + nick).getBytes(StandardCharsets.UTF_8));
    }

}
