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

package org.diorite.entity.pathfinder;

import java.util.Optional;
import java.util.function.Consumer;

import org.diorite.ILocation;
import org.diorite.entity.Entity;

public interface PathfinderService<T extends Pathfinder>
{
    /**
     * That method will create new Pathfinder instance. <br />
     * Calculations should not be here performed!
     *
     * @param entity   Entity what will go
     * @param goal     The point what will be reached by entity
     * @param callback you should send here results of your calculations. <br />
     *                 Entity will intermeditaly go on this Path.
     *                 If you send here empty optional Pathfinding will
     *                 be cancelled.
     *
     * @return pathfinder instance
     */
    T initPathfinding(Entity entity, ILocation goal, Consumer<Optional<Path>> callback);

    /**
     * That method is invoked every tick and signals that pathfinder
     * will do calculations. <br />
     * If you're developing asynchronously service, you can take into account only
     * first call of this method for specific Pathfinder.
     *
     * @param pathfinder Pathfinder that will do calculations
     */
    void processPathfinding(T pathfinder);

    /**
     * This method is invoked when you pathfinder reports that it's still active
     * and entity's target is changed or navigation is cancelled. <br />
     * This method is especially useful when you're developing asynchronously service.
     *
     * @param pathfinder Pathfinder that will be stopped
     */
    default void cancelPathfinding(final T pathfinder)
    {
    }
}
