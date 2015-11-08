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

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.RotatableMat;
import org.diorite.material.RotateAxisMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Hay Block' block material in minecraft. <br>
 * ID of block: 170 <br>
 * String ID of block: minecraft:hay_block <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_SOUTH:
 * Type name: 'North South' <br>
 * SubID: 8 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST_WEST:
 * Type name: 'East West' <br>
 * SubID: 4 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP_DOWN:
 * Type name: 'Up Down' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class HayBlockMat extends BlockMaterialData implements RotatableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 3;

    public static final HayBlockMat HAY_BLOCK_UP_DOWN     = new HayBlockMat();
    public static final HayBlockMat HAY_BLOCK_EAST_WEST   = new HayBlockMat(RotateAxisMat.EAST_WEST);
    public static final HayBlockMat HAY_BLOCK_NORTH_SOUTH = new HayBlockMat(RotateAxisMat.NORTH_SOUTH);

    private static final Map<String, HayBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HayBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected HayBlockMat()
    {
        super("HAY_BLOCK", 170, "minecraft:hay_block", "UP_DOWN", (byte) 0x00, 0.5f, 2.5f);
        this.rotateAxis = RotateAxisMat.UP_DOWN;
    }

    protected HayBlockMat(final RotateAxisMat rotateAxis)
    {
        super(HAY_BLOCK_UP_DOWN.name(), HAY_BLOCK_UP_DOWN.ordinal(), HAY_BLOCK_UP_DOWN.getMinecraftId(), rotateAxis.name(), combine(rotateAxis), HAY_BLOCK_UP_DOWN.getHardness(), HAY_BLOCK_UP_DOWN.getBlastResistance());
        this.rotateAxis = rotateAxis;
    }

    protected HayBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.rotateAxis = rotateAxis;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.HAY_BLOCK;
    }

    @Override
    public HayBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HayBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    @Override
    public RotateAxisMat getRotateAxis()
    {
        return this.rotateAxis;
    }

    @Override
    public HayBlockMat getRotated(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    @Override
    public HayBlockMat getBlockFacing(final BlockFace face)
    {
        switch (face)
        {
            case NORTH:
            case SOUTH:
                return getByID(combine(RotateAxisMat.NORTH_SOUTH));
            case EAST:
            case WEST:
                return getByID(combine(RotateAxisMat.EAST_WEST));
            case UP:
            case DOWN:
                return getByID(combine(RotateAxisMat.UP_DOWN));
            case SELF:
                return getByID(combine(RotateAxisMat.NONE));
            default:
                return getByID(combine(RotateAxisMat.UP_DOWN));
        }
    }

    private static byte combine(final RotateAxisMat axis)
    {
        if (axis == RotateAxisMat.NONE)
        {
            return 0x0;
        }
        return axis.getFlag();
    }

    /**
     * Returns one of HayBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of HayBlock or null
     */
    public static HayBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of HayBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of HayBlock or null
     */
    public static HayBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of HayBlock sub-type based on {@link RotateAxisMat}.
     * It will never return null;
     *
     * @param rotateAxis RotateAxis of HayBlock
     *
     * @return sub-type of HayBlock
     */
    public static HayBlockMat getHayBlock(final RotateAxisMat rotateAxis)
    {
        return getByID(combine(rotateAxis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HayBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public HayBlockMat[] types()
    {
        return HayBlockMat.hayBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static HayBlockMat[] hayBlockTypes()
    {
        return byID.values(new HayBlockMat[byID.size()]);
    }

    static
    {
        HayBlockMat.register(HAY_BLOCK_UP_DOWN);
        HayBlockMat.register(HAY_BLOCK_EAST_WEST);
        HayBlockMat.register(HAY_BLOCK_NORTH_SOUTH);
    }
}
