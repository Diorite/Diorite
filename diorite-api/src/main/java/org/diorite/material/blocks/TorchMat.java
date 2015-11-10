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
import org.diorite.material.AttachableMat;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Torch East' block material in minecraft. <br>
 * ID of block: 50 <br>
 * String ID of block: minecraft:torch <br>
 * This block can't be used in inventory, valid material for this block: 'Torch East'/'Item' (minecraft:torch(50):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * UP:
 * Type name: 'Up' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ITEM:
 * Type name: 'Item' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class TorchMat extends BlockMaterialData implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final TorchMat TORCH_EAST  = new TorchMat();
    public static final TorchMat TORCH_WEST  = new TorchMat(BlockFace.WEST);
    public static final TorchMat TORCH_SOUTH = new TorchMat(BlockFace.SOUTH);
    public static final TorchMat TORCH_NORTH = new TorchMat(BlockFace.NORTH);
    public static final TorchMat TORCH_UP    = new TorchMat(BlockFace.UP);
    public static final TorchMat TORCH_ITEM  = new TorchMat(BlockFace.SELF);

    private static final Map<String, TorchMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TorchMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected TorchMat()
    {
        super("TORCH_EAST", 50, "minecraft:torch", "EAST", (byte) 0x01, 0, 0);
        this.face = BlockFace.EAST;
    }

    protected TorchMat(final BlockFace face)
    {
        super(TORCH_EAST.name(), TORCH_EAST.ordinal(), TORCH_EAST.getMinecraftId(), (face == BlockFace.SELF) ? "ITEM" : face.name(), combine(face), TORCH_EAST.getHardness(), TORCH_EAST.getBlastResistance());
        this.face = face;
    }

    protected TorchMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return TORCH_ITEM;
    }

    @Override
    public TorchMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TorchMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public TorchMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public TorchMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case WEST:
                return 0x2;
            case SOUTH:
                return 0x3;
            case NORTH:
                return 0x4;
            case UP:
                return 0x5;
            case SELF:
                return 0x0;
            default:
                return 0x1;
        }
    }

    /**
     * Returns one of Torch sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Torch or null
     */
    public static TorchMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Torch sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Torch or null
     */
    public static TorchMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Torch sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of Torch
     *
     * @return sub-type of Torch
     */
    public static TorchMat getStandingSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TorchMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public TorchMat[] types()
    {
        return TorchMat.torchTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TorchMat[] torchTypes()
    {
        return byID.values(new TorchMat[byID.size()]);
    }

    static
    {
        TorchMat.register(TORCH_EAST);
        TorchMat.register(TORCH_WEST);
        TorchMat.register(TORCH_SOUTH);
        TorchMat.register(TORCH_NORTH);
        TorchMat.register(TORCH_UP);
        TorchMat.register(TORCH_ITEM);
    }
}
