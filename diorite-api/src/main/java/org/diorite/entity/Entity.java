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

package org.diorite.entity;

import javax.vecmath.Vector3f;

import java.util.Collection;
import java.util.function.Predicate;

import org.diorite.Core;
import org.diorite.GameObject;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.entity.pathfinder.EntityController;
import org.diorite.scheduler.Synchronizable;
import org.diorite.utils.math.geometry.LookupShape;
import org.diorite.world.World;

public interface Entity extends GameObject, Synchronizable
{
    EntityType getType();

    Vector3f getVelocity();

    boolean exist();

    int getId();

    int getAge();

    void setAge(int age);

    double getHealth();

    void setHealth(double health);

    double getX();

    double getZ();

    double getY();

    float getYaw();

    float getPitch();

    World getWorld();

    ImmutableLocation getLocation();

    Core getCore();

    Collection<? extends Entity> getNearbyEntities(final double x, final double y, final double z, final LookupShape shape);

    Collection<? extends Entity> getNearbyEntities(final double x, final double y, final double z, final EntityType type, final LookupShape shape);

    <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Class<? extends T> type, final LookupShape shape);

    <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Class<? extends T> type, final Predicate<Entity> predicate);

    <T extends Entity> Collection<? extends T> getNearbyEntities(double x, double y, double z, Predicate<Entity> predicate);

    void setVelocity(Vector3f velocity);

    boolean isAiEnabled();

    void setAiEnabled(boolean aiEnabled);

    boolean isOnFire();

    void setOnFire(boolean onFire);

    boolean isCrouching();

    void setCrouching(boolean crouching);

    boolean isSprinting();

    void setSprinting(boolean sprinting);

    boolean hasActionFlag();

    void setActionFlag(boolean eating);

    boolean isInvisible();

    void setInvisible(boolean invisible);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    int getAir();

    void setAir(int air);

    String getCustomName();

    void setCustomName(String name);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean visible);

    /**
     * If true, this entity will not make sound.
     *
     * @return
     */
    boolean isSilent();

    void setSilent(boolean silent);

    float getHeadPitch();

    void teleport(ILocation location); //TODO: left by skprime to check by GoToFinal

    default void teleport(final Entity entity)
    {
        this.teleport(entity.getLocation());
    }

    EntityController getEntityController();
}
