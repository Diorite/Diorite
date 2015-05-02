package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.DoorMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "AcaciaDoor" and all its subtypes.
 */
public class AcaciaDoorMat extends WoodenDoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACACIA_DOOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACACIA_DOOR__HARDNESS;

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

    private static final Map<String, AcaciaDoorMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaDoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected AcaciaDoorMat()
    {
        super("ACACIA_DOOR", 196, "minecraft:acacia_door", "BOTTOM_EAST", WoodTypeMat.ACACIA);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    protected AcaciaDoorMat(final boolean powered, final boolean hingeOnRightSide)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.getId(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), "TOP_" + (hingeOnRightSide ? "RIGHT" : "LEFT") + (powered ? "_POWERED" : ""), DoorMat.combine(powered, hingeOnRightSide), WoodTypeMat.ACACIA);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    protected AcaciaDoorMat(final BlockFace blockFace, final boolean open)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.getId(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), "BOTTOM_" + (open ? "OPEN_" : "") + blockFace.name(), DoorMat.combine(blockFace, open), WoodTypeMat.ACACIA);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = open;
        this.topPart = false;
        this.blockFace = blockFace;
    }

    protected AcaciaDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodTypeMat woodType, final boolean powered, final boolean hingeOnRightSide, final boolean open, final boolean topPart, final BlockFace blockFace)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = open;
        this.topPart = topPart;
        this.blockFace = blockFace;
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
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
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
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
