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

package org.diorite.material;

import org.diorite.BlockFace;

/**
 * Used by all door-based blocks
 */
public interface DoorMat extends PowerableMat, DirectionalMat, OpenableMat
{
    /**
     * Bit flag defining half-part of door.
     * If bit is set to 0, then it's bottom part.
     */
    byte HALF_FLAG    = 0x8; // 0 -> bottom part of dors
    /**
     * Bit flag defining if door is closed.
     * Works only for bottom part of door.
     * If bit is set to 0, then door is closed
     */
    byte OPEN_FLAG    = 0x4; // 0 -> door is closed, only for bottom part
    /**
     * Bit flag defining if door is powered.
     * Works only for top part of door.
     * If bit is set to 0, then door is unpowered
     */
    byte POWERED_FLAG = 0x2; // 0 -> door is closed, only for top part
    /**
     * Bit flag defining if door have hinge on left side
     * Works only for top part of door.
     * If bit is set to 0, then door have hinge on left side
     */
    byte HINGE_FLAG   = 0x1; // 0 -> door have hinge on left side, only for top part

    /**
     * Returns sub-type of Door, it will always return top part of block with selected powered state.
     *
     * @param isPowered        if door should be powered
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of Door
     */
    DoorMat getType(boolean isPowered, boolean hingeOnRightSide);

    /**
     * Returns sub-type of Door, it will always return buttom part of block with selected {@link BlockFace} and open state.
     *
     * @param face   facing of door
     * @param isOpen if door should be open
     *
     * @return sub-type of Door
     */
    DoorMat getType(BlockFace face, boolean isOpen);


    /**
     * @return true if this is top part of door.
     */
    boolean isTopPart();

    /**
     * Return sub-type of door based on half-part state/
     *
     * @param top if it should be top part of door
     *
     * @return sub-type of door.
     */
    DoorMat getTopPart(boolean top);

    /**
     * Returns facing direction ({@link BlockFace}) of door, only bottom part of door defining facing.
     *
     * @return facing direction ({@link BlockFace}) of door.
     *
     * @throws RuntimeException if this is top part of door
     */
    @Override
    BlockFace getBlockFacing() throws RuntimeException;

    /**
     * Return sub-type of door based on {@link BlockFace}, it can be used only on bottop part of door.
     *
     * @param face facing direction ({@link BlockFace}) of door.
     *
     * @return sub-type of door.
     *
     * @throws RuntimeException if this is top part of door
     */
    @Override
    DoorMat getBlockFacing(BlockFace face) throws RuntimeException;

    /**
     * Returns true if door is open, only bottom part of door defining if door is open.
     *
     * @return true if door is open.
     *
     * @throws RuntimeException if this is top part of door
     */
    @Override
    boolean isOpen() throws RuntimeException;

    /**
     * Return sub-type of door based on open state, it can be used only on bottop part of door.
     *
     * @param open if it should be open type
     *
     * @return sub-type of door.
     *
     * @throws RuntimeException if this is top part of door
     */
    @Override
    DoorMat getOpen(boolean open) throws RuntimeException;

    /**
     * Returns true if door is powered, only top part of door defining if door is powered.
     *
     * @return true if door is powered.
     *
     * @throws RuntimeException if this is bottom part of door
     */
    @Override
    boolean isPowered() throws RuntimeException;

    /**
     * Return sub-type of door based on powered state, it can be used only on top part of door.
     *
     * @param powered if it should be powered type
     *
     * @return sub-type of door.
     *
     * @throws RuntimeException if this is bottom part of door
     */
    @Override
    DoorMat getPowered(boolean powered) throws RuntimeException;

    /**
     * Returns true if door has hinge on rgiht side, only top part of door defining side of hinge.
     *
     * @return true if door has hinge on rgiht side.
     *
     * @throws RuntimeException if this is bottom part of door
     */
    boolean hasHingeOnRightSide() throws RuntimeException;

    /**
     * Return sub-type of door based on hinge side, it can be used only on top part of door.
     *
     * @param onRightSide if door should have hinge on right side.
     *
     * @return sub-type of door.
     *
     * @throws RuntimeException if this is bottom part of door
     */
    DoorMat getHingeOnRightSide(boolean onRightSide) throws RuntimeException;


    @SuppressWarnings("JavaDoc")
    static byte combine(final boolean isPowered, final boolean hingeOnRightSide)
    {
        byte result = HALF_FLAG;
        if (isPowered)
        {
            result |= POWERED_FLAG;
        }
        if (hingeOnRightSide)
        {
            result |= HINGE_FLAG;
        }
        return result;
    }

    @SuppressWarnings("JavaDoc")
    static byte combine(final BlockFace face, final boolean isOpen)
    {
        byte result = isOpen ? OPEN_FLAG : 0x0;
        switch (face)
        {
            case SOUTH:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case NORTH:
                result |= 0x3;
                break;
            default:
                break;
        }
        return result;
    }
}
