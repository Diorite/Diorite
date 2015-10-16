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

package org.diorite;

/**
 * Represents the face of a block.
 */
public enum BlockFace
{
    /**
     * North is facing towards negative z (0, 0, -1).
     */
    NORTH(0, 0, - 1),

    /**
     * East is facing towards positive x (1, 0, 0).
     */
    EAST(1, 0, 0),

    /**
     * South is facing towards positive z (0, 0, 1).
     */
    SOUTH(0, 0, 1),

    /**
     * West is facing towards negative x (-1, 0, 0).
     */
    WEST(- 1, 0, 0),

    /**
     * Up is facing towards positive y (0, 1, 0).
     */
    UP(0, 1, 0),

    /**
     * Down is facing towards negative y (0, -1, 0).
     */
    DOWN(0, - 1, 0),

    /**
     * North-East, facing towards positive x and negative z (1, 0, -1).
     */
    NORTH_EAST(NORTH, EAST),

    /**
     * North-West, facing towards negative x and negative z (-1, 0, -1).
     */
    NORTH_WEST(NORTH, WEST),

    /**
     * South-East, facing towards positive x and positive z (1, 0, 1).
     */
    SOUTH_EAST(SOUTH, EAST),

    /**
     * South-West, facing towards negative x and positive z (-1, 0, 1).
     */
    SOUTH_WEST(SOUTH, WEST),

    /**
     * West-North-West, facing towards negative x and negative z (-2, 0, -1).
     */
    WEST_NORTH_WEST(WEST, NORTH_WEST),

    /**
     * North-North-West, facing towards negative x and negative z (-1, 0, -2).
     */
    NORTH_NORTH_WEST(NORTH, NORTH_WEST),

    /**
     * North-North-East, facing towards positive x and negative z (1, 0, -2).
     */
    NORTH_NORTH_EAST(NORTH, NORTH_EAST),

    /**
     * East-North-East, facing towards positive x and negative z (2, 0, -1).
     */
    EAST_NORTH_EAST(EAST, NORTH_EAST),

    /**
     * East-South-East, facing towards positive x and positive z (2, 0, 1).
     */
    EAST_SOUTH_EAST(EAST, SOUTH_EAST),

    /**
     * South-South-East, facing towards positive x and positive z (1, 0, 2).
     */
    SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),

    /**
     * South-South-West, facing towards negative x and positive z (-1, 0, 2).
     */
    SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),

    /**
     * West-South-West, facing towards negative x and positive z (-2, 0, 1).
     */
    WEST_SOUTH_WEST(WEST, SOUTH_WEST),

    /**
     * Self, facing itself. (0, 0, 0)
     */
    SELF(0, 0, 0);
    public static final BlockFace[] EMPTY = new BlockFace[0];

    private final int modX;
    private final int modY;
    private final int modZ;

    BlockFace(final int modX, final int modY, final int modZ)
    {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    BlockFace(final BlockFace face1, final BlockFace face2)
    {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }

    /**
     * @return Amount of X-coordinates to modify to get the represented block
     */
    public int getModX()
    {
        return this.modX;
    }

    /**
     * @return Amount of Y-coordinates to modify to get the represented block
     */
    public int getModY()
    {
        return this.modY;
    }

    /**
     * @return Amount of Z-coordinates to modify to get the represented block
     */
    public int getModZ()
    {
        return this.modZ;
    }

    /**
     * @return true if block face is using only one axis
     */
    public boolean isBasic()
    {
        switch (this)
        {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
            case UP:
            case DOWN:
            case SELF:
                return true;
            default:
                return false;
        }
    }

    /**
     * @return Opposite face of block. like UP {@literal ->} DOWN
     */
    public BlockFace getOppositeFace()
    {
        switch (this)
        {
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.EAST;
            case UP:
                return BlockFace.DOWN;
            case DOWN:
                return BlockFace.UP;
            case NORTH_EAST:
                return BlockFace.SOUTH_WEST;
            case NORTH_WEST:
                return BlockFace.SOUTH_EAST;
            case SOUTH_EAST:
                return BlockFace.NORTH_WEST;
            case SOUTH_WEST:
                return BlockFace.NORTH_EAST;
            case WEST_NORTH_WEST:
                return BlockFace.EAST_SOUTH_EAST;
            case NORTH_NORTH_WEST:
                return BlockFace.SOUTH_SOUTH_EAST;
            case NORTH_NORTH_EAST:
                return BlockFace.SOUTH_SOUTH_WEST;
            case EAST_NORTH_EAST:
                return BlockFace.WEST_SOUTH_WEST;
            case EAST_SOUTH_EAST:
                return BlockFace.WEST_NORTH_WEST;
            case SOUTH_SOUTH_EAST:
                return BlockFace.NORTH_NORTH_WEST;
            case SOUTH_SOUTH_WEST:
                return BlockFace.NORTH_NORTH_EAST;
            case WEST_SOUTH_WEST:
                return BlockFace.EAST_NORTH_EAST;
            case SELF:
                return BlockFace.SELF;
        }
        return BlockFace.SELF;
    }
}
