package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Ladder" and all its subtypes.
 */
public class LadderMat extends BlockMaterialData implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final LadderMat LADDER_NORTH = new LadderMat();
    public static final LadderMat LADDER_SOUTH = new LadderMat(BlockFace.SOUTH);
    public static final LadderMat LADDER_WEST  = new LadderMat(BlockFace.WEST);
    public static final LadderMat LADDER_EAST  = new LadderMat(BlockFace.EAST);

    private static final Map<String, LadderMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LadderMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected LadderMat()
    {
        super("LADDER", 65, "minecraft:ladder", "NORTH", (byte) 0x00, 0.4f, 2);
        this.face = BlockFace.NORTH;
    }

    protected LadderMat(final BlockFace face)
    {
        super(LADDER_NORTH.name(), LADDER_NORTH.ordinal(), LADDER_NORTH.getMinecraftId(), face.name(), combine(face), LADDER_NORTH.getHardness(), LADDER_NORTH.getBlastResistance());
        this.face = face;
    }

    protected LadderMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.LADDER;
    }

    @Override
    public LadderMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LadderMat getType(final int id)
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
    public LadderMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public LadderMat getAttachedFace(final BlockFace face)
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
     * Returns one of Ladder sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Ladder or null
     */
    public static LadderMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Ladder sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Ladder or null
     */
    public static LadderMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Ladder sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Ladder.
     *
     * @return sub-type of Ladder
     */
    public static LadderMat getLadder(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LadderMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LadderMat[] types()
    {
        return LadderMat.ladderTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LadderMat[] ladderTypes()
    {
        return byID.values(new LadderMat[byID.size()]);
    }

    static
    {
        LadderMat.register(LADDER_NORTH);
        LadderMat.register(LADDER_SOUTH);
        LadderMat.register(LADDER_WEST);
        LadderMat.register(LADDER_EAST);
    }
}
