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

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Torch On' block material in minecraft. <br>
 * ID of block: 76 <br>
 * String ID of block: minecraft:redstone_torch <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone Torch On'/'Item' (minecraft:redstone_torch(76):0) <br>
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
 * EAST:
 * Type name: 'East' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
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
public class RedstoneTorchOnMat extends RedstoneTorchMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_WEST  = new RedstoneTorchOnMat();
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_EAST  = new RedstoneTorchOnMat(BlockFace.EAST);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_SOUTH = new RedstoneTorchOnMat(BlockFace.SOUTH);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_NORTH = new RedstoneTorchOnMat(BlockFace.NORTH);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_UP    = new RedstoneTorchOnMat(BlockFace.UP);
    public static final RedstoneTorchOnMat REDSTONE_TORCH_ON_ITEM  = new RedstoneTorchOnMat(BlockFace.SELF);

    private static final Map<String, RedstoneTorchOnMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOnMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOnMat()
    {
        super("REDSTONE_TORCH_ON", 76, "minecraft:redstone_torch", BlockFace.WEST, 0, 0);
    }

    protected RedstoneTorchOnMat(final BlockFace face)
    {
        super(REDSTONE_TORCH_ON_WEST.name(), REDSTONE_TORCH_ON_WEST.ordinal(), REDSTONE_TORCH_ON_WEST.getMinecraftId(), face, REDSTONE_TORCH_ON_WEST.getHardness(), REDSTONE_TORCH_ON_WEST.getBlastResistance());
    }

    protected RedstoneTorchOnMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return REDSTONE_TORCH_ON_ITEM;
    }

    @Override
    public RedstoneTorchOnMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOnMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    @Override
    public RedstoneTorchOnMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public RedstoneTorchOnMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOnMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneTorchOn or null
     */
    public static RedstoneTorchOnMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedstoneTorchOn sub-type based on facing direction.
     * It will never return null.
     *
     * @param face facing direction of RedstoneTorchOn.
     *
     * @return sub-type of RedstoneTorchOn
     */
    public static RedstoneTorchOnMat getRedstoneTorchOn(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOnMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneTorchOnMat[] types()
    {
        return RedstoneTorchOnMat.redstoneTorchOnTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneTorchOnMat[] redstoneTorchOnTypes()
    {
        return byID.values(new RedstoneTorchOnMat[byID.size()]);
    }

    static
    {
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_WEST);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_EAST);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_SOUTH);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_NORTH);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_UP);
        RedstoneTorchOnMat.register(REDSTONE_TORCH_ON_ITEM);
    }
}
