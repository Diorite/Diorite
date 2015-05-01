package org.diorite.material.blocks.wooden.wood.door;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Door;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchDoor" and all its subtypes.
 */
public class BirchDoor extends WoodenDoor
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_DOOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_DOOR__HARDNESS;

    public static final BirchDoor BIRCH_DOOR_BOTTOM_EAST       = new BirchDoor();
    public static final BirchDoor BIRCH_DOOR_BOTTOM_SOUTH      = new BirchDoor("BOTTOM_SOUTH", BlockFace.SOUTH, false);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_WEST       = new BirchDoor("BOTTOM_WEST", BlockFace.WEST, false);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_NORTH      = new BirchDoor("BOTTOM_NORTH", BlockFace.NORTH, false);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_OPEN_EAST  = new BirchDoor("BOTTOM_OPEN_EAST", BlockFace.EAST, true);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_OPEN_SOUTH = new BirchDoor("BOTTOM_OPEN_SOUTH", BlockFace.SOUTH, true);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_OPEN_WEST  = new BirchDoor("BOTTOM_OPEN_WEST", BlockFace.WEST, true);
    public static final BirchDoor BIRCH_DOOR_BOTTOM_OPEN_NORTH = new BirchDoor("BOTTOM_OPEN_NORTH", BlockFace.NORTH, true);
    public static final BirchDoor BIRCH_DOOR_TOP_LEFT          = new BirchDoor("TOP_LEFT", false, false);
    public static final BirchDoor BIRCH_DOOR_TOP_RIGHT         = new BirchDoor("TOP_RIGHT", false, true);
    public static final BirchDoor BIRCH_DOOR_TOP_LEFT_POWERED  = new BirchDoor("TOP_LEFT_POWERED", true, false);
    public static final BirchDoor BIRCH_DOOR_TOP_RIGHT_POWERED = new BirchDoor("TOP_RIGHT_POWERED", true, true);

    private static final Map<String, BirchDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected BirchDoor()
    {
        super("BIRCH_DOOR", 194, "minecraft:birch_door", "BOTTOM_EAST", WoodType.BIRCH);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    public BirchDoor(final String enumName, final boolean powered, final boolean hingeOnRightSide)
    {
        super(BIRCH_DOOR_BOTTOM_EAST.name(), BIRCH_DOOR_BOTTOM_EAST.getId(), BIRCH_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, Door.combine(powered, hingeOnRightSide), WoodType.BIRCH);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    public BirchDoor(final String enumName, final BlockFace blockFace, final boolean open)
    {
        super(BIRCH_DOOR_BOTTOM_EAST.name(), BIRCH_DOOR_BOTTOM_EAST.getId(), BIRCH_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, Door.combine(blockFace, open), WoodType.BIRCH);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = open;
        this.topPart = false;
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
    public BirchDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchDoor getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BirchDoor getType(final boolean isPowered, final boolean hingeOnRightSide)
    {
        return getByID(Door.combine(isPowered, hingeOnRightSide));
    }

    @Override
    public BirchDoor getType(final BlockFace face, final boolean isOpen)
    {
        return getByID(Door.combine(face, isOpen));
    }

    @Override
    public boolean isTopPart()
    {
        return this.topPart;
    }

    @Override
    public BirchDoor getTopPart(final boolean top)
    {
        if (top)
        {
            return getByID(Door.combine(this.powered, this.hingeOnRightSide));
        }
        return getByID(Door.combine(this.blockFace, this.open));

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
    public BirchDoor getBlockFacing(final BlockFace face) throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define facing direction of door.");
        }
        return getByID(Door.combine(face, this.open));
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
    public BirchDoor getOpen(final boolean open) throws RuntimeException
    {
        if (this.topPart)
        {
            throw new RuntimeException("Top part don't define if door is open!");
        }
        return getByID(Door.combine(this.blockFace, open));
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
    public BirchDoor getPowered(final boolean powered) throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define if door is powered!");
        }
        return getByID(Door.combine(powered, this.hingeOnRightSide));
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
    public BirchDoor getHingeOnRightSide(final boolean onRightSide) throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define side of hinge!");
        }
        return getByID(Door.combine(this.powered, onRightSide));
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
     * Returns one of BirchDoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchDoor or null
     */
    public static BirchDoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchDoor or null
     */
    public static BirchDoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BirchDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of BirchDoor
     */
    public static BirchDoor getBirchDoor(final boolean powered, final boolean hingeOnRightSide)
    {
        return getByID(Door.combine(powered, hingeOnRightSide));
    }

    /**
     * Returns one of BirchDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of BirchDoor
     */
    public static BirchDoor getBirchDoor(final BlockFace blockFace, final boolean open)
    {
        return getByID(Door.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchDoor.register(BIRCH_DOOR_BOTTOM_EAST);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_SOUTH);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_WEST);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_NORTH);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_OPEN_EAST);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_OPEN_SOUTH);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_OPEN_WEST);
        BirchDoor.register(BIRCH_DOOR_BOTTOM_OPEN_NORTH);
        BirchDoor.register(BIRCH_DOOR_TOP_LEFT);
        BirchDoor.register(BIRCH_DOOR_TOP_RIGHT);
        BirchDoor.register(BIRCH_DOOR_TOP_LEFT_POWERED);
        BirchDoor.register(BIRCH_DOOR_TOP_RIGHT_POWERED);
    }
}
