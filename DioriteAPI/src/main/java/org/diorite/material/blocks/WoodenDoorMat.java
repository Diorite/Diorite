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

package org.diorite.material.blocks;

import org.diorite.BlockFace;
import org.diorite.material.DoorMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenDoor-based blocks
 */
public abstract class WoodenDoorMat extends WoodMat implements DoorMat
{
    protected WoodenDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType, hardness, blastResistance);
    }

    protected WoodenDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, woodType, hardness, blastResistance);
    }

    private static final SimpleEnumMap<WoodType, WoodenDoorMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @Override
    public abstract WoodenDoorMat getType(final boolean isPowered, final boolean hingeOnRightSide);

    @Override
    public abstract WoodenDoorMat getType(final BlockFace face, final boolean isOpen);

    @Override
    public abstract WoodenDoorMat getBlockFacing(final BlockFace face) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getTopPart(final boolean top);

    @Override
    public abstract WoodenDoorMat getPowered(final boolean powered) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getOpen(final boolean open) throws RuntimeException;

    @Override
    public abstract WoodenDoorMat getHingeOnRightSide(final boolean onRightSide) throws RuntimeException;

    @Override
    public WoodenDoorMat getWoodType(final WoodType woodType)
    {
        return types.get(woodType);
    }

    @Override
    public abstract WoodenDoorMat getType(final int type);

    @Override
    public abstract WoodenDoorMat getType(final String type);

    @Override
    public abstract WoodenDoorMat[] types();

    /**
     * Returns one of WoodenDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param woodType         {@link WoodType} of WoodenDoor
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of WoodenDoor
     */
    public static WoodenDoorMat getWoodenDoor(final WoodType woodType, final boolean powered, final boolean hingeOnRightSide)
    {
        return types.get(woodType).getType(powered, hingeOnRightSide);
    }

    /**
     * Returns one of WoodenDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param woodType  {@link WoodType} of WoodenDoor
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of WoodenDoor
     */
    public static WoodenDoorMat getWoodenDoor(final WoodType woodType, final BlockFace blockFace, final boolean open)
    {
        return types.get(woodType).getType(blockFace, open);
    }

    /**
     * Register new wood type to one of doors, like OAK to OAK_DOOR.
     *
     * @param type type of wood.
     * @param mat  door material.
     */
    public static void registerWoodType(final WoodType type, final WoodenDoorMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodType.OAK, OAK_DOOR);
        registerWoodType(WoodType.SPRUCE, SPRUCE_DOOR);
        registerWoodType(WoodType.BIRCH, BIRCH_DOOR);
        registerWoodType(WoodType.JUNGLE, JUNGLE_DOOR);
        registerWoodType(WoodType.ACACIA, ACACIA_DOOR);
        registerWoodType(WoodType.DARK_OAK, DARK_OAK_DOOR);
    }
}
