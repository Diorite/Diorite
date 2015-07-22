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
 * Class representing block "StandingSign" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class StandingSignMat extends SignBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StandingSignMat STANDING_SIGN_SOUTH            = new StandingSignMat();
    public static final StandingSignMat STANDING_SIGN_SOUTH_SOUTH_WEST = new StandingSignMat(BlockFace.SOUTH_SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_WEST       = new StandingSignMat(BlockFace.SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_WEST_SOUTH_WEST  = new StandingSignMat(BlockFace.WEST_SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_WEST             = new StandingSignMat(BlockFace.WEST);
    public static final StandingSignMat STANDING_SIGN_WEST_NORTH_WEST  = new StandingSignMat(BlockFace.WEST_NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH_WEST       = new StandingSignMat(BlockFace.NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH_NORTH_WEST = new StandingSignMat(BlockFace.NORTH_NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH            = new StandingSignMat(BlockFace.NORTH);
    public static final StandingSignMat STANDING_SIGN_NORTH_NORTH_EAST = new StandingSignMat(BlockFace.NORTH_NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_NORTH_EAST       = new StandingSignMat(BlockFace.NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_EAST_NORTH_EAST  = new StandingSignMat(BlockFace.EAST_NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_EAST             = new StandingSignMat(BlockFace.EAST);
    public static final StandingSignMat STANDING_SIGN_EAST_SOUTH_EAST  = new StandingSignMat(BlockFace.EAST_SOUTH_EAST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_EAST       = new StandingSignMat(BlockFace.SOUTH_EAST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_SOUTH_EAST = new StandingSignMat(BlockFace.SOUTH_SOUTH_EAST);

    private static final Map<String, StandingSignMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StandingSignMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected StandingSignMat()
    {
        super("STANDING_SIGN", 63, "minecraft:standing_sign", "SOUTH", (byte) 0x00, 1, 5);
        this.face = BlockFace.SOUTH;
    }

    protected StandingSignMat(final BlockFace face)
    {
        super(STANDING_SIGN_SOUTH.name(), STANDING_SIGN_SOUTH.ordinal(), STANDING_SIGN_SOUTH.getMinecraftId(), face.name(), combine(face), STANDING_SIGN_SOUTH.getHardness(), STANDING_SIGN_SOUTH.getBlastResistance());
        this.face = face;
    }

    protected StandingSignMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.SIGN;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public StandingSignMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public StandingSignMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StandingSignMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
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
     * Returns one of StandingSign sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSignMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StandingSign sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSignMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StandingSign sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of StandingSign
     *
     * @return sub-type of StandingSign
     */
    public static StandingSignMat getStandingSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StandingSignMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StandingSignMat[] types()
    {
        return StandingSignMat.standingSignTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StandingSignMat[] standingSignTypes()
    {
        return byID.values(new StandingSignMat[byID.size()]);
    }

    static
    {
        StandingSignMat.register(STANDING_SIGN_SOUTH);
        StandingSignMat.register(STANDING_SIGN_SOUTH_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH);
        StandingSignMat.register(STANDING_SIGN_NORTH_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST_SOUTH_EAST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_EAST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_SOUTH_EAST);
    }
}
