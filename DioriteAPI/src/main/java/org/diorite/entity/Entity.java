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

package org.diorite.entity;

import java.util.Collection;

import org.diorite.Core;
import org.diorite.GameObject;
import org.diorite.ImmutableLocation;
import org.diorite.scheduler.Synchronizable;

public interface Entity extends GameObject, Synchronizable
{
    EntityType getType();

    float getVelocityX();

    float getVelocityY();

    float getVelocityZ();

    boolean exist();

    int getId();

    double getX();

    double getZ();

    double getY();

    ImmutableLocation getLocation();

    Core getCore();

    Collection<? extends Entity> getNearbyEntities(double x, double y, double z);

    <T extends Entity> Collection<? extends T> getNearbyEntities(double x, double y, double z, Class<? extends T> type);

    <T extends Entity> Collection<? extends T> getNearbyEntities(double x, double y, double z, EntityType type);

    default int getMcId()
    {
        return this.getType().getMinecraftId();
    }
}
