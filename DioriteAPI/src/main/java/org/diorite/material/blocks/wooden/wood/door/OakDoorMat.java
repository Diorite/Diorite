package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.DoorMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "OakDoor" and all its subtypes.
 */
public class OakDoorMat extends WoodenDoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_DOOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_DOOR__HARDNESS;

    public static final OakDoorMat OAK_DOOR_BOTTOM_EAST       = new OakDoorMat();
    public static final OakDoorMat OAK_DOOR_BOTTOM_SOUTH      = new OakDoorMat(BlockFace.SOUTH, false);
    public static final OakDoorMat OAK_DOOR_BOTTOM_WEST       = new OakDoorMat(BlockFace.WEST, false);
    public static final OakDoorMat OAK_DOOR_BOTTOM_NORTH      = new OakDoorMat(BlockFace.NORTH, false);
    public static final OakDoorMat OAK_DOOR_BOTTOM_OPEN_EAST  = new OakDoorMat(BlockFace.EAST, true);
    public static final OakDoorMat OAK_DOOR_BOTTOM_OPEN_SOUTH = new OakDoorMat(BlockFace.SOUTH, true);
    public static final OakDoorMat OAK_DOOR_BOTTOM_OPEN_WEST  = new OakDoorMat(BlockFace.WEST, true);
    public static final OakDoorMat OAK_DOOR_BOTTOM_OPEN_NORTH = new OakDoorMat(BlockFace.NORTH, true);
    public static final OakDoorMat OAK_DOOR_TOP_LEFT          = new OakDoorMat(false, false);
    public static final OakDoorMat OAK_DOOR_TOP_RIGHT         = new OakDoorMat(false, true);
    public static final OakDoorMat OAK_DOOR_TOP_LEFT_POWERED  = new OakDoorMat(true, false);
    public static final OakDoorMat OAK_DOOR_TOP_RIGHT_POWERED = new OakDoorMat(true, true);

    private static final Map<String, OakDoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakDoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected OakDoorMat()
    {
        super("OAK_DOOR", 64, "minecraft:wooden_door", "OAK_DOOR", WoodTypeMat.OAK);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    protected OakDoorMat(final boolean powered, final boolean hingeOnRightSide)
    {
        super(OAK_DOOR_BOTTOM_EAST.name(), OAK_DOOR_BOTTOM_EAST.ordinal(), OAK_DOOR_BOTTOM_EAST.getMinecraftId(), "TOP_" + (hingeOnRightSide ? "RIGHT" : "LEFT") + (powered ? "_POWERED" : ""), DoorMat.combine(powered, hingeOnRightSide), WoodTypeMat.OAK);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    protected OakDoorMat(final BlockFace blockFace, final boolean open)
    {
        super(OAK_DOOR_BOTTOM_EAST.name(), OAK_DOOR_BOTTOM_EAST.ordinal(), OAK_DOOR_BOTTOM_EAST.getMinecraftId(), "BOTTOM_" + (open ? "OPEN_" : "") + blockFace.name(), DoorMat.combine(blockFace, open), WoodTypeMat.OAK);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = open;
        this.topPart = false;
        this.blockFace = blockFace;
    }

    protected OakDoorMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodTypeMat woodType, final boolean powered, final boolean hingeOnRightSide, final boolean open, final boolean topPart, final BlockFace blockFace)
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
    public OakDoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakDoorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public OakDoorMat getType(final boolean isPowered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(isPowered, hingeOnRightSide));
    }

    @Override
    public OakDoorMat getType(final BlockFace face, final boolean isOpen)
    {
        return getByID(DoorMat.combine(face, isOpen));
    }

    @Override
    public boolean isTopPart()
    {
        return this.topPart;
    }

    @Override
    public OakDoorMat getTopPart(final boolean top)
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
    public OakDoorMat getBlockFacing(final BlockFace face) throws RuntimeException
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
    public OakDoorMat getOpen(final boolean open) throws RuntimeException
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
    public OakDoorMat getPowered(final boolean powered) throws RuntimeException
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
    public OakDoorMat getHingeOnRightSide(final boolean onRightSide) throws RuntimeException
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
     * Returns one of OakDoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakDoor or null
     */
    public static OakDoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of OakDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of OakDoor or null
     */
    public static OakDoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of OakDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of OakDoor
     */
    public static OakDoorMat getOakDoor(final boolean powered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(powered, hingeOnRightSide));
    }

    /**
     * Returns one of OakDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of OakDoor
     */
    public static OakDoorMat getOakDoor(final BlockFace blockFace, final boolean open)
    {
        return getByID(DoorMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakDoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public OakDoorMat[] types()
    {
        return OakDoorMat.oakDoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static OakDoorMat[] oakDoorTypes()
    {
        return byID.values(new OakDoorMat[byID.size()]);
    }

    static
    {
        OakDoorMat.register(OAK_DOOR_BOTTOM_EAST);
        OakDoorMat.register(OAK_DOOR_BOTTOM_SOUTH);
        OakDoorMat.register(OAK_DOOR_BOTTOM_WEST);
        OakDoorMat.register(OAK_DOOR_BOTTOM_NORTH);
        OakDoorMat.register(OAK_DOOR_BOTTOM_OPEN_EAST);
        OakDoorMat.register(OAK_DOOR_BOTTOM_OPEN_SOUTH);
        OakDoorMat.register(OAK_DOOR_BOTTOM_OPEN_WEST);
        OakDoorMat.register(OAK_DOOR_BOTTOM_OPEN_NORTH);
        OakDoorMat.register(OAK_DOOR_TOP_LEFT);
        OakDoorMat.register(OAK_DOOR_TOP_RIGHT);
        OakDoorMat.register(OAK_DOOR_TOP_LEFT_POWERED);
        OakDoorMat.register(OAK_DOOR_TOP_RIGHT_POWERED);
    }
}
