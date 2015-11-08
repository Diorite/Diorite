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
import org.diorite.material.DoorMat;
import org.diorite.material.Material;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Acacia Door' block material in minecraft. <br>
 * ID of block: 196 <br>
 * String ID of block: minecraft:acacia_door <br>
 * This block can't be used in inventory, valid material for this block: 'Acacia Door Item' (minecraft:acacia_door(430):0) <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * TOP_RIGHT_POWERED:
 * Type name: 'Top Right Powered' <br>
 * SubID: 11 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * TOP_LEFT_POWERED:
 * Type name: 'Top Left Powered' <br>
 * SubID: 10 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * TOP_RIGHT:
 * Type name: 'Top Right' <br>
 * SubID: 9 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * TOP_LEFT:
 * Type name: 'Top Left' <br>
 * SubID: 8 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_OPEN_NORTH:
 * Type name: 'Bottom Open North' <br>
 * SubID: 7 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_OPEN_WEST:
 * Type name: 'Bottom Open West' <br>
 * SubID: 6 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_OPEN_SOUTH:
 * Type name: 'Bottom Open South' <br>
 * SubID: 5 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_OPEN_EAST:
 * Type name: 'Bottom Open East' <br>
 * SubID: 4 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_NORTH:
 * Type name: 'Bottom North' <br>
 * SubID: 3 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_WEST:
 * Type name: 'Bottom West' <br>
 * SubID: 2 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_SOUTH:
 * Type name: 'Bottom South' <br>
 * SubID: 1 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BOTTOM_EAST:
 * Type name: 'Bottom East' <br>
 * SubID: 0 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class AcaciaDoorMat extends WoodenDoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_EAST       = new AcaciaDoorMat();
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_SOUTH      = new AcaciaDoorMat(BlockFace.SOUTH, false);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_WEST       = new AcaciaDoorMat(BlockFace.WEST, false);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_NORTH      = new AcaciaDoorMat(BlockFace.NORTH, false);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_OPEN_EAST  = new AcaciaDoorMat(BlockFace.EAST, true);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_OPEN_SOUTH = new AcaciaDoorMat(BlockFace.SOUTH, true);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_OPEN_WEST  = new AcaciaDoorMat(BlockFace.WEST, true);
    public static final AcaciaDoorMat ACACIA_DOOR_BOTTOM_OPEN_NORTH = new AcaciaDoorMat(BlockFace.NORTH, true);
    public static final AcaciaDoorMat ACACIA_DOOR_TOP_LEFT          = new AcaciaDoorMat(false, false);
    public static final AcaciaDoorMat ACACIA_DOOR_TOP_RIGHT         = new AcaciaDoorMat(false, true);
    public static final AcaciaDoorMat ACACIA_DOOR_TOP_LEFT_POWERED  = new AcaciaDoorMat(true, false);
    public static final AcaciaDoorMat ACACIA_DOOR_TOP_RIGHT_POWERED = new AcaciaDoorMat(true, true);

    private static final Map<String, AcaciaDoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaDoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected AcaciaDoorMat()
    {
        super("ACACIA_DOOR", 196, "minecraft:acacia_door", "BOTTOM_EAST", WoodType.ACACIA, 3, 15);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    protected AcaciaDoorMat(final boolean powered, final boolean hingeOnRightSide)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.ordinal(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), "TOP_" + (hingeOnRightSide ? "RIGHT" : "LEFT") + (powered ? "_POWERED" : ""), DoorMat.combine(powered, hingeOnRightSide), WoodType.ACACIA, ACACIA_DOOR_BOTTOM_EAST.getHardness(), ACACIA_DOOR_BOTTOM_EAST.getBlastResistance());
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    protected AcaciaDoorMat(final BlockFace blockFace, final boolean open)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.ordinal(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), "BOTTOM_" + (open ? "OPEN_" : "") + blockFace.name(), DoorMat.combine(blockFace, open), WoodType.ACACIA, ACACIA_DOOR_BOTTOM_EAST.getHardness(), ACACIA_DOOR_BOTTOM_EAST.getBlastResistance());
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = open;
        this.topPart = false;
        this.blockFace = blockFace;
    }

    protected AcaciaDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType, final boolean powered, final boolean hingeOnRightSide, final boolean open, final boolean topPart, final BlockFace blockFace)
    {
        super(enumName, id, minecraftId, typeName, type, woodType, ACACIA_DOOR_BOTTOM_EAST.getHardness(), ACACIA_DOOR_BOTTOM_EAST.getBlastResistance());
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = open;
        this.topPart = topPart;
        this.blockFace = blockFace;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return ACACIA_DOOR_ITEM;
    }

    @Override
    public AcaciaDoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaDoorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AcaciaDoorMat getType(final boolean isPowered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(isPowered, hingeOnRightSide));
    }

    @Override
    public AcaciaDoorMat getType(final BlockFace face, final boolean isOpen)
    {
        return getByID(DoorMat.combine(face, isOpen));
    }

    @Override
    public boolean isTopPart()
    {
        return this.topPart;
    }

    @Override
    public AcaciaDoorMat getTopPart(final boolean top)
    {
        if (top)
        {
            return getByID(DoorMat.combine(this.powered, this.hingeOnRightSide));
        }
        return getByID(DoorMat.combine(this.blockFace, this.open));

    }

    @Override
    public BlockFace getBlockFacing() throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define facing direction of door.");
        }
        return this.blockFace;
    }

    @Override
    public AcaciaDoorMat getBlockFacing(final BlockFace face) throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define facing direction of door.");
        }
        return getByID(DoorMat.combine(face, this.open));
    }

    @Override
    public boolean isOpen() throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define if door is open!");
        }
        return this.open;
    }

    @Override
    public AcaciaDoorMat getOpen(final boolean open) throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define if door is open!");
        }
        return getByID(DoorMat.combine(this.blockFace, open));
    }

    @Override
    public boolean isPowered() throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define if door is powered!");
        }
        return this.powered;
    }

    @Override
    public AcaciaDoorMat getPowered(final boolean powered) throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define if door is powered!");
        }
        return getByID(DoorMat.combine(powered, this.hingeOnRightSide));
    }

    @Override
    public boolean hasHingeOnRightSide() throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define side of hinge!");
        }
        return this.hingeOnRightSide;
    }

    @Override
    public AcaciaDoorMat getHingeOnRightSide(final boolean onRightSide) throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define side of hinge!");
        }
        return getByID(DoorMat.combine(this.powered, onRightSide));
    }

    @Override
    public String toString()
    {
        if (this.topPart)
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("powered", this.powered).append("topPart", true).toString();
        }
        else
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("open", this.open).append("blockFace", this.blockFace).append("topPart", false).toString();
        }
    }

    /**
     * Returns one of AcaciaDoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaDoor or null
     */
    public static AcaciaDoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of AcaciaDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of AcaciaDoor or null
     */
    public static AcaciaDoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of AcaciaDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of AcaciaDoor
     */
    public static AcaciaDoorMat getAcaciaDoor(final boolean powered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(powered, hingeOnRightSide));
    }

    /**
     * Returns one of AcaciaDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of AcaciaDoor
     */
    public static AcaciaDoorMat getAcaciaDoor(final BlockFace blockFace, final boolean open)
    {
        return getByID(DoorMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaDoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public AcaciaDoorMat[] types()
    {
        return AcaciaDoorMat.acaciaDoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AcaciaDoorMat[] acaciaDoorTypes()
    {
        return byID.values(new AcaciaDoorMat[byID.size()]);
    }

    static
    {
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_EAST);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_SOUTH);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_WEST);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_NORTH);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_OPEN_EAST);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_OPEN_SOUTH);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_OPEN_WEST);
        AcaciaDoorMat.register(ACACIA_DOOR_BOTTOM_OPEN_NORTH);
        AcaciaDoorMat.register(ACACIA_DOOR_TOP_LEFT);
        AcaciaDoorMat.register(ACACIA_DOOR_TOP_RIGHT);
        AcaciaDoorMat.register(ACACIA_DOOR_TOP_LEFT_POWERED);
        AcaciaDoorMat.register(ACACIA_DOOR_TOP_RIGHT_POWERED);
    }
}
