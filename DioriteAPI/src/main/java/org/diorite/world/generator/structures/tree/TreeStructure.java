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

package org.diorite.world.generator.structures.tree;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.WoodType;
import org.diorite.material.RotateAxisMat;
import org.diorite.material.blocks.LeavesMat;
import org.diorite.material.blocks.LogMat;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.structures.Structure;

public abstract class TreeStructure implements Structure
{
    protected static final Set<Material> FLOOR_MATS = Sets.newHashSet(Material.GRASS, Material.DIRT);

    protected final BlockMaterialData logMat;
    protected final BlockMaterialData leavesMat;

    public TreeStructure(final WoodType type)
    {
        this(LogMat.getLog(type, RotateAxisMat.UP_DOWN), LeavesMat.getLeaves(type, false, true));
    }

    public TreeStructure(final BlockMaterialData logMat, final BlockMaterialData leavesMat)
    {
        this.logMat = logMat;
        this.leavesMat = leavesMat;
    }

    @Override
    public boolean generate(final ChunkPos chunkPos, final Random random, final BlockLocation location)
    {
        return FLOOR_MATS.contains(location.getBlock().getType()) && this.genTree(chunkPos, random, location);
    }

    protected abstract boolean genTree(final ChunkPos chunkPos, final Random random, final BlockLocation location);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("logMat", this.logMat).append("leavesMat", this.leavesMat).toString();
    }
}
