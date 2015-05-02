package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Hopper" and all its subtypes.
 */
public class HopperMat extends BlockMaterialData implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if Hopper is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte  POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 10;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__HOPPER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__HOPPER__HARDNESS;

    public static final HopperMat HOPPER_DOWN  = new HopperMat();
    public static final HopperMat HOPPER_NORTH = new HopperMat(BlockFace.NORTH, false);
    public static final HopperMat HOPPER_SOUTH = new HopperMat(BlockFace.SOUTH, false);
    public static final HopperMat HOPPER_WEST  = new HopperMat(BlockFace.WEST, false);
    public static final HopperMat HOPPER_EAST  = new HopperMat(BlockFace.EAST, false);

    public static final HopperMat HOPPER_DOWN_POWERED  = new HopperMat(BlockFace.DOWN, true);
    public static final HopperMat HOPPER_NORTH_POWERED = new HopperMat(BlockFace.NORTH, true);
    public static final HopperMat HOPPER_SOUTH_POWERED = new HopperMat(BlockFace.SOUTH, true);
    public static final HopperMat HOPPER_WEST_POWERED  = new HopperMat(BlockFace.WEST, true);
    public static final HopperMat HOPPER_EAST_POWERED  = new HopperMat(BlockFace.EAST, true);

    private static final Map<String, HopperMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HopperMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace facing;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected HopperMat()
    {
        super("HOPPER", 23, "minecraft:dispenser", "DOWN", (byte) 0x00);
        this.facing = BlockFace.DOWN;
        this.powered = false;
    }

    protected HopperMat(final BlockFace facing, final boolean powered)
    {
        super(HOPPER_DOWN.name(), HOPPER_DOWN.getId(), HOPPER_DOWN.getMinecraftId(), facing.name() + (powered ? "_POWERED" : ""), combine(facing, powered));
        this.facing = facing;
        this.powered = powered;
    }

    protected HopperMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean powered)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.facing = facing;
        this.powered = powered;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public HopperMat getPowered(final boolean powered)
    {
        return getByID(combine(this.facing, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public HopperMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    public HopperMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
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
    public HopperMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HopperMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).append("powered", this.powered).toString();
    }

    private static byte combine(final BlockFace facing, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x00;
        switch (facing)
        {
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
     * Returns one of Hopper sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Hopper or null
     */
    public static HopperMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Hopper sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Hopper or null
     */
    public static HopperMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Hopper sub-type based on {@link BlockFace} and powered state
     * It will never return null.
     *
     * @param face    facing direction of Hopper.
     * @param powered if Hopper should be powered/
     *
     * @return sub-type of Hopper
     */
    public static HopperMat getHopper(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HopperMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        HopperMat.register(HOPPER_DOWN);
        HopperMat.register(HOPPER_NORTH);
        HopperMat.register(HOPPER_SOUTH);
        HopperMat.register(HOPPER_WEST);
        HopperMat.register(HOPPER_EAST);
        HopperMat.register(HOPPER_DOWN_POWERED);
        HopperMat.register(HOPPER_NORTH_POWERED);
        HopperMat.register(HOPPER_SOUTH_POWERED);
        HopperMat.register(HOPPER_WEST_POWERED);
        HopperMat.register(HOPPER_EAST_POWERED);
    }
}
