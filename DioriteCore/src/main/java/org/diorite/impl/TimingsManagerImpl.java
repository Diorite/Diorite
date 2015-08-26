package org.diorite.impl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.EventType;
import org.diorite.utils.timings.TimingsManager;

public final class TimingsManagerImpl implements TimingsManager
{
    private boolean isCollectingData = true;

    @Override
    public void reset()
    {
        for (final EventType<?, ?> event : EventType.values())
        {
            event.getPipeline().getTimings().clear();
        }
    }

    @Override
    public void setCollecting(final boolean isCollecting)
    {
        this.isCollectingData = isCollecting;
    }

    @Override
    public boolean isCollecting()
    {
        return this.isCollectingData;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isCollectingData", this.isCollectingData).toString();
    }
}
