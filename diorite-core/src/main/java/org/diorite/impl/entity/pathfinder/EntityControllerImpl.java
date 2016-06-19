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
    private final IEntity    entity;
    private       Pathfinder currentPathfinder;
    private Queue<BlockLocation> currentPath = new LinkedList<>();
    private int lastMove;


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
        this.getService().cancellPathfinding(this.currentPathfinder);
        this.currentPathfinder = null;
        this.lastMove = 0;
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

    public void tick()
    {
        if (! this.isCurrentlyNavigating())
        {
            return;
        }

        if (! this.currentPathfinder.isCompleted())
        {
            this.getService().processPathfinding(this.currentPathfinder);
        }

        if (! this.currentPath.isEmpty() && (this.lastMove++ > 10))
        {
            this.lastMove = 0;
            this.move(this.currentPath.poll());
        }
    }

    private void move(final BlockLocation newBlock)
    {
        final BlockLocation currBlock = this.getEntity().getLocation().toBlockLocation();

        int x = newBlock.getX() - currBlock.getX();
        int y = newBlock.getY() - currBlock.getY();
        int z = newBlock.getZ() - currBlock.getZ();

        CoreMain.debug("currBlock=" + currBlock);
        CoreMain.debug("x=" + x + ", y=" + y + ", z=" + z);
        CoreMain.debug("newBlock=" + newBlock);
        CoreMain.debug(" ");

        this.entity.move(x, y, z);
    }

    private void receivePath(final Path path)
    {
        this.currentPath.addAll(path.getPath());
    }

    private PathfinderService<Pathfinder> getService()
    {
        //noinspection unchecked
        return this.entity.getCore().getServerManager().getPathfinderService();
    }
}
