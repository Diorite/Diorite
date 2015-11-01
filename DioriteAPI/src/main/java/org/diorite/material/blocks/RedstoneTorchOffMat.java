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
 * Class representing 'Redstone Torch Off' block material in minecraft. <br>
 * ID of block: 75 <br>
 * String ID of block: minecraft:unlit_redstone_torch <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone Torch On'/'West' (minecraft:redstone_torch(76):1) <br>
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
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class RedstoneTorchOffMat extends RedstoneTorchMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_WEST  = new RedstoneTorchOffMat();
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_EAST  = new RedstoneTorchOffMat(BlockFace.EAST);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_SOUTH = new RedstoneTorchOffMat(BlockFace.SOUTH);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_NORTH = new RedstoneTorchOffMat(BlockFace.NORTH);
    public static final RedstoneTorchOffMat REDSTONE_TORCH_OFF_UP    = new RedstoneTorchOffMat(BlockFace.UP);

    private static final Map<String, RedstoneTorchOffMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneTorchOffMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneTorchOffMat()
    {
        super("REDSTONE_TORCH_OFF", 75, "minecraft:unlit_redstone_torch", BlockFace.WEST, 0, 0);
    }

    protected RedstoneTorchOffMat(final BlockFace face)
    {
        super(REDSTONE_TORCH_OFF_WEST.name(), REDSTONE_TORCH_OFF_WEST.ordinal(), REDSTONE_TORCH_OFF_WEST.getMinecraftId(), face, REDSTONE_TORCH_OFF_WEST.getHardness(), REDSTONE_TORCH_OFF_WEST.getBlastResistance());
    }

    protected RedstoneTorchOffMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return REDSTONE_TORCH_ON;
    }

    @Override
    public RedstoneTorchOffMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneTorchOffMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return false;
    }

    @Override
    public RedstoneTorchOffMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public RedstoneTorchOffMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOffMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneTorchOff or null
     */
    public static RedstoneTorchOffMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedstoneTorchOff sub-type based on facing direction.
     * It will never return null.
     *
     * @param face facing direction of RedstoneTorchOff.
     *
     * @return sub-type of RedstoneTorchOff
     */
    public static RedstoneTorchOffMat getRedstoneTorchOff(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneTorchOffMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneTorchOffMat[] types()
    {
        return RedstoneTorchOffMat.redstoneTorchOffTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneTorchOffMat[] redstoneTorchOffTypes()
    {
        return byID.values(new RedstoneTorchOffMat[byID.size()]);
    }

    static
    {
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_WEST);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_EAST);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_SOUTH);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_NORTH);
        RedstoneTorchOffMat.register(REDSTONE_TORCH_OFF_UP);
    }
}
