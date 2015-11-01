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
import org.diorite.material.PortalMat;
import org.diorite.material.RotatableMat;
import org.diorite.material.RotateAxisMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Nether Portal' block material in minecraft. <br>
 * ID of block: 90 <br>
 * String ID of block: minecraft:portal <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_SOUTH:
 * Type name: 'North South' <br>
 * SubID: 2 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * EAST_WEST:
 * Type name: 'East West' <br>
 * SubID: 1 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class NetherPortalMat extends BlockMaterialData implements RotatableMat, PortalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final NetherPortalMat NETHER_PORTAL_EAST_WEST   = new NetherPortalMat();
    public static final NetherPortalMat NETHER_PORTAL_NORTH_SOUTH = new NetherPortalMat("NORTH_SOUTH", RotateAxisMat.NORTH_SOUTH);

    private static final Map<String, NetherPortalMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<NetherPortalMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected NetherPortalMat()
    {
        super("NETHER_PORTAL", 90, "minecraft:portal", "EAST_WEST", (byte) 0x01, - 1, 18_000_000);
        this.rotateAxis = RotateAxisMat.EAST_WEST;
    }

    protected NetherPortalMat(final String enumName, final RotateAxisMat rotateAxis)
    {
        super(NETHER_PORTAL_EAST_WEST.name(), NETHER_PORTAL_EAST_WEST.ordinal(), NETHER_PORTAL_EAST_WEST.getMinecraftId(), enumName, combine(rotateAxis), NETHER_PORTAL_EAST_WEST.getHardness(), NETHER_PORTAL_EAST_WEST.getBlastResistance());
        this.rotateAxis = rotateAxis;
    }

    protected NetherPortalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.rotateAxis = rotateAxis;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.NETHER_PORTAL;
    }

    @Override
    public NetherPortalMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public NetherPortalMat getType(final int id)
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
    public NetherPortalMat getRotated(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    @Override
    public NetherPortalMat getBlockFacing(final BlockFace face)
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
                return getByID(combine(RotateAxisMat.EAST_WEST));
        }
    }

    private static byte combine(final RotateAxisMat rotateAxis)
    {
        switch (rotateAxis)
        {
            case NORTH_SOUTH:
                return 0x02;
            default:
                return 0x01;
        }
    }

    /**
     * Returns one of NetherPortal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of NetherPortal or null
     */
    public static NetherPortalMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of NetherPortal sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of NetherPortal or null
     */
    public static NetherPortalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of NetherPortal sub-type based on {@link RotateAxisMat}.
     * It will never return null;
     *
     * @param axis rotate axis of block, unsupported axis will cause using axis from default type.
     *
     * @return sub-type of NetherPortal
     */
    public static NetherPortalMat getNetherPortal(final RotateAxisMat axis)
    {
        return getByID(combine(axis));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final NetherPortalMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public NetherPortalMat[] types()
    {
        return NetherPortalMat.netherPortalTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static NetherPortalMat[] netherPortalTypes()
    {
        return byID.values(new NetherPortalMat[byID.size()]);
    }

    static
    {
        NetherPortalMat.register(NETHER_PORTAL_EAST_WEST);
        NetherPortalMat.register(NETHER_PORTAL_NORTH_SOUTH);
    }
}
