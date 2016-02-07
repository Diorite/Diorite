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

import java.util.concurrent.TimeUnit;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

/**
 * Small utility class useful when we have something to log, that can be invoked very often and we don't want make spam in output. <br>
 * Like log it only once per few minutes.
 */
public final class SpammyError
{
    private static final Object2LongMap<Object> errors = new Object2LongOpenHashMap<>(10, 0.1f);

    static
    {
        errors.defaultReturnValue(0);
    }

    private SpammyError()
    {
    }

    /**
     * Print message to {@link System#err} if last message with this same key wasn't printed less than minimum time between messages.
     *
     * @param message            message to print.
     * @param secondsBetweenLogs minimum time between messages in seconds.
     * @param key                key to store last time of message.
     */
    public static void err(final String message, final int secondsBetweenLogs, final Object key)
    {
        final long currentTime = System.currentTimeMillis();
        final long nextTime = errors.getLong(key) + TimeUnit.SECONDS.toMillis(secondsBetweenLogs);
        if (currentTime >= nextTime)
        {
            System.err.println(message);
            errors.put(key, currentTime);
        }
    }

    /**
     * Print message to {@link System#out} if last message with this same key wasn't printed less than minimum time between messages.
     *
     * @param message            message to print.
     * @param secondsBetweenLogs minimum time between messages in seconds.
     * @param key                key to store last time of message.
     */
    public static void out(final String message, final int secondsBetweenLogs, final Object key)
    {
        final long currentTime = System.currentTimeMillis();
        final long nextTime = errors.getLong(key) + TimeUnit.SECONDS.toMillis(secondsBetweenLogs);
        if (currentTime >= nextTime)
        {
            System.out.println(message);
            errors.put(key, currentTime);
        }
    }
}
