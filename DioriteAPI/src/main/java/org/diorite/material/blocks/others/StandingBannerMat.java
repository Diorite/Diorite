package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StandingBanner" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class StandingBannerMat extends BannerBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StandingBannerMat STANDING_BANNER_SOUTH            = new StandingBannerMat();
    public static final StandingBannerMat STANDING_BANNER_SOUTH_SOUTH_WEST = new StandingBannerMat(BlockFace.SOUTH_SOUTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_SOUTH_WEST       = new StandingBannerMat(BlockFace.SOUTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_WEST_SOUTH_WEST  = new StandingBannerMat(BlockFace.WEST_SOUTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_WEST             = new StandingBannerMat(BlockFace.WEST);
    public static final StandingBannerMat STANDING_BANNER_WEST_NORTH_WEST  = new StandingBannerMat(BlockFace.WEST_NORTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_NORTH_WEST       = new StandingBannerMat(BlockFace.NORTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_NORTH_NORTH_WEST = new StandingBannerMat(BlockFace.NORTH_NORTH_WEST);
    public static final StandingBannerMat STANDING_BANNER_NORTH            = new StandingBannerMat(BlockFace.NORTH);
    public static final StandingBannerMat STANDING_BANNER_NORTH_NORTH_EAST = new StandingBannerMat(BlockFace.NORTH_NORTH_EAST);
    public static final StandingBannerMat STANDING_BANNER_NORTH_EAST       = new StandingBannerMat(BlockFace.NORTH_EAST);
    public static final StandingBannerMat STANDING_BANNER_EAST_NORTH_EAST  = new StandingBannerMat(BlockFace.EAST_NORTH_EAST);
    public static final StandingBannerMat STANDING_BANNER_EAST             = new StandingBannerMat(BlockFace.EAST);
    public static final StandingBannerMat STANDING_BANNER_EAST_SOUTH_EAST  = new StandingBannerMat(BlockFace.EAST_SOUTH_EAST);
    public static final StandingBannerMat STANDING_BANNER_SOUTH_EAST       = new StandingBannerMat(BlockFace.SOUTH_EAST);
    public static final StandingBannerMat STANDING_BANNER_SOUTH_SOUTH_EAST = new StandingBannerMat(BlockFace.SOUTH_SOUTH_EAST);

    private static final Map<String, StandingBannerMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StandingBannerMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected StandingBannerMat()
    {
        super("STANDING_BANNER_SOUTH", 176, "minecraft:standing_banner", "SOUTH", (byte) 0x00, 1, 5);
        this.face = BlockFace.SOUTH;
    }

    protected StandingBannerMat(final BlockFace face)
    {
        super(STANDING_BANNER_SOUTH.name(), STANDING_BANNER_SOUTH.ordinal(), STANDING_BANNER_SOUTH.getMinecraftId(), face.name(), combine(face), STANDING_BANNER_SOUTH.getHardness(), STANDING_BANNER_SOUTH.getBlastResistance());
        this.face = face;
    }

    protected StandingBannerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.BANNER;
    }

    @Override
    public StandingBannerMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StandingBannerMat getType(final int id)
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
    public StandingBannerMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @SuppressWarnings("MagicNumber")
    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH_SOUTH_WEST:
                return 0x1;
            case SOUTH_WEST:
                return 0x2;
            case WEST_SOUTH_WEST:
                return 0x3;
            case WEST:
                return 0x4;
            case WEST_NORTH_WEST:
                return 0x5;
            case NORTH_WEST:
                return 0x6;
            case NORTH_NORTH_WEST:
                return 0x7;
            case NORTH:
                return 0x8;
            case NORTH_NORTH_EAST:
                return 0x9;
            case NORTH_EAST:
                return 0xA;
            case EAST_NORTH_EAST:
                return 0xB;
            case EAST:
                return 0xC;
            case EAST_SOUTH_EAST:
                return 0xD;
            case SOUTH_EAST:
                return 0xE;
            case SOUTH_SOUTH_EAST:
                return 0xF;
            default:
                return 0x0;
        }
    }

    /**
     * Returns one of StandingBanner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StandingBanner or null
     */
    public static StandingBannerMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StandingBanner sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StandingBanner or null
     */
    public static StandingBannerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StandingBanner sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of StandingBanner
     *
     * @return sub-type of StandingBanner
     */
    public static StandingBannerMat getStandingBanner(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StandingBannerMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StandingBannerMat[] types()
    {
        return StandingBannerMat.standingBannerTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StandingBannerMat[] standingBannerTypes()
    {
        return byID.values(new StandingBannerMat[byID.size()]);
    }

    static
    {
        StandingBannerMat.register(STANDING_BANNER_SOUTH);
        StandingBannerMat.register(STANDING_BANNER_SOUTH_SOUTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_SOUTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_WEST_SOUTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_WEST);
        StandingBannerMat.register(STANDING_BANNER_WEST_NORTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_NORTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_NORTH_NORTH_WEST);
        StandingBannerMat.register(STANDING_BANNER_NORTH);
        StandingBannerMat.register(STANDING_BANNER_NORTH_NORTH_EAST);
        StandingBannerMat.register(STANDING_BANNER_NORTH_EAST);
        StandingBannerMat.register(STANDING_BANNER_EAST_NORTH_EAST);
        StandingBannerMat.register(STANDING_BANNER_EAST);
        StandingBannerMat.register(STANDING_BANNER_EAST_SOUTH_EAST);
        StandingBannerMat.register(STANDING_BANNER_SOUTH_EAST);
        StandingBannerMat.register(STANDING_BANNER_SOUTH_SOUTH_EAST);
    }
}
