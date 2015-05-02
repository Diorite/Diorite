package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Lever" and all its subtypes.
 */
public class LeverMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    /**
     * Bit flag defining if level is powered/activated.
     * If bit is set to 0, then it isn't powered/activated
     */
    public static final byte  POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__LEVER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__LEVER__HARDNESS;

    public static final LeverMat LEVER_DOWN               = new LeverMat();
    public static final LeverMat LEVER_EAST               = new LeverMat(BlockFace.EAST, false, false);
    public static final LeverMat LEVER_WEST               = new LeverMat(BlockFace.WEST, false, false);
    public static final LeverMat LEVER_SOUTH              = new LeverMat(BlockFace.SOUTH, false, false);
    public static final LeverMat LEVER_NORTH              = new LeverMat(BlockFace.NORTH, false, false);
    public static final LeverMat LEVER_UP_SOUTH           = new LeverMat(BlockFace.UP, true, false);
    public static final LeverMat LEVER_UP                 = new LeverMat(BlockFace.UP, false, false);
    public static final LeverMat LEVER_DOWN_SOUTH         = new LeverMat(BlockFace.DOWN, true, false);
    public static final LeverMat LEVER_DOWN_POWERED       = new LeverMat(BlockFace.DOWN, false, true);
    public static final LeverMat LEVER_EAST_POWERED       = new LeverMat(BlockFace.EAST, false, true);
    public static final LeverMat LEVER_WEST_POWERED       = new LeverMat(BlockFace.WEST, false, true);
    public static final LeverMat LEVER_SOUTH_POWERED      = new LeverMat(BlockFace.SOUTH, false, true);
    public static final LeverMat LEVER_NORTH_POWERED      = new LeverMat(BlockFace.NORTH, false, true);
    public static final LeverMat LEVER_UP_SOUTH_POWERED   = new LeverMat(BlockFace.UP, true, true);
    public static final LeverMat LEVER_UP_POWERED         = new LeverMat(BlockFace.UP, false, true);
    public static final LeverMat LEVER_DOWN_SOUTH_POWERED = new LeverMat(BlockFace.DOWN, true, true);

    private static final Map<String, LeverMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LeverMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;
    protected final boolean   rotated;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected LeverMat()
    {
        super("LEVER", 69, "minecraft:lever", "DOWN", (byte) 0x00);
        this.face = BlockFace.DOWN;
        this.rotated = false;
        this.powered = false;
    }

    protected LeverMat(final BlockFace face, final boolean rotated, final boolean powered)
    {
        super(LEVER_DOWN.name(), LEVER_DOWN.getId(), LEVER_DOWN.getMinecraftId(), face.name() + (rotated ? "_SOUTH" : "") + (powered ? "_POWERED" : ""), combine(face, rotated, powered));
        this.face = face;
        this.rotated = rotated;
        this.powered = powered;
    }

    protected LeverMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean rotated, final boolean powered)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.face = face;
        this.rotated = rotated;
        this.powered = powered;
    }

    @Override
    public LeverMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.rotated, this.powered));
    }

    private static byte combine(final BlockFace face, final boolean rotated, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case SOUTH:
                result |= 0x3;
                break;
            case NORTH:
                result |= 0x4;
                break;
            case UP:
                if (rotated)
                {
                    result |= 0x5;
                }
                else
                {
                    result |= 0x6;
                }
                break;
            case DOWN:
                if (rotated)
                {
                    result |= 0x7;
                }
                break;
            default:
                break;
        }
        return result;
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
    public LeverMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LeverMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Level is rotated if block facing is down or up and it's point south when off.
     *
     * @return if level is rotated.
     */
    public boolean isRotated()
    {
        return this.rotated;
    }

    /**
     * Returns one of Lever sub-type based on rotated state.
     * It will never return null;
     *
     * @param rotated if lever should be rotated, apply only for lever facing up or down.
     *
     * @return sub-type of Level
     */
    public LeverMat getRotated(final boolean rotated)
    {
        return getByID(combine(this.face, rotated, this.powered));
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public LeverMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, this.rotated, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public LeverMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.rotated, this.powered));
    }

    public LeverMat getType(final BlockFace face, final boolean rotated, final boolean powered)
    {
        return getByID(combine(face, rotated, powered));
    }

    /**
     * Returns one of Lever sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Lever or null
     */
    public static LeverMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Lever sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Lever or null
     */
    public static LeverMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Lever sub-type based on {@link BlockFace} rotated and powered state.
     * It will never return null;
     *
     * @param face    facing direction of Level.
     * @param rotated if lever should be rotated, apply only for lever facing up or down.
     * @param powered if lever should be powered.
     *
     * @return sub-type of Level
     */
    public static LeverMat getLever(final BlockFace face, final boolean rotated, final boolean powered)
    {
        return getByID(combine(face, rotated, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LeverMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        LeverMat.register(LEVER_DOWN);
        LeverMat.register(LEVER_EAST);
        LeverMat.register(LEVER_WEST);
        LeverMat.register(LEVER_SOUTH);
        LeverMat.register(LEVER_NORTH);
        LeverMat.register(LEVER_UP_SOUTH);
        LeverMat.register(LEVER_UP);
        LeverMat.register(LEVER_DOWN_SOUTH);
        LeverMat.register(LEVER_DOWN_POWERED);
        LeverMat.register(LEVER_EAST_POWERED);
        LeverMat.register(LEVER_WEST_POWERED);
        LeverMat.register(LEVER_SOUTH_POWERED);
        LeverMat.register(LEVER_NORTH_POWERED);
        LeverMat.register(LEVER_UP_SOUTH_POWERED);
        LeverMat.register(LEVER_UP_POWERED);
        LeverMat.register(LEVER_DOWN_SOUTH_POWERED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("rotated", this.rotated).append("powered", this.powered).toString();
    }
}
