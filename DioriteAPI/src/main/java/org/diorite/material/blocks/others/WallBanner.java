package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WallBanner" and all its subtypes.
 */
public class WallBanner extends BannerBlock
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

    public static final WallBanner WALL_BANNER_NORTH = new WallBanner();
    public static final WallBanner WALL_BANNER_SOUTH = new WallBanner(BlockFace.SOUTH);
    public static final WallBanner WALL_BANNER_WEST  = new WallBanner(BlockFace.WEST);
    public static final WallBanner WALL_BANNER_EAST  = new WallBanner(BlockFace.EAST);

    private static final Map<String, WallBanner>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallBanner> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected WallBanner()
    {
        super("WALL_BANNER", 177, "minecraft:wall_banner", "NORTH", (byte) 0x02);
        this.face = BlockFace.NORTH;
    }

    public WallBanner(final BlockFace face)
    {
        super(WALL_BANNER_NORTH.name(), WALL_BANNER_NORTH.getId(), WALL_BANNER_NORTH.getMinecraftId(), face.name(), combine(face));
        this.face = face;
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
    public WallBanner getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallBanner getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public WallBanner getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns one of WallBanner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBanner getByID(final int id)
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
    public static WallBanner getByEnumName(final String name)
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
    public static WallBanner getWallBanner(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallBanner element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WallBanner.register(WALL_BANNER_NORTH);
        WallBanner.register(WALL_BANNER_SOUTH);
        WallBanner.register(WALL_BANNER_WEST);
        WallBanner.register(WALL_BANNER_EAST);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }
}
