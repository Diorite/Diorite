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
import org.diorite.material.DirectionalMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'End Portal Frame' block material in minecraft. <br>
 * ID of block: 120 <br>
 * String ID of block: minecraft:end_portal_frame <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST_EYE:
 * Type name: 'East Eye' <br>
 * SubID: 7 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * NORTH_EYE:
 * Type name: 'North Eye' <br>
 * SubID: 6 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * WEST_EYE:
 * Type name: 'West Eye' <br>
 * SubID: 5 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * SOUTH_EYE:
 * Type name: 'South Eye' <br>
 * SubID: 4 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 3 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 2 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 1 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 0 <br>
 * Hardness: -1 <br>
 * Blast Resistance 18000000 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class EndPortalFrameMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 8;
    /**
     * Flag that determine if this is activated sub-type
     */
    public static final byte ACTIVATE_FLAG    = 0x4;

    public static final EndPortalFrameMat END_PORTAL_FRAME_SOUTH     = new EndPortalFrameMat();
    public static final EndPortalFrameMat END_PORTAL_FRAME_WEST      = new EndPortalFrameMat(BlockFace.WEST, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_NORTH     = new EndPortalFrameMat(BlockFace.NORTH, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_EAST      = new EndPortalFrameMat(BlockFace.EAST, false);
    public static final EndPortalFrameMat END_PORTAL_FRAME_SOUTH_EYE = new EndPortalFrameMat(BlockFace.SOUTH, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_WEST_EYE  = new EndPortalFrameMat(BlockFace.WEST, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_NORTH_EYE = new EndPortalFrameMat(BlockFace.NORTH, true);
    public static final EndPortalFrameMat END_PORTAL_FRAME_EAST_EYE  = new EndPortalFrameMat(BlockFace.EAST, true);

    private static final Map<String, EndPortalFrameMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EndPortalFrameMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    private final BlockFace face;
    private final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected EndPortalFrameMat()
    {
        super("END_PORTAL_FRAME", 120, "minecraft:end_portal_frame", "SOUTH", (byte) 0x00, - 1, 18_000_000);
        this.face = BlockFace.SOUTH;
        this.activated = false;
    }

    protected EndPortalFrameMat(final BlockFace face, final boolean activated)
    {
        super(END_PORTAL_FRAME_SOUTH.name(), END_PORTAL_FRAME_SOUTH.ordinal(), END_PORTAL_FRAME_SOUTH.getMinecraftId(), face.name() + (activated ? "_EYE" : ""), combine(face, activated), END_PORTAL_FRAME_SOUTH.getHardness(), END_PORTAL_FRAME_SOUTH.getBlastResistance());
        this.face = face;
        this.activated = activated;
    }

    protected EndPortalFrameMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean activated, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.activated = activated;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.END_PORTAL_FRAME;
    }

    @Override
    public EndPortalFrameMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EndPortalFrameMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("activated", this.activated).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public EndPortalFrameMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    /**
     * @return true if frame have eye of ender in it.
     */
    public boolean isActivated()
    {
        return this.activated;
    }


    /**
     * Returns one of EndPortalFrame sub-type based on activated status.
     *
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public EndPortalFrameMat getActivated(final boolean activated)
    {
        return getByID(combine(this.face, activated));
    }

    /**
     * Returns one of EndPortalFrame sub-type based on {@link BlockFace} and activated status.
     * It will never return null;
     *
     * @param face      face of block, unsupported face will cause using face from default type.
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public EndPortalFrameMat getType(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    protected static byte combine(final BlockFace face, final boolean activated)
    {
        byte value = activated ? ACTIVATE_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                value += 0x1;
                break;
            case NORTH:
                value += 0x2;
                break;
            case EAST:
                value += 0x3;
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * Returns one of EndPortalFrame sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EndPortalFrame or null
     */
    public static EndPortalFrameMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EndPortalFrame sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EndPortalFrame or null
     */
    public static EndPortalFrameMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of EndPortalFrame sub-type based on {@link BlockFace} and activated status.
     * It will never return null;
     *
     * @param face      face of block, unsupported face will cause using face from default type.
     * @param activated if it have eye of ender in it.
     *
     * @return sub-type of EndPortalFrame
     */
    public static EndPortalFrameMat getEndPortalFrame(final BlockFace face, final boolean activated)
    {
        return getByID(combine(face, activated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EndPortalFrameMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EndPortalFrameMat[] types()
    {
        return EndPortalFrameMat.endPortalFrameTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EndPortalFrameMat[] endPortalFrameTypes()
    {
        return byID.values(new EndPortalFrameMat[byID.size()]);
    }

    static
    {
        EndPortalFrameMat.register(END_PORTAL_FRAME_SOUTH);
        EndPortalFrameMat.register(END_PORTAL_FRAME_WEST);
        EndPortalFrameMat.register(END_PORTAL_FRAME_NORTH);
        EndPortalFrameMat.register(END_PORTAL_FRAME_EAST);
        EndPortalFrameMat.register(END_PORTAL_FRAME_SOUTH_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_WEST_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_NORTH_EYE);
        EndPortalFrameMat.register(END_PORTAL_FRAME_EAST_EYE);
    }
}
