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

package org.diorite.commons;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Basic thread utilities.
 */
public final class DioriteThreadUtils
{
    private DioriteThreadUtils()
    {
    }

    /**
     * Returns full thread name, prefixed by all thread groups using : as separator.
     *
     * @param thread
     *         thread to get full name of.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread)
    {
        return getFullThreadName(thread, ":");
    }

    /**
     * Returns full thread name, prefixed by all thread groups using : as separator.
     *
     * @param thread
     *         thread to get full name of.
     * @param skipSystem
     *         skip first system thread group.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread, boolean skipSystem)
    {
        return getFullThreadName(thread, ":", skipSystem);
    }

    /**
     * Returns full thread name, prefixed by all thread groups using given string as separator.
     *
     * @param thread
     *         thread to get full name of.
     * @param sep
     *         separator to use.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread, String sep)
    {
        return getFullThreadName(thread, sep, false);
    }

    /**
     * Returns full thread name, prefixed by all thread groups using given string as separator.
     *
     * @param thread
     *         thread to get full name of.
     * @param sep
     *         separator to use.
     * @param skipSystem
     *         skip first system thread group.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread, String sep, boolean skipSystem)
    {
        StringBuilder sb = new StringBuilder(128);
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add(thread.getName());
        ThreadGroup threadGroup = thread.getThreadGroup();
        while (threadGroup != null)
        {
            linkedList.add(threadGroup.getName());
            threadGroup = threadGroup.getParent();
        }
        Iterator<String> stringIterator = linkedList.descendingIterator();
        if (skipSystem)
        {
            stringIterator.next();
        }
        sb.append(stringIterator.next());
        while (stringIterator.hasNext())
        {
            sb.append(sep);
            sb.append(stringIterator.next());
        }
        return sb.toString();
    }

    /**
     * Returns full thread name, prefixed by all thread groups using given char as separator.
     *
     * @param thread
     *         thread to get full name of.
     * @param sep
     *         separator to use.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread, char sep)
    {
        return getFullThreadName(thread, Character.toString(sep), false);
    }

    /**
     * Returns full thread name, prefixed by all thread groups using given char as separator.
     *
     * @param thread
     *         thread to get full name of.
     * @param sep
     *         separator to use.
     * @param skipSystem
     *         skip first system thread group.
     *
     * @return full thread name.
     */
    public static String getFullThreadName(Thread thread, char sep, boolean skipSystem)
    {
        return getFullThreadName(thread, Character.toString(sep), skipSystem);
    }
}
