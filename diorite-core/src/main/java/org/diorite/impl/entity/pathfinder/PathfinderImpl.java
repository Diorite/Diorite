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

package org.diorite.impl.entity.pathfinder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.diorite.impl.CoreMain;
import org.diorite.impl.entity.IEntity;
import org.diorite.BlockLocation;
import org.diorite.ILocation;
import org.diorite.entity.Entity;
import org.diorite.entity.pathfinder.Path;
import org.diorite.entity.pathfinder.Pathfinder;

public class PathfinderImpl implements Pathfinder
{
    private final BlockLocation  goal;
    private final IEntity        entity;
    private final Consumer<Path> callback;

    private boolean       isCompleted;
    private BlockLocation currentBlock;

    public PathfinderImpl(final ILocation goal, final IEntity entity, final Consumer<Path> callback)
    {
        this.goal = goal.toBlockLocation();
        this.entity = entity;
        this.callback = callback;
        this.currentBlock = entity.getLocation().toBlockLocation();
    }

    @Override
    public Entity getEntity()
    {
        return this.entity;
    }

    @Override
    public boolean isCompleted()
    {
        return this.isCompleted;
    }

    private double calculateNear(final BlockLocation loc1, final BlockLocation loc2)
    {
        return loc1.distanceSquared(loc2);
    }

    private boolean isMoveOk(final BlockLocation blockLocation)
    {
        if (blockLocation.getBlock().getType().isSolid())
        {
            return false;
        }

        if (! blockLocation.addY(-1).getBlock().getType().isSolid())
        {
            return false;
        }

        return true;
    }

    private List<BlockLocation> getMoveCandidates()
    {
        final LinkedList<BlockLocation> locations = new LinkedList<>();

        for (int x = -1; x < 2; x++)
        {
            for (int z = -1; z < 2; z++)
            {
                if ((x == 0) && (z == 0))
                {
                    continue;
                }
                locations.add(this.currentBlock.addX(x).addZ(z));
                locations.add(this.currentBlock.addX(x).addZ(z).addY(1));
                locations.add(this.currentBlock.addX(x).addZ(z).addY(- 1));
            }
        }

        final Iterator<BlockLocation> iterator = locations.iterator();
        while (iterator.hasNext())
        {
            final BlockLocation bl = iterator.next();
            if (! this.isMoveOk(bl))
            {
                iterator.remove();
            }
        }

        return locations;
    }

    @Override
    public void process()
    {
        double near = Integer.MAX_VALUE;
        BlockLocation candidate = null;
        final List<BlockLocation> candidates = this.getMoveCandidates();
        for (final BlockLocation blockLocation : candidates)
        {
            final double nearThis;
            if ((nearThis = this.calculateNear(blockLocation, this.goal)) < near)
            {
                near = nearThis;
                candidate = blockLocation;
            }
        }

        this.currentBlock = candidate;
        final Queue<BlockLocation> path = new LinkedBlockingQueue<>();
        path.add(candidate);

        this.callback.accept(new PathImpl(path));

        if (this.goal.equals(candidate))
        {
            CoreMain.debug("Completed");
            this.isCompleted = true;
        }
    }
}
