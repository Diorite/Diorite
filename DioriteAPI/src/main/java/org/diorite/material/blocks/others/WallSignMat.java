package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WallSign" and all its subtypes.
 */
public class WallSignMat extends SignBlockMat implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WALL_SIGN__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WALL_SIGN__HARDNESS;

    public static final WallSignMat WALL_SIGN_NORTH = new WallSignMat();
    public static final WallSignMat WALL_SIGN_SOUTH = new WallSignMat(BlockFace.SOUTH);
    public static final WallSignMat WALL_SIGN_WEST  = new WallSignMat(BlockFace.WEST);
    public static final WallSignMat WALL_SIGN_EAST  = new WallSignMat(BlockFace.EAST);

    private static final Map<String, WallSignMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallSignMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected WallSignMat()
    {
        super("WALL_SIGN", 68, "minecraft:wall_sign", "NORTH", (byte) 0x02);
        this.face = BlockFace.NORTH;
    }

    protected WallSignMat(final BlockFace face)
    {
        super(WALL_SIGN_NORTH.name(), WALL_SIGN_NORTH.getId(), WALL_SIGN_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected WallSignMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.face = face;
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
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public WallSignMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public WallSignMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallSignMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public WallSignMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH:
                return 0x3;
            case WEST:
                return 0x4;
            case EAST:
                return 0x5;
            default:
                return 0x2;
        }
    }

    /**
     * Returns one of WallSign sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallSign or null
     */
    public static WallSignMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WallSign sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WallSign or null
     */
    public static WallSignMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WallSign sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of WallBanner
     *
     * @return sub-type of WallSign
     */
    public static WallSignMat getWallSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallSignMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WallSignMat.register(WALL_SIGN_NORTH);
        WallSignMat.register(WALL_SIGN_SOUTH);
        WallSignMat.register(WALL_SIGN_WEST);
        WallSignMat.register(WALL_SIGN_EAST);
    }
}
