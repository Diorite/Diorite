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

package org.diorite.impl.log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "Queue", category = "Core", elementType = "appender", printObject = true)
public class QueueLogAppender extends AbstractAppender
{
    private static final int                                MAX_CAPACITY     = 250;
    private static final Map<String, BlockingQueue<String>> QUEUES           = new HashMap<>(10);
    private static final ReadWriteLock                      QUEUE_LOCK       = new ReentrantReadWriteLock();
    private static final long                               serialVersionUID = 0;
    private final BlockingQueue<String> queue;

    public QueueLogAppender(final String name, final Filter filter, final Layout<? extends Serializable> layout, final boolean ignoreExceptions, final BlockingQueue<String> queue)
    {
        super(name, filter, layout, ignoreExceptions);
        this.queue = queue;
    }

    @Override
    public void append(final LogEvent event)
    {
        if (this.queue.size() >= MAX_CAPACITY)
        {
            this.queue.clear();
        }
        this.queue.add(this.getLayout().toSerializable(event).toString());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("queue", this.queue).toString();
    }

    @PluginFactory
    public static QueueLogAppender createAppender(@PluginAttribute("name") final String name, @PluginAttribute("ignoreExceptions") final String ignore, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filters") final Filter filter, @PluginAttribute("target") String target)
    {
        final boolean ignoreExceptions = Boolean.parseBoolean(ignore);
        if (name == null)
        {
            LOGGER.error("No name provided for QueueLogAppender");
            return null;
        }
        if (target == null)
        {
            target = name;
        }
        QUEUE_LOCK.writeLock().lock();
        BlockingQueue<String> queue = QUEUES.get(target);
        if (queue == null)
        {
            queue = new LinkedBlockingQueue<>();
            QUEUES.put(target, queue);
        }
        QUEUE_LOCK.writeLock().unlock();
        if (layout == null)
        {
            layout = PatternLayout.createLayout(null, null, null, null, false, false, null, null);
        }
        return new QueueLogAppender(name, filter, layout, ignoreExceptions, queue);
    }

    public static String getNextLogEvent(final String queueName)
    {
        QUEUE_LOCK.readLock().lock();
        final BlockingQueue<String> queue = QUEUES.get(queueName);
        QUEUE_LOCK.readLock().unlock();
        if (queue != null)
        {
            try
            {
                return queue.take();
            } catch (final InterruptedException ignored)
            {
            }
        }
        return null;
    }
}
