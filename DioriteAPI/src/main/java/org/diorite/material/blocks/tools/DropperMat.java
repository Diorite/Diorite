package org.diorite.material.blocks.tools;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.material.blocks.stony.StonyMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dropper" and all its subtypes.
 */
public class DropperMat extends StonyMat implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if Dropper is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte  POWERED_FLAG     = 0x8;
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

    public static final DropperMat DROPPER_DOWN  = new DropperMat();
    public static final DropperMat DROPPER_UP    = new DropperMat(BlockFace.UP, false);
    public static final DropperMat DROPPER_NORTH = new DropperMat(BlockFace.NORTH, false);
    public static final DropperMat DROPPER_SOUTH = new DropperMat(BlockFace.SOUTH, false);
    public static final DropperMat DROPPER_WEST  = new DropperMat(BlockFace.WEST, false);
    public static final DropperMat DROPPER_EAST  = new DropperMat(BlockFace.EAST, false);

    public static final DropperMat DROPPER_DOWN_POWERED  = new DropperMat(BlockFace.DOWN, true);
    public static final DropperMat DROPPER_UP_POWERED    = new DropperMat(BlockFace.UP, true);
    public static final DropperMat DROPPER_NORTH_POWERED = new DropperMat(BlockFace.NORTH, true);
    public static final DropperMat DROPPER_SOUTH_POWERED = new DropperMat(BlockFace.SOUTH, true);
    public static final DropperMat DROPPER_WEST_POWERED  = new DropperMat(BlockFace.WEST, true);
    public static final DropperMat DROPPER_EAST_POWERED  = new DropperMat(BlockFace.EAST, true);

    private static final Map<String, DropperMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DropperMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace facing;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected DropperMat()
    {
        super("DROPPER", 158, "minecraft:dropper", "DOWN", (byte) 0x00);
        this.facing = BlockFace.DOWN;
        this.powered = false;
    }

    protected DropperMat(final BlockFace facing, final boolean powered)
    {
        super(DROPPER_DOWN.name(), DROPPER_DOWN.getId(), DROPPER_DOWN.getMinecraftId(), facing.name() + (powered ? "_POWERED" : ""), combine(facing, powered));
        this.facing = facing;
        this.powered = powered;
    }

    protected DropperMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean powered)
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
    public DropperMat getPowered(final boolean powered)
    {
        return getByID(combine(this.facing, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public DropperMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    public DropperMat getType(final BlockFace face, final boolean powered)
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
    public DropperMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DropperMat getType(final int id)
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
    public static DropperMat getByID(final int id)
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
    public static DropperMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dropper sub-type based on {@link BlockFace} and powered state
     * It will never return null.
     *
     * @param face    facing direction of Dropper.
     * @param powered if Dropper should be powered/
     *
     * @return sub-type of Dropper
     */
    public static DropperMat getDropper(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DropperMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public DropperMat[] types()
    {
        return DropperMat.dropperTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DropperMat[] dropperTypes()
    {
        return byID.values(new DropperMat[byID.size()]);
    }

    static
    {
        DropperMat.register(DROPPER_DOWN);
        DropperMat.register(DROPPER_UP);
        DropperMat.register(DROPPER_NORTH);
        DropperMat.register(DROPPER_SOUTH);
        DropperMat.register(DROPPER_WEST);
        DropperMat.register(DROPPER_EAST);
        DropperMat.register(DROPPER_DOWN_POWERED);
        DropperMat.register(DROPPER_UP_POWERED);
        DropperMat.register(DROPPER_NORTH_POWERED);
        DropperMat.register(DROPPER_SOUTH_POWERED);
        DropperMat.register(DROPPER_WEST_POWERED);
        DropperMat.register(DROPPER_EAST_POWERED);
    }
}
