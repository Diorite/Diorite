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

package org.diorite.impl.block;

import org.diorite.block.Block;
import org.diorite.block.CommandBlock;
import org.diorite.tileentity.TileEntityCommandBlock;

public class CommandBlockImpl extends BlockStateImpl implements CommandBlock
{
    private final TileEntityCommandBlock commandBlock;

    public CommandBlockImpl(final Block block)
    {
        super(block);

        this.commandBlock = (TileEntityCommandBlock) block.getWorld().getTileEntity(block.getLocation());

        //TODO: set command and name (get values from tile entity)
    }

    @Override
    public boolean update(final boolean force, final boolean applyPhysics)
    {
        boolean result = super.update(force, applyPhysics);

        if(result)
        {

        }

        return result;
    }

    @Override
    public String getCommand()
    {
        return this.commandBlock.getCommand();
    }

    @Override
    public void setCommand(final String command)
    {
        this.commandBlock.setCommand(command);
    }

    @Override
    public String getName()
    {
        return this.commandBlock.getName();
    }

    @Override
    public void setName(final String name)
    {
        this.commandBlock.setName(name);
    }
}
