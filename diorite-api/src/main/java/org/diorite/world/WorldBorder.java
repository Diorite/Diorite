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

package org.diorite.world;

import org.diorite.ILocation;

public interface WorldBorder
{
    World getWorld();

    State getWorldBorderState();

    void reset();

    ILocation getCenter();

    void setCenter(double x, double z);

    double getSize();

    void setSize(double size);

    void setSize(double size, long ticks);

    double getStartSize();

    double getTargetSize();

    long getTargetSizeReachTime();

    int getWarningDistance();

    void setWarningDistance(int warningDistance);

    int getWarningTime();

    void setWarningTime(int warningTime);

    double getDamageAmount();

    void setDamageAmount(double damageAmount);

    double getDamageBuffer();

    void setDamageBuffer(double damageBuffer);

    int getPortalTeleportBoundary();

    void setPortalTeleportBoundary(int portalTeleportBoundary);

    default void setCenter(final ILocation location)
    {
        this.setCenter(location.getX(), location.getZ());
    }

    enum State
    {
        GROWING, DECREASING, STATIONARY;
    }
}
