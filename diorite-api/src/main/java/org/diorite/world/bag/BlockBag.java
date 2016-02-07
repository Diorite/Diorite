package org.diorite.world.bag;

import org.diorite.material.BlockMaterialData;

/**
 * Represent some kind of block changes.
 */
public interface BlockBag
{
    /**
     * Get block material on given cords.
     * @param x x cords of block.
     * @param y y cords of block.
     * @param z z cords of block.
     * @return material.
     */
    BlockMaterialData getBlock(int x, int y, int z);

    /**
     * Set block material on given cords.
     * @param x x cords of block.
     * @param y y cords of block.
     * @param z z cords of block.
     * @param material material of block.
     */
    void setBlock(int x, int y, int z, BlockMaterialData material);

    // TODO: set/get tile entites etc.
}
