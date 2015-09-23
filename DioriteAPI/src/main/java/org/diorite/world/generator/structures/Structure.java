package org.diorite.world.generator.structures;

import java.util.Random;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.Block;
import org.diorite.world.chunk.ChunkPos;

@FunctionalInterface
public interface Structure
{
    /**
     * Main generate method, should generate structure and return true. <br>
     * If structure can't be generated, then return false.
     *
     * @param pos      position of pupulated chunk.
     * @param random   random instance to use.
     * @param location center location of structure
     *
     * @return true if strucutre was generated
     */
    boolean generate(ChunkPos pos, Random random, final BlockLocation location);

    /**
     * Main generate method, should generate structure and return true. <br>
     * If structure can't be generated, then return false.
     *
     * @param pos    position of pupulated chunk.
     * @param random random instance to use.
     * @param x      center x location of structure.
     * @param y      center y location of structure.
     * @param z      center z location of structure.
     *
     * @return true if strucutre was generated
     */
    default boolean generate(final ChunkPos pos, final Random random, final int x, final int y, final int z)
    {
        return this.generate(pos, random, new BlockLocation(x, y, z, pos.getWorld()));
    }

    /**
     * Should return true if this block can be replaced to structure one. <br>
     * Default implementation allows all non-solid blocks to be changed. {@link BlockMaterialData#isSolid()}
     *
     * @param block block to check.
     *
     * @return true if block can be replaced.
     */
    default boolean canBeReplaced(final Block block)
    {
        return (block == null) || ! block.getType().isSolid();
    }

    /**
     * Helper method to set block relative to given location to selected material.
     *
     * @param loc   center location.
     * @param x     blocks to move from center in x coords.
     * @param y     blocks to move from center in y coords.
     * @param z     blocks to move from center in z coords.
     * @param block new material to set.
     */
    default void setBlockForce(final BlockLocation loc, final int x, final int y, final int z, final BlockMaterialData block)
    {
        loc.add(x, y, z).setBlock(block);
    }

    /**
     * Helper method to set block relative to given location to selected material.
     *
     * @param loc   center location.
     * @param rel   blocks to move from center.
     * @param block new material to set.
     */
    default void setBlockForce(final BlockLocation loc, final BlockLocation rel, final BlockMaterialData block)
    {
        loc.add(rel).setBlock(block);
    }

    /**
     * Helper method to set block on given location to selected material.
     *
     * @param loc   center location.
     * @param block new material to set.
     */
    default void setBlockForce(final BlockLocation loc, final BlockMaterialData block)
    {
        loc.setBlock(block);
    }

    /**
     * Helper method to set block relative to given location to selected material.<br>
     * It will only change block if {@link #canBeReplaced(Block)} returns true.
     *
     * @param loc   center location.
     * @param x     blocks to move from center in x coords.
     * @param y     blocks to move from center in y coords.
     * @param z     blocks to move from center in z coords.
     * @param block new material to set.
     *
     * @return true if black was set.
     */
    default boolean setBlock(final BlockLocation loc, final int x, final int y, final int z, final BlockMaterialData block)
    {
        final Block b = loc.add(x, y, z).getBlock();
        if (! this.canBeReplaced(b))
        {
            return false;
        }
        b.setType(block);
        return true;
    }

    /**
     * Helper method to set block relative to given location to selected material.<br>
     * It will only change block if {@link #canBeReplaced(Block)} returns true.
     *
     * @param loc   center location.
     * @param rel   blocks to move from center.
     * @param block new material to set.
     *
     * @return true if black was set.
     */
    default boolean setBlock(final BlockLocation loc, final BlockLocation rel, final BlockMaterialData block)
    {
        final Block b = loc.add(rel).getBlock();
        if (! this.canBeReplaced(b))
        {
            return false;
        }
        b.setType(block);
        return true;
    }

    /**
     * Helper method to set block relative to given location to selected material.<br>
     * It will only change block if {@link #canBeReplaced(Block)} returns true.
     *
     * @param loc   center location.
     * @param block new material to set.
     *
     * @return true if black was set.
     */
    default boolean setBlock(final BlockLocation loc, final BlockMaterialData block)
    {
        final Block b = loc.getBlock();
        if (! this.canBeReplaced(b))
        {
            return false;
        }
        b.setType(block);
        return true;
    }
}
