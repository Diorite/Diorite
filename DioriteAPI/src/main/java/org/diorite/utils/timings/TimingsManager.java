package org.diorite.utils.timings;

public interface TimingsManager
{
    void reset();

    void setCollecting(boolean isCollecting);

    boolean isCollecting();
}
