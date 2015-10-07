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
import org.diorite.material.blocks.RotateAxisMat;
import org.diorite.material.blocks.wooden.wood.LeavesMat;
import org.diorite.material.blocks.wooden.wood.LogMat;
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
