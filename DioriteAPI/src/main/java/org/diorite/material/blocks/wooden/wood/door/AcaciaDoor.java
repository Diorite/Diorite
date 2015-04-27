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
 * Class representing block "AcaciaDoor" and all its subtypes.
 */
public class AcaciaDoor extends WoodenDoor
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

    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_EAST       = new AcaciaDoor();
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_SOUTH      = new AcaciaDoor("BOTTOM_SOUTH", BlockFace.SOUTH, false);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_WEST       = new AcaciaDoor("BOTTOM_WEST", BlockFace.WEST, false);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_NORTH      = new AcaciaDoor("BOTTOM_NORTH", BlockFace.NORTH, false);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_OPEN_EAST  = new AcaciaDoor("BOTTOM_OPEN_EAST", BlockFace.EAST, true);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_OPEN_SOUTH = new AcaciaDoor("BOTTOM_OPEN_SOUTH", BlockFace.SOUTH, true);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_OPEN_WEST  = new AcaciaDoor("BOTTOM_OPEN_WEST", BlockFace.WEST, true);
    public static final AcaciaDoor ACACIA_DOOR_BOTTOM_OPEN_NORTH = new AcaciaDoor("BOTTOM_OPEN_NORTH", BlockFace.NORTH, true);
    public static final AcaciaDoor ACACIA_DOOR_TOP_LEFT          = new AcaciaDoor("TOP_LEFT", false, false);
    public static final AcaciaDoor ACACIA_DOOR_TOP_RIGHT         = new AcaciaDoor("TOP_RIGHT", false, true);
    public static final AcaciaDoor ACACIA_DOOR_TOP_LEFT_POWERED  = new AcaciaDoor("TOP_LEFT_POWERED", true, false);
    public static final AcaciaDoor ACACIA_DOOR_TOP_RIGHT_POWERED = new AcaciaDoor("TOP_RIGHT_POWERED", true, true);

    private static final Map<String, AcaciaDoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaDoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected AcaciaDoor()
    {
        super("ACACIA_DOOR", 196, "minecraft:acacia_door", "BOTTOM_EAST", WoodType.ACACIA);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    public AcaciaDoor(final String enumName, final boolean powered, final boolean hingeOnRightSide)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.getId(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, Door.combine(powered, hingeOnRightSide), WoodType.ACACIA);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    public AcaciaDoor(final String enumName, final BlockFace blockFace, final boolean open)
    {
        super(ACACIA_DOOR_BOTTOM_EAST.name(), ACACIA_DOOR_BOTTOM_EAST.getId(), ACACIA_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, Door.combine(blockFace, open), WoodType.ACACIA);
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
    public AcaciaDoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaDoor getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AcaciaDoor getType(final boolean isPowered, final boolean hingeOnRightSide)
    {
        return getByID(Door.combine(isPowered, hingeOnRightSide));
    }

    @Override
    public AcaciaDoor getType(final BlockFace face, final boolean isOpen)
    {
        return getByID(Door.combine(face, isOpen));
    }

    @Override
    public boolean isTopPart()
    {
        return this.topPart;
    }

    @Override
    public AcaciaDoor getTopPart(final boolean top)
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
    public AcaciaDoor getBlockFacing(final BlockFace face) throws RuntimeException
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
    public AcaciaDoor getOpen(final boolean open) throws RuntimeException
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
    public AcaciaDoor getPowered(final boolean powered) throws RuntimeException
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
    public AcaciaDoor getHingeOnRightSide(final boolean onRightSide) throws RuntimeException
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
     * Returns one of AcaciaDoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaDoor or null
     */
    public static AcaciaDoor getByID(final int id)
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
    public static AcaciaDoor getByEnumName(final String name)
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
    public static AcaciaDoor getAcaciaDoor(final boolean powered, final boolean hingeOnRightSide)
    {
        return getByID(Door.combine(powered, hingeOnRightSide));
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
    public static AcaciaDoor getAcaciaDoor(final BlockFace blockFace, final boolean open)
    {
        return getByID(Door.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaDoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_EAST);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_SOUTH);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_WEST);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_NORTH);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_OPEN_EAST);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_OPEN_SOUTH);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_OPEN_WEST);
        AcaciaDoor.register(ACACIA_DOOR_BOTTOM_OPEN_NORTH);
        AcaciaDoor.register(ACACIA_DOOR_TOP_LEFT);
        AcaciaDoor.register(ACACIA_DOOR_TOP_RIGHT);
        AcaciaDoor.register(ACACIA_DOOR_TOP_LEFT_POWERED);
        AcaciaDoor.register(ACACIA_DOOR_TOP_RIGHT_POWERED);
    }
}
