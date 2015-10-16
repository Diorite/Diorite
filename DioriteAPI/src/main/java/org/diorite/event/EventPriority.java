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

package org.diorite.event;

import org.diorite.event.pipelines.EventPipeline;

/**
 * Event priority used in {@link EventPipeline}
 * <p>
 * Higher priority means that this action will be performed later, after
 * actions with lower priority.
 * <p>
 * LOWEST {@literal ->} LOWER {@literal ->} LOW {@literal ->} BELOW_NORMAL {@literal ->} NORMAL {@literal ->} ABOVE_NORMAL {@literal ->} HIGH {@literal ->} HIGHER {@literal ->} HIGHEST
 */
public enum EventPriority
{
    HIGHEST("*EventPriority_HIGHEST*"),
    HIGHER("*EventPriority_HIGHER*"),
    HIGH("*EventPriority_HIGH*"),
    ABOVE_NORMAL("*EventPriority_ABOVE_NORMAL*"),
    NORMAL("*EventPriority_NORMAL*"),
    BELOW_NORMAL("*EventPriority_BELOW_NORMAL*"),
    LOW("*EventPriority_LOW*"),
    LOWER("*EventPriority_LOWER*"),
    LOWEST("*EventPriority_LOWEST*");

    private final String pipelineName;

    EventPriority(final String pipelineName)
    {
        this.pipelineName = pipelineName;
    }

    /**
     * @return name used in pipelines.
     */
    public String getPipelineName()
    {
        return this.pipelineName;
    }

    /**
     * Check if this priority is higher than given one.
     *
     * @param other other priority.
     *
     * @return true if this priority is higher.
     */
    public boolean isHigherThan(final EventPriority other)
    {
        return this.ordinal() > other.ordinal();
    }

    /**
     * Check if this priority is lower than given one.
     *
     * @param other other priority.
     *
     * @return true if this priority is lower.
     */
    public boolean isLowerThan(final EventPriority other)
    {
        return this.ordinal() < other.ordinal();
    }

    /**
     * @return higher priority than current one or this same if it is the highest one.
     */
    public EventPriority getHigher()
    {
        switch (this)
        {
            case HIGHER:
                return HIGHEST;
            case HIGH:
                return HIGHER;
            case ABOVE_NORMAL:
                return HIGH;
            case NORMAL:
                return HIGH;
            case BELOW_NORMAL:
                return NORMAL;
            case LOW:
                return BELOW_NORMAL;
            case LOWER:
                return LOW;
            case LOWEST:
                return LOWER;
            case HIGHEST:
            default:
                return this;
        }
    }

    /**
     * @return lower priority than current one or this same if it is the lowest one.
     */
    public EventPriority getLower()
    {
        switch (this)
        {
            case HIGHEST:
                return HIGHER;
            case HIGHER:
                return HIGH;
            case HIGH:
                return ABOVE_NORMAL;
            case ABOVE_NORMAL:
                return NORMAL;
            case NORMAL:
                return BELOW_NORMAL;
            case BELOW_NORMAL:
                return LOW;
            case LOW:
                return LOWER;
            case LOWER:
                return LOWEST;
            case LOWEST:
            default:
                return this;
        }
    }
}
