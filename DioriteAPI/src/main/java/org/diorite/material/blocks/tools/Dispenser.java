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
 * Class representing block "Dispenser" and all its subtypes.
 */
public class Dispenser extends Stony implements Directional, Activatable
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 12;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DISPENSER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DISPENSER__HARDNESS;
    public static final byte  ACTIVE_FLAG      = 0x08;

    public static final Dispenser DISPENSER_DOWN  = new Dispenser();
    public static final Dispenser DISPENSER_UP    = new Dispenser("UP", BlockFace.UP, false);
    public static final Dispenser DISPENSER_NORTH = new Dispenser("NORTH", BlockFace.NORTH, false);
    public static final Dispenser DISPENSER_SOUTH = new Dispenser("SOUTH", BlockFace.SOUTH, false);
    public static final Dispenser DISPENSER_WEST  = new Dispenser("WEST", BlockFace.WEST, false);
    public static final Dispenser DISPENSER_EAST  = new Dispenser("EAST", BlockFace.EAST, false);

    public static final Dispenser DISPENSER_DOWN_ACTIVE  = new Dispenser("DOWN_ACTIVE", BlockFace.DOWN, true);
    public static final Dispenser DISPENSER_UP_ACTIVE    = new Dispenser("UP_ACTIVE", BlockFace.UP, true);
    public static final Dispenser DISPENSER_NORTH_ACTIVE = new Dispenser("NORTH_ACTIVE", BlockFace.NORTH, true);
    public static final Dispenser DISPENSER_SOUTH_ACTIVE = new Dispenser("SOUTH_ACTIVE", BlockFace.SOUTH, true);
    public static final Dispenser DISPENSER_WEST_ACTIVE  = new Dispenser("WEST_ACTIVE", BlockFace.WEST, true);
    public static final Dispenser DISPENSER_EAST_ACTIVE  = new Dispenser("EAST_ACTIVE", BlockFace.EAST, true);

    private static final Map<String, Dispenser>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Dispenser> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace facing;
    protected final boolean   activated;

    @SuppressWarnings("MagicNumber")
    protected Dispenser()
    {
        super("DISPENSER", 23, "minecraft:dispenser", "DOWN", (byte) 0x00);
        this.facing = BlockFace.DOWN;
        this.activated = false;
    }

    public Dispenser(final String enumName, final BlockFace facing, final boolean activated)
    {
        super(DISPENSER_DOWN.name(), DISPENSER_DOWN.getId(), DISPENSER_DOWN.getMinecraftId(), enumName, combine(facing, activated));
        this.facing = facing;
        this.activated = activated;
    }

    public Dispenser(final int maxStack, final String typeName, final BlockFace facing, final boolean activated)
    {
        super(DISPENSER_DOWN.name(), DISPENSER_DOWN.getId(), DISPENSER_DOWN.getMinecraftId(), maxStack, typeName, combine(facing, activated));
        this.facing = facing;
        this.activated = activated;
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public Dispenser getActivated(final boolean activate)
    {
        return getByID(combine(this.facing, activate));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public Dispenser getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.activated));
    }

    public Dispenser getType(final BlockFace face, final boolean activate)
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
    public Dispenser getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Dispenser getType(final int id)
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
     * Returns one of Dispenser sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dispenser or null
     */
    public static Dispenser getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dispenser sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dispenser or null
     */
    public static Dispenser getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dispenser sub-type based on {@link BlockFace} and activate state
     * It will never return null.
     *
     * @param face     facing direction of Dispenser.
     * @param activate if Dispenser should be activated/
     *
     * @return sub-type of Dispenser
     */
    public static Dispenser getDispenser(final BlockFace face, final boolean activate)
    {
        return getByID(combine(face, activate));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Dispenser element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Dispenser.register(DISPENSER_DOWN);
        Dispenser.register(DISPENSER_UP);
        Dispenser.register(DISPENSER_NORTH);
        Dispenser.register(DISPENSER_SOUTH);
        Dispenser.register(DISPENSER_WEST);
        Dispenser.register(DISPENSER_EAST);
        Dispenser.register(DISPENSER_DOWN_ACTIVE);
        Dispenser.register(DISPENSER_UP_ACTIVE);
        Dispenser.register(DISPENSER_NORTH_ACTIVE);
        Dispenser.register(DISPENSER_SOUTH_ACTIVE);
        Dispenser.register(DISPENSER_WEST_ACTIVE);
        Dispenser.register(DISPENSER_EAST_ACTIVE);
    }
}
