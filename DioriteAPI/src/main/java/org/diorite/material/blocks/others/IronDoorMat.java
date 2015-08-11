package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.DoorMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronDoor" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class IronDoorMat extends BlockMaterialData implements DoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final IronDoorMat IRON_DOOR_BOTTOM_EAST       = new IronDoorMat();
    public static final IronDoorMat IRON_DOOR_BOTTOM_SOUTH      = new IronDoorMat("BOTTOM_SOUTH", BlockFace.SOUTH, false);
    public static final IronDoorMat IRON_DOOR_BOTTOM_WEST       = new IronDoorMat("BOTTOM_WEST", BlockFace.WEST, false);
    public static final IronDoorMat IRON_DOOR_BOTTOM_NORTH      = new IronDoorMat("BOTTOM_NORTH", BlockFace.NORTH, false);
    public static final IronDoorMat IRON_DOOR_BOTTOM_OPEN_EAST  = new IronDoorMat("BOTTOM_OPEN_EAST", BlockFace.EAST, true);
    public static final IronDoorMat IRON_DOOR_BOTTOM_OPEN_SOUTH = new IronDoorMat("BOTTOM_OPEN_SOUTH", BlockFace.SOUTH, true);
    public static final IronDoorMat IRON_DOOR_BOTTOM_OPEN_WEST  = new IronDoorMat("BOTTOM_OPEN_WEST", BlockFace.WEST, true);
    public static final IronDoorMat IRON_DOOR_BOTTOM_OPEN_NORTH = new IronDoorMat("BOTTOM_OPEN_NORTH", BlockFace.NORTH, true);
    public static final IronDoorMat IRON_DOOR_TOP_LEFT          = new IronDoorMat("TOP_LEFT", false, false);
    public static final IronDoorMat IRON_DOOR_TOP_RIGHT         = new IronDoorMat("TOP_RIGHT", false, true);
    public static final IronDoorMat IRON_DOOR_TOP_LEFT_POWERED  = new IronDoorMat("TOP_LEFT_POWERED", true, false);
    public static final IronDoorMat IRON_DOOR_TOP_RIGHT_POWERED = new IronDoorMat("TOP_RIGHT_POWERED", true, true);

    private static final Map<String, IronDoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronDoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean   powered;
    protected final boolean   hingeOnRightSide;
    protected final boolean   open;
    protected final boolean   topPart;
    protected final BlockFace blockFace;

    @SuppressWarnings("MagicNumber")
    protected IronDoorMat()
    {
        super("IRON_DOOR", 71, "minecraft:iron_door", "BOTTOM_EAST", (byte) 0x00, 5, 25);
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = false;
        this.topPart = false;
        this.blockFace = BlockFace.EAST;
    }

    protected IronDoorMat(final String enumName, final boolean powered, final boolean hingeOnRightSide)
    {
        super(IRON_DOOR_BOTTOM_EAST.name(), IRON_DOOR_BOTTOM_EAST.ordinal(), IRON_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, DoorMat.combine(powered, hingeOnRightSide), IRON_DOOR_BOTTOM_EAST.getHardness(), IRON_DOOR_BOTTOM_EAST.getBlastResistance());
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = false;
        this.topPart = true;
        this.blockFace = null;
    }

    protected IronDoorMat(final String enumName, final BlockFace blockFace, final boolean open)
    {
        super(IRON_DOOR_BOTTOM_EAST.name(), IRON_DOOR_BOTTOM_EAST.ordinal(), IRON_DOOR_BOTTOM_EAST.getMinecraftId(), enumName, DoorMat.combine(blockFace, open), IRON_DOOR_BOTTOM_EAST.getHardness(), IRON_DOOR_BOTTOM_EAST.getBlastResistance());
        this.powered = false;
        this.hingeOnRightSide = false;
        this.open = open;
        this.topPart = false;
        this.blockFace = blockFace;
    }

    protected IronDoorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final boolean hingeOnRightSide, final boolean open, final boolean topPart, final BlockFace blockFace, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.powered = powered;
        this.hingeOnRightSide = hingeOnRightSide;
        this.open = open;
        this.topPart = topPart;
        this.blockFace = blockFace;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.IRON_DOOR_ITEM;
    }

    @Override
    public IronDoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronDoorMat getType(final int id)
    {
        return getByID(id);
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

    @Override
    public IronDoorMat getType(final boolean isPowered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(isPowered, hingeOnRightSide));
    }

    @Override
    public IronDoorMat getType(final BlockFace face, final boolean isOpen)
    {
        return getByID(DoorMat.combine(face, isOpen));
    }

    @Override
    public boolean isTopPart()
    {
        return this.topPart;
    }

    @Override
    public IronDoorMat getTopPart(final boolean top)
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
    public IronDoorMat getBlockFacing(final BlockFace face) throws RuntimeException
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
    public IronDoorMat getOpen(final boolean open) throws RuntimeException
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
    public IronDoorMat getPowered(final boolean powered) throws RuntimeException
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
    public IronDoorMat getHingeOnRightSide(final boolean onRightSide) throws RuntimeException
    {
        if (! this.topPart)
        {
            throw new RuntimeException("Bottom part don't define side of hinge!");
        }
        return getByID(DoorMat.combine(this.powered, onRightSide));
    }

    /**
     * Returns one of IronDoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronDoor or null
     */
    public static IronDoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronDoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronDoor or null
     */
    public static IronDoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of IronDoor sub-type based on powered state.
     * It will never return null, and always return top part of door.
     *
     * @param powered          if door should be powered.
     * @param hingeOnRightSide if door should have hinge on right side.
     *
     * @return sub-type of IronDoor
     */
    public static IronDoorMat getIronDoor(final boolean powered, final boolean hingeOnRightSide)
    {
        return getByID(DoorMat.combine(powered, hingeOnRightSide));
    }

    /**
     * Returns one of IronDoor sub-type based on facing direction and open state.
     * It will never return null, and always return bottom part of door.
     *
     * @param blockFace facing direction of door.
     * @param open      if door should be open.
     *
     * @return sub-type of IronDoor
     */
    public static IronDoorMat getIronDoor(final BlockFace blockFace, final boolean open)
    {
        return getByID(DoorMat.combine(blockFace, open));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronDoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronDoorMat[] types()
    {
        return IronDoorMat.ironDoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronDoorMat[] ironDoorTypes()
    {
        return byID.values(new IronDoorMat[byID.size()]);
    }

    static
    {
        IronDoorMat.register(IRON_DOOR_BOTTOM_EAST);
        IronDoorMat.register(IRON_DOOR_BOTTOM_SOUTH);
        IronDoorMat.register(IRON_DOOR_BOTTOM_WEST);
        IronDoorMat.register(IRON_DOOR_BOTTOM_NORTH);
        IronDoorMat.register(IRON_DOOR_BOTTOM_OPEN_EAST);
        IronDoorMat.register(IRON_DOOR_BOTTOM_OPEN_SOUTH);
        IronDoorMat.register(IRON_DOOR_BOTTOM_OPEN_WEST);
        IronDoorMat.register(IRON_DOOR_BOTTOM_OPEN_NORTH);
        IronDoorMat.register(IRON_DOOR_TOP_LEFT);
        IronDoorMat.register(IRON_DOOR_TOP_RIGHT);
        IronDoorMat.register(IRON_DOOR_TOP_LEFT_POWERED);
        IronDoorMat.register(IRON_DOOR_TOP_RIGHT_POWERED);
    }
}
