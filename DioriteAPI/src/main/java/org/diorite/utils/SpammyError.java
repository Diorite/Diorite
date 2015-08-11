package org.diorite.utils;

import java.util.concurrent.TimeUnit;

import gnu.trove.map.hash.TObjectLongHashMap;

/**
 * Small utility class useful when we have something to log, that can be invoked very often and we don't want make spam in output. <br>
 * Like log it only once per few minutes.
 */
@SuppressWarnings("MagicNumber")
public final class SpammyError
{
    private static final TObjectLongHashMap<Object> errors = new TObjectLongHashMap<>(10, 0.1f, 0);

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
        final long nextTime = errors.get(key) + TimeUnit.SECONDS.toMillis(secondsBetweenLogs);
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
        final long nextTime = errors.get(key) + TimeUnit.SECONDS.toMillis(secondsBetweenLogs);
        if (currentTime >= nextTime)
        {
            System.out.println(message);
            errors.put(key, currentTime);
        }
    }
}
