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

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import org.diorite.impl.CoreMain;
import org.diorite.impl.entity.IEntity;
import org.diorite.BlockLocation;
import org.diorite.ILocation;
import org.diorite.entity.Entity;
import org.diorite.entity.pathfinder.EntityController;
import org.diorite.entity.pathfinder.Path;
import org.diorite.entity.pathfinder.Pathfinder;
import org.diorite.entity.pathfinder.PathfinderService;

public class EntityControllerImpl implements EntityController
{
    private final IEntity entity;
    private final Queue<BlockLocation> currentPath = new LinkedList<>();
    private Pathfinder currentPathfinder;

    private int           blockMoves;
    private BlockLocation currBlock;
    private BlockLocation newBlock;

    public EntityControllerImpl(final IEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public Entity getEntity()
    {
        return this.entity;
    }

    @Override
    public boolean isCurrentlyNavigating()
    {
        return this.currentPathfinder != null;
    }

    @Override
    public void cancelNavigation()
    {
        this.getService().cancelPathfinding(this.currentPathfinder);
        this.currentPathfinder = null;
        this.currentPath.clear();
    }

    @Override
    public void navigateTo(final ILocation location)
    {
        if (this.isCurrentlyNavigating())
        {
            this.cancelNavigation();
        }

        this.currentPathfinder = this.getService().initPathfinding(this.entity, location, this::receivePath);
    }

    public void tick(final int tps)
    {
        if (! this.isCurrentlyNavigating())
        {
            return;
        }

        if (! this.currentPathfinder.isCompleted())
        {
            this.getService().processPathfinding(this.currentPathfinder);
        }

        if (this.currentPath.isEmpty())
        {
            return;
        }

        if (this.blockMoves++ == 0)
        {
            if (this.newBlock == null)
            {
                this.currBlock = this.getEntity().getLocation().toBlockLocation();
            }
            else
            {
                this.currBlock = this.newBlock;
            }
            this.newBlock = this.currentPath.poll();
        }
        else if (this.blockMoves > 10)
        {
            this.blockMoves = 0;
            return;
        }
        this.move();
    }

    private void move()
    {
        final double x = (this.newBlock.getX() - this.currBlock.getX()) * 0.1D;
        final double y = (this.newBlock.getY() - this.currBlock.getY()) * 0.1D;
        final double z = (this.newBlock.getZ() - this.currBlock.getZ()) * 0.1D;

        CoreMain.debug("currBlock=" + this.currBlock);
        CoreMain.debug("x=" + x + ", y=" + y + ", z=" + z);
        CoreMain.debug("newBlock=" + this.newBlock);
        CoreMain.debug(" ");

        this.entity.move(x, y, z);
    }

    private void receivePath(final Optional<Path> path)
    {
        if (path.isPresent())
        {
            this.currentPath.addAll(path.get().getPath());
        }
        else
        {
            this.cancelNavigation(); // Pathfinder cancelled the pathfinding by sending null path
        }
    }

    private PathfinderService<Pathfinder> getService()
    {
        //noinspection unchecked
        return this.entity.getCore().getServerManager().getPathfinderService();
    }
}
