package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;
import org.diorite.material.blocks.stony.Stony;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dropper" and all its subtypes.
 */
public class Dropper extends Stony implements Directional, Activatable
{
    /**
     * Bit flag defining if Dropper is active.
     * If bit is set to 0, then it isn't active
     */
    public static final byte  ACTIVE_FLAG      = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DROPPER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DROPPER__HARDNESS;

    public static final Dropper DROPPER_DOWN  = new Dropper();
    public static final Dropper DROPPER_UP    = new Dropper("UP", BlockFace.UP, false);
    public static final Dropper DROPPER_NORTH = new Dropper("NORTH", BlockFace.NORTH, false);
    public static final Dropper DROPPER_SOUTH = new Dropper("SOUTH", BlockFace.SOUTH, false);
    public static final Dropper DROPPER_WEST  = new Dropper("WEST", BlockFace.WEST, false);
    public static final Dropper DROPPER_EAST  = new Dropper("EAST", BlockFace.EAST, false);

    public static final Dropper DROPPER_DOWN_ACTIVE  = new Dropper("DOWN_ACTIVE", BlockFace.DOWN, true);
    public static final Dropper DROPPER_UP_ACTIVE    = new Dropper("UP_ACTIVE", BlockFace.UP, true);
    public static final Dropper DROPPER_NORTH_ACTIVE = new Dropper("NORTH_ACTIVE", BlockFace.NORTH, true);
    public static final Dropper DROPPER_SOUTH_ACTIVE = new Dropper("SOUTH_ACTIVE", BlockFace.SOUTH, true);
    public static final Dropper DROPPER_WEST_ACTIVE  = new Dropper("WEST_ACTIVE", BlockFace.WEST, true);
    public static final Dropper DROPPER_EAST_ACTIVE  = new Dropper("EAST_ACTIVE", BlockFace.EAST, true);

    private static final Map<String, Dropper>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Dropper> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace facing;
    protected final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected Dropper()
    {
        super("DROPPER", 158, "minecraft:dropper", "DOWN", (byte) 0x00);
        this.facing = BlockFace.DOWN;
        this.activated = false;
    }

    public Dropper(final String enumName, final BlockFace facing, final boolean activated)
    {
        super(DROPPER_DOWN.name(), DROPPER_DOWN.getId(), DROPPER_DOWN.getMinecraftId(), enumName, combine(facing, activated));
        this.facing = facing;
        this.activated = activated;
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public Dropper getActivated(final boolean activate)
    {
        return getByID(combine(this.facing, activate));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public Dropper getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    public Dropper getType(final BlockFace face, final boolean activate)
    {
        return getByID(combine(face, activate));
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
    public Dropper getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Dropper getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).append("activated", this.activated).toString();
    }

    private static byte combine(final BlockFace facing, final boolean activated)
    {
        byte result = activated ? ACTIVE_FLAG : 0x00;
        switch (facing)
        {
            case UP:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case SOUTH:
                result |= 0x03;
                break;
            case WEST:
                result |= 0x04;
                break;
            case EAST:
                result |= 0x05;
                break;
            case DOWN:
            default:
                return result;
        }
        return result;
    }

    /**
     * Returns one of Dropper sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dropper or null
     */
    public static Dropper getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dropper sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dropper or null
     */
    public static Dropper getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dropper sub-type based on {@link BlockFace} and activate state
     * It will never return null.
     *
     * @param face     facing direction of Dropper.
     * @param activate if Dropper should be activated/
     *
     * @return sub-type of Dropper
     */
    public static Dropper getDropper(final BlockFace face, final boolean activate)
    {
        return getByID(combine(face, activate));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Dropper element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Dropper.register(DROPPER_DOWN);
        Dropper.register(DROPPER_UP);
        Dropper.register(DROPPER_NORTH);
        Dropper.register(DROPPER_SOUTH);
        Dropper.register(DROPPER_WEST);
        Dropper.register(DROPPER_EAST);
        Dropper.register(DROPPER_DOWN_ACTIVE);
        Dropper.register(DROPPER_UP_ACTIVE);
        Dropper.register(DROPPER_NORTH_ACTIVE);
        Dropper.register(DROPPER_SOUTH_ACTIVE);
        Dropper.register(DROPPER_WEST_ACTIVE);
        Dropper.register(DROPPER_EAST_ACTIVE);
    }
}
