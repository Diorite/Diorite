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
 * Class representing block "WallBanner" and all its subtypes.
 */
public class WallBannerMat extends BannerBlockMat implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 4;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WALL_BANNER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WALL_BANNER__HARDNESS;

    public static final WallBannerMat WALL_BANNER_NORTH = new WallBannerMat();
    public static final WallBannerMat WALL_BANNER_SOUTH = new WallBannerMat(BlockFace.SOUTH);
    public static final WallBannerMat WALL_BANNER_WEST  = new WallBannerMat(BlockFace.WEST);
    public static final WallBannerMat WALL_BANNER_EAST  = new WallBannerMat(BlockFace.EAST);

    private static final Map<String, WallBannerMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallBannerMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected WallBannerMat()
    {
        super("WALL_BANNER", 177, "minecraft:wall_banner", "NORTH", (byte) 0x02);
        this.face = BlockFace.NORTH;
    }

    protected WallBannerMat(final BlockFace face)
    {
        super(WALL_BANNER_NORTH.name(), WALL_BANNER_NORTH.getId(), WALL_BANNER_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
    }

    protected WallBannerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
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
    public WallBannerMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallBannerMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public WallBannerMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public WallBannerMat getAttachedFace(final BlockFace face)
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
     * Returns one of WallBanner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBannerMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WallBanner sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBannerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WallBanner sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of WallBanner
     *
     * @return sub-type of WallBanner
     */
    public static WallBannerMat getWallBanner(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallBannerMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WallBannerMat.register(WALL_BANNER_NORTH);
        WallBannerMat.register(WALL_BANNER_SOUTH);
        WallBannerMat.register(WALL_BANNER_WEST);
        WallBannerMat.register(WALL_BANNER_EAST);
    }
}
