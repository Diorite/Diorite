package org.diorite.utils.timings;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class TimingsContainer // TODO
{
    private final String name;
    private long latestTime;
    private long avarageTime;

    public TimingsContainer(final String name)
    {
        this.name = name;
    }

    public void recordTiming(final long time)
    {
        this.latestTime = time;
        this.avarageTime = (this.avarageTime + this.latestTime) / 2;
    }

    public long getLatestTime()
    {
        return this.latestTime;
    }

    public long getAvarageTime()
    {
        return this.avarageTime;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("latestTime", this.latestTime).append("avarageTime", this.avarageTime).toString();
    }
}
